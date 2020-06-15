package com.yihuo.item.controller;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.WantGoodsBo;
import com.yihuo.item.pojo.WantGoods;
import com.yihuo.item.service.WantService;
import com.yihuo.parameter.pojo.BrandQueryByPageParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("want")
public class WantController {

    @Autowired
    private WantService wantService;
    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<WantGoodsBo>> queryUserByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                  @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                                  @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                  @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                                  @RequestParam(value = "key", required = false) String key){
        BrandQueryByPageParameter brandQueryByPageParameter=new BrandQueryByPageParameter(page,rows,sortBy,desc,key);
        PageResult<WantGoodsBo> result = this.wantService.queryWantByPage(brandQueryByPageParameter);
        if(result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("want")
    public ResponseEntity<Void> saveWant(@RequestBody WantGoods wantGoods){
        this.wantService.saveWant(wantGoods);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/want/{id}")
    public ResponseEntity<WantGoods> queryWantById(@PathVariable("id") Long id){
        System.out.println("wantid:"+id);
        WantGoods wantGoods=this.wantService.queryWantById(id);
        System.out.println("wantGoods:"+wantGoods);
        if (wantGoods == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(wantGoods);
    }

    /**
     * 删除求购商品
     * @param ids
     * @return
     */
    @DeleteMapping("/want/{id}")
    public ResponseEntity<Void> deleteWant(@PathVariable("id") String ids){
        String separator="-";
        if (ids.contains(separator)){
            String[] goodsId = ids.split(separator);
            for (String id:goodsId){
                this.wantService.deleteWant(Long.parseLong(id));
            }
        }
        else {
            this.wantService.deleteWant(Long.parseLong(ids));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改商品
     * @param wantGoods
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateWant(@RequestBody WantGoods wantGoods){
        this.wantService.updateWant(wantGoods);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
