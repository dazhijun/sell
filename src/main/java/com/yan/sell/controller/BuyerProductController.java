package com.yan.sell.controller;

import com.yan.sell.VO.ProductInfoVO;
import com.yan.sell.VO.ProductVO;
import com.yan.sell.VO.ResultVO;
import com.yan.sell.dataobject.ProductCategory;
import com.yan.sell.dataobject.ProductInfo;
import com.yan.sell.service.CategoryService;
import com.yan.sell.service.ProductService;
import com.yan.sell.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVO list(){
        //查询所有的上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //查询类目（一次性查询)
        //传统方法
/*        for (ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        //精简方法
        List<Integer> categoryTypeList=productInfoList.stream()
                .map(e->e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategorylist=categoryService.findByCategoryTypeIn(categoryTypeList);
        //数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategorylist){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    System.out.println(productInfoVO.getProductDescription());
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

    @GetMapping("/out")
    public String list1(){
        ResultVO resultVO=new ResultVO();
        return "hello";
    }
}
