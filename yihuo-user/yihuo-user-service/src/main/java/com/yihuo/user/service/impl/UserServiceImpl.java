package com.yihuo.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihuo.common.pojo.PageResult;
import com.yihuo.parameter.pojo.BrandQueryByPageParameter;
import com.yihuo.user.mapper.UserMapper;
import com.yihuo.user.pojo.User;
import com.yihuo.user.service.UserService;
import com.yihuo.utils.CodecUtils;
import com.yihuo.utils.JsonUtils;
import com.yihuo.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:42
 * @Feature:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "user:code:phone";

    private static final String KEY_PREFIX2 = "yihuo:user:info";

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    /**
     * 分页查询
     * @param brandQueryByPageParameter
     * @return
     */
    @Override
    public PageResult<User> queryUserByPage(BrandQueryByPageParameter brandQueryByPageParameter) {

        /**
         * 1.分页
         */
        PageHelper.startPage(brandQueryByPageParameter.getPage(),brandQueryByPageParameter.getRows());

        /**
         *  2.排序
         */
        Example example = new Example(User.class);

        /**
         * 3.查询
         */
        if(StringUtils.isNotBlank(brandQueryByPageParameter.getKey())) {
            example.createCriteria().orLike("username", brandQueryByPageParameter.getKey()+"%");
        }
        List<User> list=this.userMapper.selectByExample(example);

        /**
         * 4.创建PageInfo
         */
        PageInfo<User> pageInfo = new PageInfo<>(list);
        /**
         * 5.返回分页结果
         */
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1 :
                user.setUsername(data);
                break;
            case 2 :
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(user) == 0;
    }

    @Override
    public Boolean checkCode(String phone,String code){
        System.out.println("sp"+phone);
        System.out.println("sc"+code);
        String key = KEY_PREFIX + phone;
        //1.从redis中取出验证码
        String codeCache = this.stringRedisTemplate.opsForValue().get(key);
        //2.检查验证码是否正确
        System.out.println("codeCache="+codeCache);
        if(!codeCache.equals(code)){
            //不正确，返回
            return false;
        }else {
            try{
                this.stringRedisTemplate.delete(KEY_PREFIX + phone);
            }catch (Exception e){
                logger.error("删除缓存验证码失败，code:{}",code,e);
            }
            return true;
        }
    }

    /**
     * 发送短信验证码
     * @param phone
     */
    @Override
    public Boolean sendVerifyCode(String phone) {

        //1.生成验证码
        String code = NumberUtils.generateCode(6);

        try {
            Map<String,String> msg = new HashMap<>();
            msg.put("phone",phone);
            msg.put("code",code);
            //2.发送短信
            this.amqpTemplate.convertAndSend("yihuo.sms.exchange","sms.verify.code",msg);

            //3.将code存入redis
            this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone,code,5, TimeUnit.MINUTES);

            return true;

        }catch (Exception e){
            logger.error("发送短信失败。phone：{}，code：{}",phone,code);
            return false;
        }
    }



    @Override
    public Boolean register(String username, String password,String phone) {
        /*String key = KEY_PREFIX + user.getPhone();
        //1.从redis中取出验证码
        String codeCache = this.stringRedisTemplate.opsForValue().get(key);
        //2.检查验证码是否正确
        if(!codeCache.equals(code)){
            //不正确，返回
            return false;
        }*/
        User user=new User();
        user.setId(null);
        user.setCreated(new Date());
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        System.out.println("hhha"+user.getUsername());
        System.out.println("hhha"+user.getPassword());
        System.out.println("savenum"+user.getPhone());
        //3.密码加密
        String encodePassword = CodecUtils.passwordBcryptEncode(user.getUsername().trim(),user.getPassword().trim());
        user.setPassword(encodePassword);
        //4.写入数据库
        boolean result = this.userMapper.insertSelective(user) == 1;
        //5.如果注册成功，则删掉redis中的code
        if (result){
            try{
                this.stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
            }catch (Exception e){
                logger.error("删除缓存验证码失败，code:{}");
            }
        }
        return result;
    }

    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    @Override
    public User queryUser(String username, String password) {
        /**
         * 逻辑改变，先去缓存中查询用户数据，查到的话直接返回，查不到再去数据库中查询，然后放入到缓存当中
         */
        //1.缓存中查询
        System.out.println("username3:"+username);
        System.out.println("password"+password);
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX2);
        String userStr = (String) hashOperations.get(username);
        User user;
        System.out.println("hhh"+userStr);
        if (StringUtils.isEmpty(userStr)){
            //在缓存中没有查到，去数据库查,查到放入缓存当中
            User record = new User();
            record.setUsername(username);
            System.out.println("leia"+record);
            user = this.userMapper.selectOne(record);
            hashOperations.put(user.getUsername(), JsonUtils.serialize(user));
            System.out.println("fana"+user.getUsername());
        } else {
            user =  JsonUtils.parse(userStr,User.class);
            System.out.println("wuyu"+user);
        }


        //2.校验用户名
        if (user == null){
            return null;
        }
        //3. 校验密码
        boolean result = CodecUtils.passwordConfirm(username + password,user.getPassword());
        System.out.println("cnm"+user.getPassword());
        if (!result){
            return null;
        }
        System.out.println("user++:"+user);
        //4.用户名密码都正确
        return user;
    }

    /**
     * 根据用户名修改密码
     * @param username
     * @param newPassword
     * @return
     */
    @Override
    public boolean updatePassword(String username,String oldPassword, String newPassword) {
        /**
         * 这里面涉及到对缓存的操作：
         * 先把数据存到数据库中，成功后，再让缓存失效。
         */
        //1.读取用户信息
        User user = this.queryUser(username,oldPassword);
        if (user == null){
            return false;
        }
        //2.更新数据库中的用户信息
        User updateUser = new User();
        updateUser.setId(user.getId());
        //2.1密码加密
        String encodePassword = CodecUtils.passwordBcryptEncode(username.trim(),newPassword.trim());
        updateUser.setPassword(encodePassword);
        this.userMapper.updateByPrimaryKeySelective(updateUser);
        //3.处理缓存中的信息
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX+username);
        hashOperations.delete(username);
        return true;
    }

    /**
     * 根据ID查询用户名
     * @param id
     * @return
     */
    @Override
    public List<User> queryUserInfoById(Long id){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id",id);
        List<User> user = this.userMapper.selectByExample(example);
        System.out.println("userInfo:"+user);
        return user;
    }

 @Override
    public void saveImg(String file,long id){
        this.userMapper.updateHead(file,id);
 }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(long id) {
        //删除spu表中的数据
        this.userMapper.deleteByPrimaryKey(id);

    }

    /**
     * 更新用户信息
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(User user) {
        System.out.println("upuser:"+user);
        String encodePassword = CodecUtils.passwordBcryptEncode(user.getUsername().trim(),user.getPassword().trim());
        user.setPassword(encodePassword);
        this.userMapper.updateByPrimaryKeySelective(user);
        boolean result = this.userMapper.updateByPrimaryKeySelective(user) == 1;
        //5.如果注册成功，则删掉redis中的code
        if (result){
            try{
                this.stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
            }catch (Exception e){
                logger.error("删除缓存验证码失败，code:{}");
            }
        }

    }


    /**
     * 保存用户
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(User user) {
        String encodePassword = CodecUtils.passwordBcryptEncode(user.getUsername().trim(),user.getPassword().trim());
        user.setUsername(user.getUsername());
        user.setPassword(encodePassword);
        user.setCreated(new Date());
        user.setPhone(user.getPhone());
        user.setHead(user.getHead());
        System.out.println("usersave:"+user);
        this.userMapper.insert(user);
    }
}
