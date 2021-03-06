package com.yihuo.item.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="tb_wanted_goods")
public class WantGoodsR {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getSell_price() {
        return sell_price;
    }

    public void setSell_price(Float sell_price) {
        this.sell_price = sell_price;
    }

    public String getTrade_place() {
        return trade_place;
    }

    public void setTrade_place(String trade_place) {
        this.trade_place = trade_place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getView_number() {
        return view_number;
    }

    public void setView_number(Integer view_number) {
        this.view_number = view_number;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private String title;//物品名称

    private Float sell_price;//期望价格

    private String trade_place;//期望交易地点

    private String content;//物品详情描述

    private Integer view_number;//物品浏览量
    private Date create_time;
    private Date update_time;

    @Override
    public String toString() {
        return "WantGoodsR{" +
                "id=" + id +
                ", create_time=" + create_time +
                ", update_time="+update_time+
                ", sell_price=" + sell_price +
                ", content=" + content +
                ", title=" + title  +
                ", user_id="+user_id+
                ", view_number="+view_number+
                '}';
    }
}
