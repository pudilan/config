package com.sintmo.scsa.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {

	@Autowired
	private ServiceA serviceA;

	@Value("${common.key:}")
	private String commonKey;

	@PostConstruct
	public void postConstruct() {
		System.out.println("#################" + commonKey);
	}

	public void test1() {
		serviceA.test2();
		System.out.println("------------------------test1");
	}

	public void test2() {
		System.out.println("------------------------test2");
	}

}
