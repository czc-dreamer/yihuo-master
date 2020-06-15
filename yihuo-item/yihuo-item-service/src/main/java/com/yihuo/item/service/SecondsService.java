package com.yihuo.item.service;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.item.bo.SecondsRBo;
import com.yihuo.item.pojo.SecondsD;
import com.yihuo.parameter.pojo.SpuQueryByPageParameter;

import java.util.List;

public interface SecondsService {

    /**
     * 分页查询
     * @param spuQueryByPageParameter
     * @return
     */
    PageResult<SecondsBo> querySecondsByPageAndSort(SpuQueryByPageParameter spuQueryByPageParameter);
    /**
     * 保存商品
     * @param secondsR
     */
    void saveSeconds(SecondsRBo secondsR);



    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    SecondsBo querySecondsById(Long id);


    /**
     * 根据Sku的id查询其下所有的sku
     * @param id
     * @return
     */
    List<SecondsD> queryGoodsById(Long id);

    /**
     * 发送校区到mq
     * @param id
     * @param type
     */
    void sendMessage(Long id,String type);

    /**
     * 更新商品信息
     * @param secondsBo
     */
    void updateGoods(SecondsBo secondsBo);

    /**
     * 商品下架
     * @param id
     */
    void goodsSoldOut(Long id);

    void deleteGoods(long id);

    SecondsD querySkuById(Long id);


}
