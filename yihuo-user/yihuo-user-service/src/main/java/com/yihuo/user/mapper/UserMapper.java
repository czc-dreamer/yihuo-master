package com.yihuo.user.mapper;

import com.yihuo.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:40
 * @Feature:
 */
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> , SelectByIdListMapper<User,Long> {


    @Update("UPDATE tb_user SET head=#{file} WHERE id= #{id}")
    void updateHead(@Param("file") String file, @Param("id") Long id);
}
