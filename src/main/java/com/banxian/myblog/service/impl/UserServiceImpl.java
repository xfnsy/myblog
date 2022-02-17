package com.banxian.myblog.service.impl;

import com.banxian.myblog.domain.User;
import com.banxian.myblog.mapper.UserMapper;
import com.banxian.myblog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2020-12-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
