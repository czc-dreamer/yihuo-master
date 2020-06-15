package com.yihuo.item.bo;

import com.yihuo.item.pojo.Comment;

import java.util.Date;
import java.util.List;

public class CommentBo extends Comment {
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String head;
    public String username;
    public List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public CommentBo(){}

    public CommentBo(Long id, Long user_id, Long goods_id, String reply_to, String content, Date create_time, Date update_time,String username,String head){
        super(id,user_id,goods_id,reply_to,content,create_time,update_time,username,head);
    }
}
