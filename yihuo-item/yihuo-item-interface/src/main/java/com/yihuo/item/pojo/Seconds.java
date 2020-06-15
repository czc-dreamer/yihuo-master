package com.yihuo.item.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_goods")
public class Seconds {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(Integer buy_price) {
        this.buy_price = buy_price;
    }

    public Integer getSell_price() {
        return sell_price;
    }

    public void setSell_price(Integer sell_price) {
        this.sell_price = sell_price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Boolean getSaleable() {
        return saleable;
    }

    public void setSaleable(Boolean saleable) {
        this.saleable = saleable;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getView_number() {
        return view_number;
    }

    public void setView_number(Integer view_number) {
        this.view_number = view_number;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date create_time;
    private Date update_time;
    private Integer buy_price;
    private Integer sell_price;
    private String detail;
    private Integer flag;
    private String title;
    private String image;
    private Integer recommend;
    private Boolean saleable;
    private Long cid;
    private Long user_id;
    private Integer view_number;

    public Seconds(){

    }

    public Seconds(long id, Date create_time,Date update_time,Integer buy_price,Integer sell_price,String detail,Integer flag,String title,String image,
                   Integer recommend,Boolean saleable,Long cid,Long user_id,Integer view_number){
        this.id=id;
        this.create_time=create_time;
        this.update_time=update_time;
        this.buy_price=buy_price;
        this.sell_price=sell_price;
        this.detail=detail;
        this.flag=flag;
        this.title=title;
        this.image=image;
        this.recommend=recommend;
        this.saleable=saleable;
        this.cid=cid;
        this.user_id=user_id;
        this.view_number=view_number;
    }
}
