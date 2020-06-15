package com.yihuo.item.controller;


import com.yihuo.item.pojo.Comment;
import com.yihuo.item.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    /**
     * 保存商品
     * @param comment
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveComment(@RequestBody Comment comment){
        this.commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Comment>> queryCommentById(@PathVariable("id") Long id){
        List<Comment> comment = this.commentService.queryCommentById(id);
        if (comment == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comment);
    }
}
