package com.banxian.myblog.service.impl;

import com.banxian.myblog.domain.Admin;
import com.banxian.myblog.mapper.AdminMapper;
import com.banxian.myblog.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2020-12-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
