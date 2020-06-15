package com.yihuo.item.bo;


import com.yihuo.item.pojo.UserR;
import com.yihuo.item.pojo.WantGoods;

import java.util.Date;

public class WantGoodsBo extends WantGoods {

    public UserR getUserRS() {
        return userRS;
    }

    public void setUserRS(UserR userRS) {
        this.userRS = userRS;
    }

    UserR userRS;



    public WantGoodsBo(){}

    public WantGoodsBo(Long user_id, String title, Float sell_price, String trade_place, String content, Integer view_number, Date create_time, Date update_time){
        super(user_id,title,sell_price,trade_place,content,view_number,create_time,view_number,update_time);
    }
}
