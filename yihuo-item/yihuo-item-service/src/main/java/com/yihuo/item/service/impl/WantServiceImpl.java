package com.yihuo.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.WantGoodsBo;
import com.yihuo.item.mapper.UserMapper;
import com.yihuo.item.mapper.WantMapper;
import com.yihuo.item.mapper.WantRMapper;
import com.yihuo.item.pojo.UserR;
import com.yihuo.item.pojo.WantGoods;
import com.yihuo.item.service.WantService;
import com.yihuo.parameter.pojo.BrandQueryByPageParameter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WantServiceImpl implements WantService {

    @Autowired
    private WantMapper wantMapper;

    @Autowired
    private WantRMapper wantRMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);

    /**
     * 分页查询
     * @param brandQueryByPageParameter
     * @return
     */
    @Override
    public PageResult<WantGoodsBo> queryWantByPage(BrandQueryByPageParameter brandQueryByPageParameter) {

        /**
         * 1.分页
         */
        PageHelper.startPage(brandQueryByPageParameter.getPage(),brandQueryByPageParameter.getRows());

        /**
         *  2.排序
         */
        Example example = new Example(WantGoods.class);

        /**
         * 3.查询
         */
        if(StringUtils.isNotBlank(brandQueryByPageParameter.getKey())) {
            example.createCriteria().orLike("title", brandQueryByPageParameter.getKey()+"%");
        }

        /**
         * 4.创建PageInfo
         */
        Page<WantGoods> pageInfo = (Page<WantGoods>) this.wantMapper.selectByExample(example);

        List<WantGoodsBo> list = pageInfo.getResult().stream().map(seconds -> {
            WantGoodsBo wantGoodsBo = new WantGoodsBo();
            //1.属性拷贝
            BeanUtils.copyProperties(seconds,wantGoodsBo);

            //2.查询spu的商品分类名称，各级分类
            UserR user = this.userMapper.selectByPrimaryKey(seconds.getUser_id());
            //3.拼接名字,并存入
            wantGoodsBo.setUserRS(user);
            return wantGoodsBo;
        }).collect(Collectors.toList());
        /**
         * 5.返回分页结果
         */
        return new PageResult<>(pageInfo.getTotal(),list);
    }

    /**
     * 保存求购
     * @param wantGoods
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWant(WantGoods wantGoods) {
        //保存spu
        wantGoods.setCreate_time(new Date());
        wantGoods.setUpdate_time(wantGoods.getCreate_time());
        wantGoods.setUser_id(wantGoods.getUser_id());
        wantGoods.setSell_price(wantGoods.getSell_price());
        wantGoods.setView_number(0);
        wantGoods.setContent(wantGoods.getContent());
        this.wantMapper.insert(wantGoods);

        //发送消息到mq
        this.sendMessage(wantGoods.getId(),"insert");
    }

    /**
     * 商品删除二合一（多个单个）
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWant(long id) {
        //删除spu表中的数据
        this.wantMapper.deleteByPrimaryKey(id);

        //发送消息到mq
        this.sendMessage(id,"delete");

    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @Override
    public WantGoods queryWantById(Long id) {
        /**
         * 第一页所需信息如下：
         * 1.商品的分类信息、所属品牌、商品标题、商品卖点（子标题）
         * 2.商品的包装清单、售后服务
         */
        WantGoods wantGoods=this.wantMapper.selectByPrimaryKey(id);
        return wantGoods;
    }

    /**
     * 发送消息到mq，生产者
     * @param id
     * @param type
     */
    @Override
    public void sendMessage(Long id, String type) {
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            LOGGER.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }

    /**
     * 更新商品信息
     * @param wantGoods
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateWant(WantGoods wantGoods) {
        /**
         * 更新策略：
         *      1.判断tb_spu_detail中的spec_template字段新旧是否一致
         *      2.如果一致说明修改的只是库存、价格和是否启用，那么就使用update
         *      3.如果不一致，说明修改了特有属性，那么需要把原来的sku全部删除，然后添加新的sku
         */

        //更新spu
        wantGoods.setUpdate_time(new Date());
        this.wantMapper.updateByPrimaryKeySelective(wantGoods);

        //发送消息到mq
        this.sendMessage(wantGoods.getId(),"update");
    }
}
