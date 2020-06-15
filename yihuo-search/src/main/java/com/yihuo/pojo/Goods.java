package com.yihuo.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
public class Goods {
    @Id
    /**
     * spuId
     */
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    /**
     * 所有需要被搜索的信息，包含标题，分类，甚至品牌
     */
    private String all;



    /**
     * cid
     */
    private Long cid;
    /**
     * 创建时间
     */
    private Date createTime;



    /**
     * 价格
     */
    private Integer price;


    @Field(type = FieldType.Keyword, index = false)
    /**
     * sku信息的json结构
     */
    private String skus;


    public Goods() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", all='" + all  +'\''+
                ", cid=" + cid +
                ", createTime=" + createTime +
                ", price=" + price +
                ", skus='" + skus  +'\''+
                '}';
    }
}
