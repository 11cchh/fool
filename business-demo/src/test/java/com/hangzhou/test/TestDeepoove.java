package com.hangzhou.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.hangzhou.utils.Word2PdfAsposeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试渲染富文本
 *
 * @Author Faye
 * @Date 2025/1/21 19:01
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDeepoove {
    @Value("${aspose.template-file}")
    private String templateFile;
    @Value("${aspose.out-path}")
    private String outPath;

    @Test
    public void testDeepoove() {
        InputStream is = null;
        try {
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Map<String, Object> dataMap = new HashMap<>();
            Map<String, Object> box1 = new HashMap<>();
            box1.put("age", 1);
            Map<String, Object> box2 = new HashMap<>();
            box2.put("age", 2);
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(box1);
            list.add(box2);

            // 单个
            dataMap.put("name", "ccc");
            // 数组
            dataMap.put("jsonArray", list);

            Configure configure = Configure.builder().useSpringEL()
                    .bind(policy, new String[]{"jsonArray"}).build();

            ClassLoader classLoader = TestAspose.class.getClassLoader();
            String inFile = classLoader.getResource(templateFile).getPath();
            is = new FileInputStream(inFile);

            XWPFTemplate.compile(is, configure).render(dataMap)
                    .writeToFile(outPath + "/testDeepoove.docx");
        } catch (Exception e) {
            log.error("渲染失败", e);
        } finally {
            if (null != is) {
                try {
                    // 关闭流
                    is.close();
                } catch (IOException e) {
                    log.error("关闭流失败", e);
                }
            }
        }
    }
}
