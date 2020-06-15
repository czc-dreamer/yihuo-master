package com.yihuo.auth.service.impl;

import com.yihuo.auth.client.UserClient;
import com.yihuo.auth.entity.UserInfo;
import com.yihuo.auth.properties.JwtProperties;
import com.yihuo.auth.service.AuthService;
import com.yihuo.auth.utils.JwtUtils;
import com.yihuo.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:47
 * @Feature:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    /**
     * 用户授权
     * @param username
     * @param password
     * @return
     */
    @Override
    public String authentication(String username, String password) {
        System.out.println("username:"+username);
        System.out.println("password"+password);
        try{
            //1.调用微服务查询用户信息
            User user = this.userClient.queryUser(username,password);
            //2.查询结果为空，则直接返回null
            if (user == null){
                return null;
            }
            //3.查询结果不为空，则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
            return token;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
