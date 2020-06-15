package com.yihuo.cart.client;

import com.yihuo.item.api.SecondsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: 98050
 * @Time: 2018-10-25 21:03
 * @Feature: 商品FeignClient
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends SecondsApi {
}
