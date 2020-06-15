package com.yihuo.item.service;

import com.yihuo.item.pojo.CategoryT;
import com.yihuo.myexception.MyException;

import java.util.List;

public interface CategoryTService {
    /**
     * 根据id查询分类
     * @param cid
     * @return
     */
    List<CategoryT> queryCategoryByCid(Long cid) throws MyException;

    /**
     * 根据ids查询分类信息
     * @param asList
     * @return
     */
    List<String> queryNameByIds(List<Long> asList);

    /**
     * 更新
     * @param categoryT
     */
    void updateCategory(CategoryT categoryT);

    /**
     * 根据分类id集合查询分类信息
     * @param ids
     * @return
     */
    List<CategoryT> queryCategoryByIds(List<Long> ids);


    List<CategoryT> queryAllCategoryTLevelByCid(Long id);


}
