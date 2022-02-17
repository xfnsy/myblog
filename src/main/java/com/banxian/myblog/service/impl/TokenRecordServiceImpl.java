package com.banxian.myblog.service.impl;

import com.banxian.myblog.domain.TokenRecord;
import com.banxian.myblog.mapper.TokenRecordMapper;
import com.banxian.myblog.service.ITokenRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * token记录表 服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-26
 */
@Service
public class TokenRecordServiceImpl extends ServiceImpl<TokenRecordMapper, TokenRecord> implements ITokenRecordService {

}
