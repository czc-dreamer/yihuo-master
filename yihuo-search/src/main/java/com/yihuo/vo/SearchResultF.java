package com.yihuo.vo;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.pojo.CategoryT;

import java.util.List;

public class SearchResultF<Goods> extends PageResult<Goods> {
    /**
     * 分类的集合
     */
    private List<CategoryT> categories;

    public List<CategoryT> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryT> categories) {
        this.categories = categories;
    }

    public SearchResultF(List<CategoryT> categories){
        this.categories = categories;
    }

    public SearchResultF(Long total, List<Goods> item, List<CategoryT> categories){
        super(total,item);
        this.categories = categories;
    }

    public SearchResultF(Long total,Long totalPage, List<Goods> item,List<CategoryT> categories){
        super(total,totalPage,item);
        this.categories = categories;
    }
}
