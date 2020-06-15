package com.yihuo.item.controller;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.item.bo.SecondsRBo;
import com.yihuo.item.pojo.SecondsD;
import com.yihuo.item.service.SecondsService;
import com.yihuo.parameter.pojo.SpuQueryByPageParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("seconds")
public class SecondsController {
    @Autowired
    private SecondsService secondsService;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @param saleable
     * @return
     */
    @GetMapping("/seconds/page")
    public ResponseEntity<PageResult<SecondsBo>> querySecondsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable){
        SpuQueryByPageParameter spuQueryByPageParameter = new SpuQueryByPageParameter(page,rows,sortBy,desc,key,saleable);
        //分页查询spu信息
        PageResult<SecondsBo> result = this.secondsService.querySecondsByPageAndSort(spuQueryByPageParameter);
        System.out.println("查询数据量："+result.getTotal());
        return ResponseEntity.ok(result);
    }


    /**
     * 根据id查询sku
     * @param id
     * @return
     */
    @GetMapping("/sku/{id}")
    public ResponseEntity<SecondsD> querySkuById(@PathVariable("id") Long id){
        SecondsD secondsD = this.secondsService.querySkuById(id);
        if (secondsD == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(secondsD);
    }
    /**
     * 保存商品
     * @param secondsR
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveSeconds(@RequestBody SecondsRBo secondsR){
        this.secondsService.saveSeconds(secondsR);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    /**
     * 修改商品
     * @param secondsBo
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody SecondsBo secondsBo){
        this.secondsService.updateGoods(secondsBo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 商品上下架
     * @param ids
     * @return
     */
    @PutMapping("/seconds/out/{id}")
    public ResponseEntity<Void> goodsSoldOut(@PathVariable("id") String ids){

        String separator="-";
        if (ids.contains(separator)){
            String[] goodsId = ids.split(separator);
            for (String id:goodsId){
                this.secondsService.goodsSoldOut(Long.parseLong(id));
            }
        }
        else {
            this.secondsService.goodsSoldOut(Long.parseLong(ids));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/seconds/{id}")
    public ResponseEntity<SecondsBo> querySecondsById(@PathVariable("id") Long id){
        SecondsBo secondsBo=this.secondsService.querySecondsById(id);
        if (secondsBo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(secondsBo);
    }

    /**
     * 根据Spu的id查询其下所有的sku
     * @param id
     * @return
     */
    @GetMapping("seconds/list/{id}")
    public ResponseEntity<List<SecondsD>> queryGoodsById(@PathVariable("id") Long id){
        List<SecondsD> list = this.secondsService.queryGoodsById(id);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/seconds/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") String ids){
        String separator="-";
        if (ids.contains(separator)){
            String[] goodsId = ids.split(separator);
            for (String id:goodsId){
                this.secondsService.deleteGoods(Long.parseLong(id));
            }
        }
        else {
            this.secondsService.deleteGoods(Long.parseLong(ids));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
