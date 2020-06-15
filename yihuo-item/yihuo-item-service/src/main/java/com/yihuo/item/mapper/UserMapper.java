package com.yihuo.item.mapper;

import com.yihuo.item.pojo.UserR;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<UserR> , SelectByIdListMapper<UserR,Long> {
}
