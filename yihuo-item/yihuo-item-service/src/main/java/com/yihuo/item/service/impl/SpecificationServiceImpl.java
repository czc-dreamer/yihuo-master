package com.yihuo.item.service.impl;

import com.yihuo.item.service.SpecificationService;
import com.yihuo.item.mapper.SpecificationMapper;
import com.yihuo.item.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 98050
 * Time: 2018-08-14 15:26
 * Feature:
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public Specification queryById(Long id) {
        return this.specificationMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveSpecification(Specification specification) {
        this.specificationMapper.insert(specification);
    }

    @Override
    public void updateSpecification(Specification specification) {
        this.specificationMapper.updateByPrimaryKeySelective(specification);
    }

    @Override
    public void deleteSpecification(Specification specification) {
        this.specificationMapper.deleteByPrimaryKey(specification);
    }
}
