package com.yihuo.item.service;

import com.yihuo.item.pojo.Comment;

import java.util.List;

public interface CommentService {



    void saveComment(Comment comment);

    /**
     * 发送校区到mq
     * @param id
     * @param type
     */
    void sendMessage(Long id,String type);

    List<Comment> queryCommentById(Long id);
}
