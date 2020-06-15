package com.yihuo.repository;

import com.yihuo.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SecondsRepository extends ElasticsearchRepository<Goods,Long> {
}
