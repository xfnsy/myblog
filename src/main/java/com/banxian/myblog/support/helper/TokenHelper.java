package com.banxian.myblog.support.helper;

import com.banxian.myblog.common.util.SpringUtil;
import com.banxian.myblog.domain.TokenRecord;
import com.banxian.myblog.service.impl.TokenRecordServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class TokenHelper {

    private static final TokenRecordServiceImpl tokenRecordService;

    static {
        tokenRecordService = SpringUtil.getBean(TokenRecordServiceImpl.class);
    }

    private static final ThreadLocal<TokenRecord> threadLocal = new ThreadLocal<>();

    public static void set(TokenRecord val) {
        threadLocal.set(val);
    }

    public static TokenRecord get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }

    public static boolean checkTokenValid(String token) {
        QueryWrapper<TokenRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        queryWrapper.eq("valid", "1");
        TokenRecord tokenRecord = tokenRecordService.getOne(queryWrapper);
        if (tokenRecord == null) {
            return false;
        }
        set(tokenRecord);
        return true;
    }

    public static void invalidToken() {
        TokenRecord tokenRecord = get();
        tokenRecord.setValid("0");
        tokenRecordService.updateById(tokenRecord);
    }
}
