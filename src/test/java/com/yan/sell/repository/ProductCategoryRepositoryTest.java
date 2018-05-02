package com.yan.sell.repository;

import com.yan.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOne() {
        log.error("Error:123");
        System.out.println(repository.findOne(1).toString());
    }

    @Test
    @Transactional//这样的话，能测试，但是不会真正的写入数据库
    public void saveOne() {
        ProductCategory pro = repository.findOne(2);
        pro.setCategoryName("x");
        repository.save(pro);
    }

    @Test
    public void findSome() {
        List<Integer> categoryTypes= Arrays.asList(1,2,3,4);
        List<ProductCategory> productCategories=repository.findByCategoryTypeIn(categoryTypes);
        System.out.println(productCategories.size());
    }
}