package com.hangzhou.util;

import cn.hutool.core.util.ReUtil;
import com.hangzhou.constant.ResultEnum;
import com.hangzhou.exception.BizException;

/**
 * 非法字符校验工具类
 * @Author: Faye
 * @Data: 2022/9/14 11:16
 */
public class ImproperCharacterCheckUtils {
    private static final String REGEX = "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$";

    /**
     * 非法字符：\ / * ? : ｜
     * @param str 字符串
     */
    public static void check(String str) {
        if (!ReUtil.isMatch(REGEX, str)) {
            throw new BizException(ResultEnum.ERROR.getCode(), String.format("%s中存在非法字符", str));
        }
    }
}
