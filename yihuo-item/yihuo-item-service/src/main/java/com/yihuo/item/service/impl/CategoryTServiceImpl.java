package com.yihuo.item.service.impl;

import com.yihuo.item.mapper.CategoryTMapper;
import com.yihuo.item.pojo.CategoryT;
import com.yihuo.item.service.CategoryTService;
import com.yihuo.myexception.YhException;
import com.yihuo.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryTServiceImpl implements CategoryTService {
    @Autowired
    private CategoryTMapper categoryTMapper;

    /**
     * 根据父节点id查询分类
     * @param cid
     * @return
     */
    @Override
    public List<CategoryT> queryCategoryByCid(Long cid) throws MyException {
        Example example = new Example(CategoryT.class);
        example.createCriteria().andEqualTo("parent_id",cid);
        List<CategoryT> list = this.categoryTMapper.selectByExample(example);
        System.out.println("商品分类列表："+list);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(YhException.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    /**
     * 根据ids查询名字
     * @param asList
     * @return
     */
    @Override
    public List<String> queryNameByIds(List<Long> asList) {
        List<String> names = new ArrayList<>();
        if (asList != null && asList.size() !=0){
            for (Long id : asList) {
                names.add(this.categoryTMapper.queryNameById(id));
            }
        }
        return names;
        //使用通用mapper接口中的SelectByIdListMapper接口查询
        //return this.categoryMapper.selectByIdList(asList).stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
     * 更新
     * @param categoryT
     */
    @Override
    public void updateCategory(CategoryT categoryT) {
        this.categoryTMapper.updateByPrimaryKeySelective(categoryT);
    }
    /**
     * 根据分类id集合查询分类信息
     * @param ids
     * @return
     */
    @Override
    public List<CategoryT> queryCategoryByIds(List<Long> ids) {
        System.out.println("ids="+ids);
        return this.categoryTMapper.selectByIdList(ids);
    }

    /**
     * 根据cid=查询其所有层级分类
     * @param id
     * @return
     */
    @Override
    public List<CategoryT> queryAllCategoryTLevelByCid(Long id) {
        List<CategoryT> categoryTList = new ArrayList<>();
        CategoryT categoryT = this.categoryTMapper.selectByPrimaryKey(id);
        while (categoryT.getParent_id() != 0){
            categoryTList.add(categoryT);
            categoryT = this.categoryTMapper.selectByPrimaryKey(categoryT.getParent_id());
        }
        categoryTList.add(categoryT);
        return categoryTList;
    }
}
