package com.yihuo.service;

import com.yihuo.bo.SearchRequest;
import com.yihuo.item.pojo.Seconds;
import com.yihuo.pojo.Goods;
import com.yihuo.vo.SearchResultF;

import java.io.IOException;

public interface SearchTService {
    /**
     * 查询商品信息
     * @param seconds
     * @return
     * @throws IOException
     */
    Goods buildGoods(Seconds seconds) throws IOException;

    /**
     * 商品搜索
     * @param searchRequest
     * @return
     */
    SearchResultF<Goods> search(SearchRequest searchRequest);

    /**
     * 根据goods的id创建相应的索引
     * @param id
     * @throws IOException
     */
    void createIndex(Long id) throws IOException;

    /**
     * 根据goods的id删除相应的索引
     * @param id
     */
    void deleteIndex(Long id);
}
