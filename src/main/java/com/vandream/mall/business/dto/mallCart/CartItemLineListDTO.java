package com.vandream.mall.business.dto.mallCart;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/5/15
 * @time 14:19
 * Description:
 */
@Data
@Setter
@Getter
public class CartItemLineListDTO {
    /**
     *商品参数
     */
    private List<GoodsDetailDTO> items=new ArrayList<>();
    /**
     * 面包屑
     */
    private  List<String> categoryStrs=new ArrayList<>();

    public void addItems(GoodsDetailDTO goodsDetailDTO){
        this.items.add(goodsDetailDTO);
    }
    public  void addCategoryList(String cageStr){
        this.categoryStrs.add(cageStr);
    }

}
