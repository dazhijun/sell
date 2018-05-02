package com.yan.sell.repository;

import com.yan.sell.dataobject.SellerInfo;
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
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository Repository;
    @Test
    public void save(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setOpenid("1");
        sellerInfo.setPassword("123");
        sellerInfo.setSellerId("admin");
        sellerInfo.setUsername("yan");
        Repository.save(sellerInfo);
    }
    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo=Repository.findByOpenid("1");
        System.out.println(sellerInfo);
    }
}