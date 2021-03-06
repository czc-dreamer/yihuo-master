package com.yihuo.order.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 98050
 * @create: 2018-10-27
 **/
//@ConfigurationProperties(prefix = "yihuo.worker")
@Configuration
@RefreshScope
public class IdWorkerProperties {

    /**
     * 当前机器id
     */
    @Value("${yihuo.worker.workerId}")
    private long workerId;

    /**
     * 序列号
     */
    @Value("${yihuo.worker.dataCenterId}")
    private long dataCenterId;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
