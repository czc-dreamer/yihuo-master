package com.yihuo.user.service;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.parameter.pojo.BrandQueryByPageParameter;
import com.yihuo.user.pojo.User;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:41
 * @Feature:
 */
public interface UserService {
    /**
     * 检查用户名和手机号是否可用
     * @param data
     * @param type
     * @return
     */
    Boolean checkData(String data, Integer type);

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    Boolean sendVerifyCode(String phone);

    /**
     * 用户注册
     * @param username
     * @param password
     * @param phone
     * @return
     */
    Boolean register(String username,String password,String phone);

    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    User queryUser(String username, String password);

    /**
     * 根据用户名修改密码
     * @param username
     * @param newPassword
     * @return
     */
    boolean updatePassword(String username,String oldPassword,String newPassword);

    /**
     * 核对验证码
     * @param phone
     * @param code
     * @return
     */
    Boolean checkCode(String phone,String code);


    /**
     * 根据id查询用户名
     * @param id
     * @return
     */
    List<User> queryUserInfoById(Long id);



    PageResult<User> queryUserByPage(BrandQueryByPageParameter brandQueryByPageParameter);

    void saveImg(String file, long id);

    void deleteUser(long id);

    void updateUser(User user);

    void saveUser(User user);
}
