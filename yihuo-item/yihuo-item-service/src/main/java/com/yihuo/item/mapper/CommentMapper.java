package com.yihuo.item.mapper;

import com.yihuo.item.pojo.Comment;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface CommentMapper extends Mapper<Comment> {
    @Select("SELECT * FROM tb_comment  WHERE goods_id=#{id}")
    List<Comment> queryCommentById(Long id);
}
