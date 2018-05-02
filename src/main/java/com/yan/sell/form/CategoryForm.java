package com.yan.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {
    private Integer categoryId;
    @NotEmpty(message = "务必需要类目名字")
    private String categoryName;
    @NotNull(message = "务必需要填写类目type")
    private Integer categoryType;
}
