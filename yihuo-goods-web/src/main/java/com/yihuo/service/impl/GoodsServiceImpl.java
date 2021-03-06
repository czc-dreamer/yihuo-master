package com.yihuo.service.impl;

import com.yihuo.client.*;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.item.pojo.CategoryT;
import com.yihuo.item.pojo.SecondsD;
import com.yihuo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 98050
 * Time: 2018-10-17 19:40
 * Feature:商品详情页信息
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SecondsClient secondsClient;

    @Autowired
    private CategoryTClient categoryTClient;

    @Autowired
    private UserClient userClient;


    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Override
    public Map<String, Object> loadModel(Long spuId) throws InterruptedException, ExecutionException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

/*        SpuBo spuBo = executorService.submit(() -> {
            countDownLatch.countDown();
            return this.goodsClient.queryGoodsById(spuId);
        }).get();*/

/*        Brand brand = executorService.submit(() -> {
            countDownLatch.countDown();
            return this.brandClient.queryBrandByIds(Collections.singletonList(spuBo.getBrandId())).get(0);
        }).get();*/

        SecondsBo secondsBo=executorService.submit(()->{
            countDownLatch.countDown();
            return this.secondsClient.querySecondsById(spuId);
        }).get();
        System.out.println("secondsBo:"+secondsBo);
        System.out.println("secondsBo.getid:"+secondsBo.getCid());
        countDownLatch.await();

//        Seconds seconds = secondsBo.getDetail();
        List<SecondsD> skuList=secondsBo.getSkus();
        List<Long> ids = new ArrayList<>();
        ids.add(secondsBo.getCid());
/*        ids.add(spuBo.getCid2());
        ids.add(spuBo.getCid3());*/
        System.out.println("skuList:"+skuList);
        System.out.println("ids:"+ids);
        List<CategoryT> categoryList = executorService.submit(() -> this.categoryTClient.queryCategoryByIds(ids).getBody()).get();

        System.out.println("categoryList:"+categoryList);
        /**
         * 对于规格属性的处理需要注意以下几点：
         *      1. 所有规格都保存为id和name形式
         *      2. 规格对应的值保存为id和value形式
         *      3. 都是map形式
         *      4. 将特有规格参数单独抽取
         */

        Map<String,Object> map = new HashMap<>();
        map.put("spu",secondsBo);

        map.put("skus",skuList);

        map.put("categories",categoryList);

        map.put("userInfo",this.userClient.queryUserInfoById(secondsBo.getUser_id()));


        System.out.println("map:"+map);
        return map;
    }
/*
    private List<Map<String, Object>> getGroupsSpec(List<Map<String, Object>> allSpecs, Map<Integer, String> specName, Map<Integer, Object> specValue) {
        List<Map<String, Object>> groups = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (Map<String,Object> spec :allSpecs){
            List<Map<String, Object>> params = (List<Map<String, Object>>) spec.get("params");
            List<Map<String,Object>> temp = new ArrayList<>();
            for (Map<String,Object> param :params) {
                for (Map.Entry<Integer, String> entry : specName.entrySet()) {
                    if (entry.getValue().equals(param.get("k").toString())) {
                        String value = specValue.get(entry.getKey()) != null ? specValue.get(entry.getKey()).toString() : "无";
                        Map<String, Object> temp3 = new HashMap<>(16);
                        temp3.put("id", ++j);
                        temp3.put("name", entry.getValue());
                        temp3.put("value", value);
                        temp.add(temp3);
                    }
                }
            }
            Map<String,Object> temp2 = new HashMap<>(16);
            temp2.put("params",temp);
            temp2.put("id",++i);
            temp2.put("name",spec.get("group"));
            groups.add(temp2);
        }
        return groups;
    }

    private void getSpecialSpec(Map<String, String[]> specs, Map<Integer, String> specName, Map<Integer, Object> specValue, Map<Integer, String> specialParamName, Map<Integer, String[]> specialParamValue) {
        if (specs != null) {
            for (Map.Entry<String, String[]> entry : specs.entrySet()) {
                String key = entry.getKey();
                for (Map.Entry<Integer,String> e : specName.entrySet()) {
                    if (e.getValue().equals(key)){
                        specialParamName.put(e.getKey(),e.getValue());
                        //因为是放在数组里面，所以要先去除两个方括号，然后再以逗号分割成数组
                        String  s = specValue.get(e.getKey()).toString();
                        String result = StringUtils.substring(s,1,s.length()-1);
                        specialParamValue.put(e.getKey(), result.split(","));
                    }
                }
            }
        }
    }

    private void getAllSpecifications(List<Map<String, Object>> allSpecs, Map<Integer, String> specName, Map<Integer, Object> specValue) {
        String k = "k";
        String v = "v";
        String unit = "unit";
        String numerical = "numerical";
        String options ="options";
        int i = 0;
        if (allSpecs != null){
            for (Map<String,Object> s : allSpecs){
                List<Map<String, Object>> params = (List<Map<String, Object>>) s.get("params");
                for (Map<String,Object> param :params){
                    String result;
                    if (param.get(v) == null){
                        result = "无";
                    }else{
                        result = param.get(v).toString();
                    }
                    if (param.containsKey(numerical) && (boolean) param.get(numerical)) {
                        if (result.contains(".")){
                            Double d = Double.valueOf(result);
                            if (d.intValue() == d){
                                result = d.intValue()+"";
                            }
                        }
                        i++;
                        specName.put(i,param.get(k).toString());
                        specValue.put(i,result+param.get(unit).toString());
                    } else if (param.containsKey(options)){
                        i++;
                        specName.put(i,param.get(k).toString());
                        specValue.put(i,param.get(options));
                    }else {
                        i++;
                        specName.put(i,param.get(k).toString());
                        specValue.put(i,param.get(v));
                    }
                }
            }
        }
    }*/

}
