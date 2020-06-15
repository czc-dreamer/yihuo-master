package com.yihuo.item.service;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.WantGoodsBo;
import com.yihuo.item.pojo.WantGoods;
import com.yihuo.parameter.pojo.BrandQueryByPageParameter;

public interface WantService {
    PageResult<WantGoodsBo> queryWantByPage(BrandQueryByPageParameter brandQueryByPageParameter);

    void saveWant(WantGoods wantGoods);

    WantGoods queryWantById(Long id);

    void deleteWant(long parseLong);

    /**
     * 发送校区到mq
     * @param id
     * @param type
     */
    void sendMessage(Long id,String type);

    void updateWant(WantGoods wantGoods);
}
