package com.wenc.template.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    /***
     * 代码生成器，注意自动覆盖已打开，已经写了代码的类不要重复生成,会把原来代码删除
     * 新建一个表后，在46行写入表名即可，可以写多个用"表名1","表名2","表名3"
     * @param args
     */
    public static void main(String[] args) {

        // 1. 获取代码生成器对象
        AutoGenerator autoGenerator = new AutoGenerator();

        // 设置数据库相关配置
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/template1?characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        autoGenerator.setDataSource(dataSource);

        // 设置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");// 设置代码生成的位置
        globalConfig.setOpen(false);// 生成代码完毕后是否自动打开代码所在目录
        globalConfig.setAuthor("wenc");// 设置作者
        globalConfig.setFileOverride(true);// 设置是否覆盖原始生成的文件
//        globalConfig.setMapperName("%Dao");// 设置数据层接口名
        autoGenerator.setGlobalConfig(globalConfig);

        // 设置包名相关配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.wenc.template");// 设置父包
        autoGenerator.setPackageInfo(packageConfig);

        // 策略设置
        StrategyConfig strategyConfig = new StrategyConfig();
        //在这写要生成的相应表，可以一次写多个
        strategyConfig.setInclude("");
//        strategyConfig.setInclude("SY","LO_NEXT");// 设置本次需要自动生成哪些表的对应的代码
        strategyConfig.setEntityLombokModel(true);// 设置是否启用lombok
        strategyConfig.setEntityTableFieldAnnotationEnable(true);// 字段注解
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);// 类名、变量驼峰式命名
//        strategyConfig.setRestControllerStyle(true);// 是否启用Rest风格
//        strategyConfig.setVersionFieldName("verson");// 设置乐观锁字段名
//        strategyConfig.setLogicDeleteFieldName("delete");// 设置逻辑删除的字段名
        autoGenerator.setStrategy(strategyConfig);

        // 2. 执行生成操作
        autoGenerator.execute();

    }

}
