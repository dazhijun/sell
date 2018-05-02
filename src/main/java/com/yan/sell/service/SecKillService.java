package com.yan.sell.service;

import java.security.PrivateKey;

public interface SecKillService {
    String querySecKillProductInfo(String productId);

    void orderProductMockDiffUser(String productId);



}
