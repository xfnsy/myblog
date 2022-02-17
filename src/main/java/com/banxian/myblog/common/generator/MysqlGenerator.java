package com.banxian.myblog.common.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * Created by Enzo Cotter on 2020/12/3.
 */
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class MysqlGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) throws Exception {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.hasText(ipt)) {
                return ipt;
            }
        }
        throw new Exception("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws Exception {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);//是否覆盖已有文件默认false
        gc.setAuthor("wangpeng");
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setIdType(IdType.ASSIGN_ID);//指定生成的主键的ID类型
        gc.setSwagger2(false); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.13.128:3306/myblog?characterEncoding=UTF-8&useSSL=false");
         dsc.setSchemaName("myblog");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        // 自定义数据库表字段类型转换【可选】
//        dsc.setTypeConvert(new MySqlTypeConvert() {
//        });
        mpg.setDataSource(dsc);


        // 3.包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));// 会创建模块名称的包，各种生成文件都在下面
        pc.setParent("com.banxian.myblog");
        // 自定义包名
        pc.setEntity("domain");
        pc.setController("web.controller");
        mpg.setPackageInfo(pc);

        // 全局uid属性配置
        Map<String, Object> globalUid = new HashMap<>();
        // 4.自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            //自定义属性注入:abc,然后在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("globalUid", globalUid);
                this.setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        String xmlTemplatePath = "templates/mybatis/mapper.xml.ftl";
        // 如果模板引擎是 velocity
//        String xmlTemplatePath = "templates/mapper.xml.vm";

        // 自定义输出位置配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(xmlTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 在每个表上配置uid
                globalUid.put(tableInfo.getName(), new Random(System.currentTimeMillis()).nextLong());
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
//                        + pc.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });


        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
//                checkDir("调用默认方法创建的目录，自定义目录用");
//                if (fileType == FileType.MAPPER) {
//                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
//                    return !new File(filePath).exists();
//                }
//                if (fileType == FileType.XML) {
//                    return !new File(filePath).exists();
//                }
//                if (fileType == FileType.ENTITY) {
//                    return !new File(filePath).exists();
//                }
                if (fileType == FileType.SERVICE) {
                    return !new File(filePath).exists();
                }
                if (fileType == FileType.SERVICE_IMPL) {
                    return !new File(filePath).exists();
                }
                if (fileType == FileType.CONTROLLER) {
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        cfg.setFileOutConfigList(focList);

        mpg.setCfg(cfg);

        // 5.配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/mybatis/entity.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);//取消默认mapper的生成地址
        mpg.setTemplate(templateConfig);

        // 6.策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setLogicDeleteFieldName("deleted");
        List<TableFill> list=new ArrayList<>();
        TableFill tf1=new TableFill("create_at", FieldFill.INSERT_UPDATE);
        TableFill tf2=new TableFill("update_at", FieldFill.UPDATE);
        list.add(tf1);
        list.add(tf2);
        strategy.setTableFillList(list);
        //【实体】是否为lombok模型（默认 false）
//        strategy.setEntityLombokModel(true);
        // 是否是@RestController
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名称,多张表,隔开").split(","));
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}