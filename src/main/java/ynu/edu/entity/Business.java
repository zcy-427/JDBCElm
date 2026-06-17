package ynu.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    //商家id
    private Integer businessId;

    //商家密码
    private String password;

    //商家用户名
    private String businessName;

    //商家地址
    private String businessAddress;

    //商家介绍
    private String businessExplain;

    //商家起送价
    private BigDecimal starPrice;

    //商家配送费
    private BigDecimal deliveryPrice;
}
