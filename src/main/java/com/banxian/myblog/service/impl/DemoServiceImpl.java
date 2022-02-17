package com.banxian.myblog.service.impl;

import com.banxian.myblog.domain.Demo;
import com.banxian.myblog.mapper.DemoMapper;
import com.banxian.myblog.service.IDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2020-12-04
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

}
