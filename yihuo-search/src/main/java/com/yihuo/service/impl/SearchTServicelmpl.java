package com.yihuo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihuo.bo.SearchRequest;
import com.yihuo.client.CategoryTClient;
import com.yihuo.client.SecondsClient;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.item.pojo.CategoryT;
import com.yihuo.item.pojo.Seconds;
import com.yihuo.item.pojo.SecondsD;
import com.yihuo.pojo.Goods;
import com.yihuo.repository.SecondsRepository;
import com.yihuo.service.SearchTService;
import com.yihuo.vo.SearchResultF;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SearchTServicelmpl implements SearchTService {
    @Autowired
    private CategoryTClient categoryTClient;

    @Autowired
    private SecondsClient secondsClient;

    @Autowired
    private SecondsRepository secondsRepository;

    private ObjectMapper mapper = new ObjectMapper();
    /**
     * 查询商品信息
     *
     * @param seconds
     * @return
     * @throws IOException
     */
    @Override
    public Goods buildGoods(Seconds seconds) throws IOException {
        Goods goods = new Goods();

        //1.查询商品分类名称
        List<String> names = this.categoryTClient.queryNameByIds(Arrays.asList(seconds.getCid())).getBody();
        //2.查询sku
        List<SecondsD> secondsDS =  this.secondsClient.queryGoodsById(seconds.getId());

//        System.out.println("secondsDS:"+secondsDS);
        //3.查询详情
//        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spu.getId());

        //4.处理sku,仅封装id，价格、标题、图片、并获得价格集合
        List<Map<String,Object>> skuLists = new ArrayList<>();
        secondsDS.forEach(sku -> {
            Map<String,Object> skuMap = new HashMap<>();
            skuMap.put("id",sku.getId());
            skuMap.put("title",sku.getTitle());
            skuMap.put("price",sku.getSell_price());
            //取第一张图片
            skuMap.put("image", StringUtils.isBlank(sku.getImage()) ? "" : StringUtils.split(sku.getImage(),",")[0]);
            skuLists.add(skuMap);
        });

        goods.setId(seconds.getId());
        goods.setCid(seconds.getCid());
        goods.setCreateTime(seconds.getCreate_time());
        goods.setAll(seconds.getTitle() + " " + StringUtils.join(names, " "));
        goods.setPrice(seconds.getSell_price());
        goods.setSkus(mapper.writeValueAsString(skuLists));
        return goods;

    }

    /**
     * 搜索
     *
     * @param searchRequest
     * @return
     */
    @Override
    public SearchResultF<Goods> search(SearchRequest searchRequest) {
        String key = searchRequest.getKey();
        System.out.println("service取到的searchRequest="+searchRequest);
        System.out.println("service取到的key="+key);

        /**
         * 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
         */
        if (StringUtils.isBlank(key)) {
            return null;
        }
        //创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //1.构建查询条件
        //1.1.对关键字进行全文检索查询
        QueryBuilder basicQuery = this.buildBasicQueryWithFilter(searchRequest);
        queryBuilder.withQuery(basicQuery);
        //1.2.通过sourceFilter设置返回的结果字段，只需要id,skus,subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus"}, null));
        //1.3.分页和排序
        searchWithPageAndSort(queryBuilder, searchRequest);
        //1.4.聚合
        /**
         * 商品分类聚合名称
         */
        String categoryAggName = "category";

        //1.4.1。对商品分类进行聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid"));
//        System.out.println("queryBuilder.addAggregation:"+queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid")));
        System.out.println("queryBuilder.build()="+queryBuilder.build());
        //2.查询、获取结果
        AggregatedPage<Goods> pageInfo = (AggregatedPage<Goods>) this.secondsRepository.search(queryBuilder.build());
        System.out.println("pageInfo:"+pageInfo);
        //3.解析查询结果
        //3.1 分页信息
        Long total = pageInfo.getTotalElements();
        int totalPage = pageInfo.getTotalPages();
        System.out.println("total="+total);
        System.out.println("totalPage="+totalPage);
        //3.2 商品分类的聚合结果
        System.out.println("getAggregation"+pageInfo.getAggregation(categoryAggName));
        List<CategoryT> categories = getCategoryAggResult(pageInfo.getAggregation(categoryAggName));
        System.out.println("categoryes="+categories);
        //3.3 品牌的聚合结果
        System.out.println("pageInfo内容："+pageInfo.getContent());

        //4.封装结果，返回
        return new SearchResultF<>(total, (long) totalPage, pageInfo.getContent(), categories);
    }

    /**
     * 解析商品分类聚合结果，其中都是三级分类
     *
     * @param aggregation
     * @return
     */
    private List<CategoryT> getCategoryAggResult(Aggregation aggregation) {
        LongTerms categoryAgg = (LongTerms) aggregation;
        System.out.println("categoryAgg="+categoryAgg);
        List<Long> cids = new ArrayList<>();
        for (LongTerms.Bucket bucket : categoryAgg.getBuckets()) {

            cids.add(bucket.getKeyAsNumber().longValue());
            System.out.println("cids"+cids);
        }
        //根据id查询分类名称
        return this.categoryTClient.queryCategoryByIds(cids).getBody();
    }
    /**
     * 构建带过滤条件的基本查询
     *
     * @param searchRequest
     * @return
     */
    private QueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //基本查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()).operator(Operator.AND));
        //过滤条件构造器
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        //整理过滤条件
        Map<String, String> filter = searchRequest.getFilter();
        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String regex = "^(\\d+\\.?\\d*)-(\\d+\\.?\\d*)$";
            if (!"key".equals(key)) {
                if ("price".equals(key)) {
                    if (!value.contains("元以上")) {
                        String[] nums = StringUtils.substringBefore(value, "元").split("-");
                        filterQueryBuilder.must(QueryBuilders.rangeQuery(key).gte(Long.valueOf(nums[0]) * 100).lt(Long.valueOf(nums[1]) * 100));
                    } else {
                        String num = StringUtils.substringBefore(value, "元以上");
                        filterQueryBuilder.must(QueryBuilders.rangeQuery(key).gte(Long.valueOf(num) * 100));
                    }
                }
            } else {
                break;
            }
        }
        //添加过滤条件
        queryBuilder.filter(filterQueryBuilder);
        return queryBuilder;
    }

    /**
     * 构建基本查询条件
     *
     * @param queryBuilder
     * @param request
     */
    private void searchWithPageAndSort(NativeSearchQueryBuilder queryBuilder, SearchRequest request) {
        // 准备分页参数
        int page = request.getPage();
        int size = request.getDefaultSize();

        // 1、分页
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 2、排序
        String sortBy = request.getSortBy();
        Boolean desc = request.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            // 如果不为空，则进行排序
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }
    }
    /**
     * 创建索引
     *
     * @param id
     */
    @Override
    public void createIndex(Long id) throws IOException {
        SecondsBo secondsBo = this.secondsClient.querySecondsById(id);
        //构建商品
        Goods goods = this.buildGoods(secondsBo);

        //保存数据到索引库中
        this.secondsRepository.save(goods);
    }

    /**
     * 删除索引
     *
     * @param id
     */
    @Override
    public void deleteIndex(Long id) {

        this.secondsRepository.deleteById(id);
    }


}
