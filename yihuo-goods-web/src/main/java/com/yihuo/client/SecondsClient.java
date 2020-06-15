package com.yihuo.client;

import com.yihuo.item.api.SecondsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface SecondsClient extends SecondsApi {
}
