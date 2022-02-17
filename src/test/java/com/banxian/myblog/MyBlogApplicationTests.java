package com.banxian.myblog;

import com.banxian.myblog.common.base.Constants;
import com.banxian.myblog.common.util.HttpClientUtil;
import com.banxian.myblog.common.util.SHAUtil;
import com.banxian.myblog.common.util.SpringUtil;
import com.banxian.myblog.domain.Admin;
import com.banxian.myblog.domain.Demo;
import com.banxian.myblog.mapper.CommonMapper;
import com.banxian.myblog.mapper.DemoMapper;
import com.banxian.myblog.service.IAdminService;
import com.banxian.myblog.service.IDemoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MyBlogApplicationTests {

    @Resource
    private IDemoService demoService;

    @Resource
    private IAdminService adminService;

    @Resource
    private CommonMapper commonMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSelect() {
        demoService.list().forEach(d -> System.out.println(d));
    }

    @Test
    public void testSave() {
        Admin admin = new Admin();
        admin.setAdminName("banxian");
        admin.setPassword(SHAUtil.SHA256("tdhbg123456" + Constants.SHA256_SALT));
        adminService.save(admin);
    }

    @Test
    public void testSaveBatch() {
        ArrayList<Demo> list = new ArrayList();
        for (int i = 0; i < 200; i++) {
            Demo demo = new Demo();
            demo.setAge(i);
            demo.setName("wangsan");
            demo.setEmail("haoda");
            list.add(demo);
        }
        demoService.saveBatch(list);
    }

    @Test
    public void testUpdate() {
        Admin admin =new Admin();
        admin.setAdminId(101);
        admin.setPassword(SHAUtil.SHA256("tdhbg123456" + Constants.SHA256_SALT));
        adminService.updateById(admin);
    }

    @Test
    public void testDelete() {
        adminService.remove(null);
    }


    @Test
    public void testList() {
        Page<Demo> page = demoService.page(new Page<>(2, 10));
    }

    @Test
    public void testSelectByParam() {
        Demo demo = new Demo();
        demo.setAge(14);
        demo.setName("wanger1");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(demo);
        demoService.list(queryWrapper);
    }

    @Test
    public void testSpringUtil() {
        DemoMapper bean = SpringUtil.getBean(DemoMapper.class);
        Assert.notNull(bean, "获取bean失败");
        System.out.println("你好啊");
    }

    @Test
    public void testTableMapper() {
        List<String> tables = commonMapper.showTables();
        for (String tableName : tables) {
//            long id = commonMapper.selectMaxId(tableName, "id");
//            System.out.println(id);
        }
    }

    @Test
    public void testHttpClientUtil(){
        String result = HttpClientUtil.doPost("https://api.apiopen.top/updateUserInfo");
        System.out.println(result);
    }

}
