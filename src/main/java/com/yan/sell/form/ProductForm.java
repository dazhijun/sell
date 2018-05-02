package com.yan.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;
    @NotEmpty(message = "产品名称不能为空")
    private String productName;

    @NotNull
    private BigDecimal productPrice;

    @NotNull
    private Integer productStock;
    @NotEmpty(message = "产品描述不能为空")
    private String productDescription;
    @NotEmpty(message = "产品链接不能为空")
    private String productIcon;

    private Integer categoryType;
}
