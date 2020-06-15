package com.yihuo.controller;

import com.yihuo.bo.SearchRequest;
import com.yihuo.client.SecondsClient;
import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.pojo.Goods;
import com.yihuo.repository.SecondsRepository;
import com.yihuo.service.impl.SearchTServicelmpl;
import com.yihuo.vo.SearchResultF;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-12 20:21
 * Feature:
 */
@RestController
@RequestMapping
public class SearchTController implements InitializingBean {
    @Autowired
    private SearchTServicelmpl searchTService;

    @Autowired
    private SecondsRepository secondsRepository;

    @Autowired
    private SecondsClient secondsClient;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("pageT")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest searchRequest){
        SearchResultF<Goods> result = this.searchTService.search(searchRequest);
        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            System.out.println("查询结果："+result);
            return ResponseEntity.ok(result);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 创建索引
        this.elasticsearchTemplate.createIndex(Goods.class);
        // 配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);

        //加载数据
        List<SecondsBo> list = new ArrayList<>();
        int page = 1;
        int row = 50;
        int size;
        do {
            //分页查询数据
            PageResult<SecondsBo> result = this.secondsClient.querySecondsByPage(page, row, null, true, null, true);
            List<SecondsBo> spus = result.getItems();
            size = spus.size();
            page ++;
            list.addAll(spus);
        }while (size == 50);
        System.out.println("list="+list);
        //创建Goods集合
        List<Goods> secondsList = new ArrayList<>();
        //遍历spu
        for (SecondsBo secondsBo : list) {
            try {
                Goods goods = this.searchTService.buildGoods(secondsBo);
                secondsList.add(goods);
            } catch (IOException e) {
                System.out.println("查询失败：" + secondsBo.getId());
            }
        }
        this.secondsRepository.saveAll(secondsList);
    }
}
