package com.yihuo.client;

import com.yihuo.item.api.CategoryTApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface CategoryTClient extends CategoryTApi {
}
