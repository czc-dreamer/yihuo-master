package com.yihuo.item.controller;

import com.yihuo.item.pojo.CategoryT;
import com.yihuo.item.service.CategoryTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categoryT")
public class CategoryTController {
    @Autowired
    private CategoryTService categoryTService;
    @GetMapping("/list")
    public ResponseEntity<List<CategoryT>> queryCategoryByCid(@RequestParam("pid") Long cid){

        List<CategoryT> list = this.categoryTService.queryCategoryByCid(cid);
        if (list == null) {
            //没有找到返回404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        //找到返回200
        return ResponseEntity.ok(list);

    }

    /**
     * 根据分类id集合查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids){
        List<String> list = categoryTService.queryNameByIds(ids);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 更新
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(CategoryT categoryT){
        this.categoryTService.updateCategory(categoryT);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    
    /**
     * 根据分类id集合查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("all")
    public ResponseEntity<List<CategoryT>> queryCategoryByIds(@RequestParam("ids")List<Long> ids){
        List<CategoryT> list = categoryTService.queryCategoryByIds(ids);
        System.out.println("list="+list);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            System.out.println("触发了：："+ResponseEntity.ok(list));
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 根据分类id集合查询分类名称
     * @param id
     * @return
     */
    @GetMapping("all/level/{cid}")
    public ResponseEntity<List<CategoryT>> queryAllCategoryTLevelByCid(@PathVariable("cid")Long id){
        List<CategoryT> list = categoryTService.queryAllCategoryTLevelByCid(id);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(list);
        }
    }


}
