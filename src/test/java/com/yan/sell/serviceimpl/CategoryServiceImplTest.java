package com.yan.sell.serviceimpl;

import com.yan.sell.dataobject.ProductCategory;
import com.yan.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;
    @Test
    public void findOne() {
        ProductCategory productCategory=categoryService.findOne(1);
        System.out.println(productCategory);
        productCategory.setCategoryName("热销榜热销榜");
        categoryService.save(productCategory);
    }

    @Test
    public void findAll() {
    }
}