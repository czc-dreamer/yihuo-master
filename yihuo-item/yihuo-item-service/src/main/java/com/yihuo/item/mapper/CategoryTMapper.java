package com.yihuo.item.mapper;

import com.yihuo.item.pojo.CategoryT;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface CategoryTMapper extends Mapper<CategoryT>, SelectByIdListMapper<CategoryT,Long>{

    /**
     * 根据id查名字
     * @param id
     * @return
     */
    @Select("SELECT name FROM tb_goods_category WHERE id = #{id}")
    String queryNameById(Long id);
}
