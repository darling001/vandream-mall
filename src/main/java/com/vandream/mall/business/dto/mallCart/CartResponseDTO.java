package com.vandream.mall.business.dto.mallCart;

import com.vandream.mall.business.vo.FavorityItemVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/7
 * @time 21:11
 * Description:
 */
@Data
@Getter
@Setter
public class CartResponseDTO implements Serializable {
    private static final long serialVersionUID = -170987395632340470L;
    /**
     * 商品总数
     */
    private Long count ;
    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 购物车商品集合
     *
     */
    private List<CartItemLineListDTO> cartItemLineListDTOList;
 }
