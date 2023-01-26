package com.hangzhou.test.circular;


import com.hangzhou.spring.annotation.Autowired;
import com.hangzhou.spring.annotation.Component;

/**
 * @Author Faye
 * @Date 2023/1/22 15:35
 */
@Component
public class ClassA implements IClassA {
    @Autowired
    private ClassB classB;

    public ClassA() {
        System.out.println("ClassA Constructor");
    }

    public ClassA(ClassB classB) {
        this.classB = classB;
    }

    public void setClassB(ClassB classB) {
        this.classB = classB;
    }

    public ClassB getClassB() {
        return classB;
    }

    @Override
    public void execute() {
        System.out.println("ClassA execute...");
    }
}
