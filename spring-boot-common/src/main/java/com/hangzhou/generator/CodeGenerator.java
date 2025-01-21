package com.hangzhou.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * @Author: Faye
 * @Data: 2022/9/7 09:27
 */
public class CodeGenerator {
    // 数据库信息
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hangzhou?characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "19970210";
    // 作者
    private static final String AUTHER = "Faye";
    // 项目目录
    private static final String projectPath = System.getProperty("user.dir");
    // 包配置
    private static final String PACKAGE = "com.hangzhou";

    //输出各模块目录路径Map
    private static Map<OutputFile, String> OUT_PUT_FILE = new HashMap<>();

    private static List<String> tableList = new ArrayList<>();

    static {
        init();
        //需要生成的表列表
        tableList.add("table_column_type");
    }

    private static void init() {
        // 构建输出路径
        String PACKAGE_DIR = PACKAGE.replace(".", "/");
        String XML_OUT_PUT_DIR = projectPath + "/datasource/src/main/resources/mapper/";
        String CONTROLLER_OUT_PUT_DIR = projectPath + "/datasource/src/main/java/" + PACKAGE_DIR + "/controller/";
        String SERVICE_OUT_PUT_DIR = projectPath + "/datasource/src/main/java/" + PACKAGE_DIR + "/service/";
        String SERVICE_IMPL_OUT_PUT_DIR = projectPath + "/datasource/src/main/java/" + PACKAGE_DIR + "/service/impl/";
        String MAPPER_OUT_PUT_DIR = projectPath + "/datasource/src/main/java/" + PACKAGE_DIR + "/mapper/";
        String ENTITY_OUT_PUT_DIR = projectPath + "/datasource/src/main/java/" + PACKAGE_DIR + "/entity/";
        OUT_PUT_FILE.put(OutputFile.controller, CONTROLLER_OUT_PUT_DIR);
        OUT_PUT_FILE.put(OutputFile.service, SERVICE_OUT_PUT_DIR);
        OUT_PUT_FILE.put(OutputFile.serviceImpl, SERVICE_IMPL_OUT_PUT_DIR);
        OUT_PUT_FILE.put(OutputFile.mapper, MAPPER_OUT_PUT_DIR);
        OUT_PUT_FILE.put(OutputFile.entity, ENTITY_OUT_PUT_DIR);
        OUT_PUT_FILE.put(OutputFile.mapperXml, XML_OUT_PUT_DIR);
    }

    public static void main(String[] args) {
        DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig.
                Builder(URL, USERNAME, PASSWORD)
                .typeConvert(new MySqlTypeConvert());
        FastAutoGenerator.create(dataSourceBuilder)
                //全局配置
                .globalConfig(builder -> {
                    builder.author(AUTHER) // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath); // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent(PACKAGE) // 设置父包名
                            .pathInfo(OUT_PUT_FILE);//输出路径
                })
                //策略配置
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder.addInclude(tableList)
                            // 固定前缀
//                            .addTablePrefix("ed_")
                            .entityBuilder()
                            .enableTableFieldAnnotation()
                            .enableChainModel()
                            .enableLombok()
                            .controllerBuilder()
                            .enableRestStyle()
                            .serviceBuilder()
                            .convertServiceFileName(new ConverterFileName() {
                                @Override
                                public
                                String convert(String entityName) {
                                    return entityName + "Service";
                                }
                            })
                            .mapperBuilder()
                            .enableBaseResultMap();
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
