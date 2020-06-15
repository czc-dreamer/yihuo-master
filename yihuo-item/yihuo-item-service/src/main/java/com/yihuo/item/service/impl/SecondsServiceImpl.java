package com.yihuo.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.item.bo.SecondsRBo;
import com.yihuo.item.mapper.*;
import com.yihuo.item.pojo.Seconds;
import com.yihuo.item.pojo.SecondsD;
import com.yihuo.item.service.CategoryTService;
import com.yihuo.item.service.SecondsService;
import com.yihuo.parameter.pojo.SpuQueryByPageParameter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecondsServiceImpl implements SecondsService {

    @Autowired
    private SecondsMapper secondsMapper;

    @Autowired
    private SecondsRMapper secondsRMapper;

    @Autowired
    private WantMapper wantMapper;

    @Autowired
    private WantRMapper wantRMapper;

    @Autowired
    private SecondsDMapper secondsDMapper;

    @Autowired
    private CategoryTService categoryTService;


    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);


    /**
     * 分页查询
     * @param spuQueryByPageParameter
     * @return
     */
    @Override
    public PageResult<SecondsBo> querySecondsByPageAndSort(SpuQueryByPageParameter spuQueryByPageParameter) {

        //1.查询spu，分页查询，最多查询100条
        PageHelper.startPage(spuQueryByPageParameter.getPage(),Math.min(spuQueryByPageParameter.getRows(),100));

        //2.创建查询条件
        Example example = new Example(Seconds.class);
        Example.Criteria criteria = example.createCriteria();

        //3.条件过滤
        //3.1 是否过滤上下架
        if (spuQueryByPageParameter.getSaleable() != null){
            System.out.println(spuQueryByPageParameter.getSaleable());
            criteria.orEqualTo("saleable",spuQueryByPageParameter.getSaleable());
        }
        //3.2 是否模糊查询
        if (StringUtils.isNotBlank(spuQueryByPageParameter.getKey())){
            criteria.andLike("title","%"+spuQueryByPageParameter.getKey()+"%");
        }
        //3.3 是否排序
        if (StringUtils.isNotBlank(spuQueryByPageParameter.getSortBy())){
            example.setOrderByClause(spuQueryByPageParameter.getSortBy()+(spuQueryByPageParameter.getDesc()? " DESC":" ASC"));
        }
        Page<Seconds> pageInfo = (Page<Seconds>) this.secondsMapper.selectByExample(example);
        System.out.println("pageInfo:"+pageInfo);

        List<SecondsBo> list = pageInfo.getResult().stream().map(seconds -> {
            SecondsBo secondsBo = new SecondsBo();
            //1.属性拷贝
            BeanUtils.copyProperties(seconds,secondsBo);

            //2.查询spu的商品分类名称，各级分类
            List<String> nameList = this.categoryTService.queryNameByIds(Arrays.asList(seconds.getCid()));
            //3.拼接名字,并存入
            secondsBo.setCname(StringUtils.join(nameList,"/"));
            return secondsBo;
        }).collect(Collectors.toList());

        return new PageResult<>(pageInfo.getTotal(),list);
    }





    /**
     * 根据skuId查询sku
     * @param id
     * @return
     */
    @Override
    public SecondsD querySkuById(Long id) {
        return this.secondsDMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存商品
     * @param secondsR
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSeconds(SecondsRBo secondsR) {
        //保存spu
        secondsR.setSaleable(true);
        secondsR.setCreate_time(new Date());
        secondsR.setUpdate_time(secondsR.getCreate_time());
        secondsR.setUser_id(secondsR.getUser_id());
        secondsR.setCid(secondsR.getCid());
        secondsR.setBuy_price(secondsR.getBuy_price());
        secondsR.setSell_price(secondsR.getSell_price());
        secondsR.setImage(secondsR.getImage());
        secondsR.setFlag(1);
        secondsR.setRecommend(0);
        secondsR.setView_number(0);
        secondsR.setDetail(secondsR.getDetail());
        this.secondsRMapper.insert(secondsR);

        //发送消息到mq
        this.sendMessage(secondsR.getId(),"insert");
    }



    /**
     * 商品删除二合一（多个单个）
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGoods(long id) {
        //删除spu表中的数据
        this.secondsMapper.deleteByPrimaryKey(id);

        //发送消息到mq
        this.sendMessage(id,"delete");

    }


    /**
     * 商品下架
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void goodsSoldOut(Long id) {
        //下架或者上架spu中的商品

        Seconds oldSpu = this.secondsMapper.selectByPrimaryKey(id);
        Example example = new Example(SecondsD.class);
        example.createCriteria().andEqualTo("id",id);
        List<Seconds> skuList = this.secondsMapper.selectByExample(example);
        if (oldSpu.getSaleable()){
            //下架
            oldSpu.setSaleable(false);
            this.secondsMapper.updateByPrimaryKeySelective(oldSpu);
        }else {
            //上架
            oldSpu.setSaleable(true);
            this.secondsMapper.updateByPrimaryKeySelective(oldSpu);
        }

        //发送消息到mq
        this.sendMessage(id,"update");
    }

    /**
     * 更新商品信息
     * @param secondsBo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGoods(SecondsBo secondsBo) {
        /**
         * 更新策略：
         *      1.判断tb_spu_detail中的spec_template字段新旧是否一致
         *      2.如果一致说明修改的只是库存、价格和是否启用，那么就使用update
         *      3.如果不一致，说明修改了特有属性，那么需要把原来的sku全部删除，然后添加新的sku
         */

        //更新spu
        secondsBo.setSaleable(true);
        secondsBo.setUpdate_time(new Date());
        this.secondsMapper.updateByPrimaryKeySelective(secondsBo);

        //发送消息到mq
        this.sendMessage(secondsBo.getId(),"update");
    }


    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @Override
    public SecondsBo querySecondsById(Long id) {
        /**
         * 第一页所需信息如下：
         * 1.商品的分类信息、所属品牌、商品标题、商品卖点（子标题）
         * 2.商品的包装清单、售后服务
         */
        Seconds seconds=this.secondsMapper.selectByPrimaryKey(id);

        Example example = new Example(SecondsD.class);
        example.createCriteria().andEqualTo("id",seconds.getId());
        List<SecondsD> skuList = this.secondsDMapper.selectByExample(example);

        SecondsBo secondsBo = new SecondsBo(seconds.getId(),seconds.getCreate_time(),seconds.getUpdate_time(),seconds.getBuy_price(),seconds.getSell_price(),seconds.getDetail(),
                seconds.getFlag(),seconds.getTitle(),seconds.getImage(),seconds.getRecommend(),seconds.getSaleable(),seconds.getCid(),seconds.getUser_id(),seconds.getView_number());
        secondsBo.setSkus(skuList);
        return secondsBo;
    }


    @Override
    public List<SecondsD> queryGoodsById(Long id) {
        Example example = new Example(SecondsD.class);
        example.createCriteria().andEqualTo("id",id);
        List<SecondsD> skuList = this.secondsDMapper.selectByExample(example);
        return skuList;
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
        }catch (Exception e){
            LOGGER.error("{}商品消息发送异常，商品id：{}",type,id,e);
        }
    }


}
