package com.yihuo.item.bo;

import com.yihuo.item.pojo.SecondsR;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class SecondsRBo extends SecondsR {
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<SecondsR> getSecondsR() {
        return secondsR;
    }

    public void setSecondsR(List<SecondsR> secondsR) {
        this.secondsR = secondsR;
    }

    /**
     * 商品分类名称
     */
    @Transient
    private String cname;

    @Transient
    private List<SecondsR> secondsR;

    public SecondsRBo(){

    }

    public SecondsRBo(Date create_time, Date update_time, Integer buy_price, Integer sell_price, String detail, Integer flag, String title, String image,
                     Integer recommend, Boolean saleable, Long cid, Long user_id, Integer view_number){
        super(create_time,update_time,buy_price,sell_price,detail,flag,title,image,recommend,saleable,cid,user_id,view_number);
    }
}
