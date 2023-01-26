package com.hangzhou.test.circular;


import com.hangzhou.spring.annotation.Autowired;
import com.hangzhou.spring.annotation.Component;

/**
 * @Author Faye
 * @Date 2023/1/22 15:35
 */
@Component
public class ClassB {
	@Autowired
	private ClassA classA;

	public ClassB() {
		System.out.println("ClassB Constructor");
	}

	public ClassB(ClassA classA) {
		this.classA = classA;
	}

	public ClassA getClassA() {
		return classA;
	}

	public void setClassA(ClassA classA) {
		this.classA = classA;
	}
}
