package com.sintmo.scsa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    @Autowired
    private ServiceA serviceA;

    public void test1() {
        serviceA.test2();
        System.out.println("------------------------test1");
    }

    public void test2() {
        System.out.println("------------------------test2");
    }

}
