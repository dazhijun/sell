package com.yan.sell.dataobject.mapper;

import java.security.spec.ECField;

public class dog extends animal{
    public dog() {
        super(1,1);

    }

    @Override
    public void a() throws Exception{
        System.out.println("dog");
    }
    public static void main(String args[]) throws Exception {
        dog d=new dog();
        animal a=d;
        a.a();
    }
}
