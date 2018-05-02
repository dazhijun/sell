package com.yan.sell.dataobject.mapper;

import com.yan.sell.dataobject.ProductCategory;
import com.yan.sell.dataobject.dao.ProductCategoryDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("category_name","师兄最不爱");
        map.put("category_type",556);
        int re=mapper.insertByMap(map);
        System.out.println(re);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("shishishi");
        productCategory.setCategoryType(556);
        mapper.insertByObject(productCategory);

    }

    @Test
    public void findByType() {
        ProductCategory productCategory= mapper.findBycategoryType(5);
        System.out.println(productCategory.getCategoryName());

    }

    @Test
    public void findByName() {
        List<ProductCategory> productCategory= mapper.findBycategoryName("师兄最不爱");
        System.out.println();
    }

    @Test
    public void updateByType() {
        mapper.updateByCategoryType("shishi",555);
        System.out.println();
    }

    @Test
    public void updateByObj() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("shishishi");
        productCategory.setCategoryType(556);
        int re=mapper.updateByObject(productCategory);
        System.out.println();
    }

    @Test
    public void delete() {
        int re=mapper.deleteByCategoryType(555);
        System.out.println();
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory re=mapper.selectByCategoryType(444);
        System.out.println();
    }
}