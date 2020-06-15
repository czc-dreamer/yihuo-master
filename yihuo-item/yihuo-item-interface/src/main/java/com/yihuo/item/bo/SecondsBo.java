package com.yihuo.item.bo;

import com.yihuo.item.pojo.Seconds;
import com.yihuo.item.pojo.SecondsD;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class SecondsBo extends Seconds {
    @Transient
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    public List<SecondsD> getSkus() {
        return skus;
    }

    public void setSkus(List<SecondsD> skus) {
        this.skus = skus;
    }
    /**
     * 商品分类名称
     */
    @Transient
    private String cname;




    @Transient
    private List<SecondsD> skus;



    public SecondsBo(){

    }

    public SecondsBo(long id, Date create_time, Date update_time, Integer buy_price, Integer sell_price, String detail, Integer flag, String title, String image,
                      Integer recommend, Boolean saleable, Long cid, Long user_id, Integer view_number){
        super(id,create_time,update_time,buy_price,sell_price,detail,flag,title,image,recommend,saleable,cid,user_id,view_number);
    }
}
