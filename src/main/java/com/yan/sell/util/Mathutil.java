package com.yan.sell.util;

public class Mathutil {
    private static final Double MONEY_RANFE=0.01;
    public static Boolean equals(Double d1,Double d2){
        Double result=Math.abs(d1-d2);
        if (result<MONEY_RANFE){
            return true;
        }else {
            return false;
        }
    }
}
