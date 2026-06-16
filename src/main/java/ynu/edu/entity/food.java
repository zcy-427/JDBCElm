package ynu.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class food {
    //菜品id
    private Integer foodId;

    //商家id,外键关联business表
    private Integer businessId;

    //菜品名称
    private String foodName;

    //菜品描述
    private String foodExplain;

    //菜品价格
    private BigDecimal foodPrice;
}
