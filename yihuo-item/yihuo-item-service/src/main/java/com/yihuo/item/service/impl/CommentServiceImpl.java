package com.yihuo.item.service.impl;


import com.yihuo.item.mapper.CommentMapper;
import com.yihuo.item.mapper.UserMapper;
import com.yihuo.item.pojo.Comment;
import com.yihuo.item.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);

    /**
     * 保存商品
     *
     * @param comment
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveComment(Comment comment) {
        //保存spu
        comment.setCreate_time(new Date());
        comment.setUpdate_time(comment.getCreate_time());
        this.commentMapper.insert(comment);

        //发送消息到mq
        this.sendMessage(comment.getId(), "insert");
    }

    @Override
    public List<Comment> queryCommentById(Long id){
        List<Comment> comment=this.commentMapper.queryCommentById(id);

        return comment;
    }

    @Override
    public void sendMessage(Long id, String type) {
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            LOGGER.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }
}
