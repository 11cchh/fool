package com.hangzhou.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 弱密码检测工具类
 *
 * @Author: Faye
 * @Data: 2022/9/14 11:16
 */
public class WeakPasswordUtils {
    public static boolean isWeakPassword(String password) {

        // 字符长度8 - 16字符
        // 1个及以上大写字母
        // 1个及以上小写字母
        // 1个及以上特殊字符 !@#$&*
        // 3个及以上数字字符
        // 需要满足的基本规则
        String baseReg = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9].*[0-9])(?=.*[a-z]).{8,16}$";

        boolean matchBaseReg = password.matches(baseReg);

        // 不满足要求，认为是弱密码
        if (!matchBaseReg) {
            return true;
        }

        // 需要满足的特征
        String pattern1 = "([0-9a-zA-Z])\\1{2}";

        String pattern2 = "(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}";

        String pattern3 = "(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2}";

        boolean weakPassword = isMatchWeakPassword(password, pattern1);
        if (weakPassword) {
            return true;
        }

        weakPassword = isMatchWeakPassword(password, pattern2);
        if (weakPassword) {
            return true;
        }

        weakPassword = isMatchWeakPassword(password, pattern3);
        return weakPassword;

    }

    public static boolean isMatchWeakPassword(String password, String patternStr) {

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(patternStr);

        // 现在创建 matcher 对象
        Matcher matcher = pattern.matcher(password);

        // 找到弱密码特征
        return matcher.find();

    }

    public static void main(String[] args) {

        System.out.println(isWeakPassword("$13AA11aa"));

        System.out.println(isWeakPassword("admin123456"));

        System.out.println(isWeakPassword("12345678"));

    }
}
