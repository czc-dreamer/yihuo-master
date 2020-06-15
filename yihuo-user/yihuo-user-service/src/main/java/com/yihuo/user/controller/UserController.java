package com.yihuo.user.controller;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.parameter.pojo.BrandQueryByPageParameter;
import com.yihuo.user.pojo.User;
import com.yihuo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: 98050
 * @Time: 2018-10-21 18:40
 * @Feature:
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;



    /**
     * 用户数据检查
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,@PathVariable(value = "type") Integer type){
        System.out.println(data);
        System.out.println(type);
        Boolean result = this.userService.checkData(data,type);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity senVerifyCode(@RequestParam(value = "phone",required = true) String phone){
        System.out.println(phone);
        Boolean result = this.userService.sendVerifyCode(phone);
        if (result == null || !result){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 核对验证码
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("checkCode/{phone}/{code}")
    public ResponseEntity<Boolean> checkCode(@PathVariable("phone") String phone,@PathVariable(value = "code") String code){
        System.out.println("验证码的手机"+phone);
        System.out.println("核对验证码"+code);
        Boolean result = this.userService.checkCode(phone,code);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }
    /**
     * 注册
     * @param username
     * @param password
     * @param phone
     * @return
     */
    @PostMapping("register/{username}/{password}/{phone}")
    public ResponseEntity<Boolean> register(@PathVariable("username") String username,@PathVariable(value = "password") String password,@PathVariable(value = "phone") String phone){
        Boolean result = this.userService.register(username,password,phone);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 图片上传
     * @param file
     * @param id
     * @return
     */
    @PostMapping("saveImg")
    public ResponseEntity<Void> saveImg(@RequestParam("file")String file,@RequestParam("id")long id){
        System.out.println("ifile:"+file);
        System.out.println("iid:"+id);
        this.userService.saveImg(file,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username, @RequestParam("password")String password){
        System.out.println("username2:"+username);
        System.out.println("password2"+password);
        User user = this.userService.queryUser(username,password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("queryUserInfo/{id}")
    public ResponseEntity<List<User>> queryUserInfoById(@PathVariable("id") Long id){
        List<User> user = this.userService.queryUserInfoById(id);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("selectUser")
    public ResponseEntity<PageResult<User>> queryUserByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                              @RequestParam(value = "sortBy", required = false) String sortBy,
                                                              @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                              @RequestParam(value = "key", required = false) String key){
        BrandQueryByPageParameter brandQueryByPageParameter=new BrandQueryByPageParameter(page,rows,sortBy,desc,key);
        PageResult<User> result = this.userService.queryUserByPage(brandQueryByPageParameter);
        if(result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") String ids){
        String separator="-";
        if (ids.contains(separator)){
            String[] userId = ids.split(separator);
            for (String id:userId){
                this.userService.deleteUser(Long.parseLong(id));
            }
        }
        else {
            this.userService.deleteUser(Long.parseLong(ids));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改商品
     * @param user
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user){
        this.userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 保存商品
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody User user){
        this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
