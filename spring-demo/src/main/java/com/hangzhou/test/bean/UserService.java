package com.hangzhou.test.bean;

import com.hangzhou.spring.annotation.Scope;
import com.hangzhou.spring.annotation.Service;

/**
 * @Author Faye
 * @Date 2023/1/10 15:44
 */
@Scope("singleton")
@Service("userService")
public class UserService {
}
