package com.yihuo.item.api;

import com.yihuo.common.pojo.PageResult;
import com.yihuo.item.bo.SecondsBo;
import com.yihuo.item.pojo.SecondsD;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("seconds")

public interface SecondsApi {

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
    PageResult<SecondsBo> querySecondsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/seconds/{id}")
    SecondsBo querySecondsById(@PathVariable("id") Long id);


    /**
     * 根据id查询sku
     * @param id
     * @return
     */
    @GetMapping("/sku/{id}")
    SecondsD querySkuById(@PathVariable("id") Long id);
    /**
     * 根据Spu的id查询其下所有的sku
     * @param id
     * @return
     */
    @GetMapping("seconds/list/{id}")
    List<SecondsD> queryGoodsById(@PathVariable("id") Long id);
}
