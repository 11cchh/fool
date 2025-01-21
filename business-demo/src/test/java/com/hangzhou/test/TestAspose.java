package com.hangzhou.test;

import com.hangzhou.utils.Word2PdfAsposeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 测试word2pdf功能
 *
 * @Author Faye
 * @Date 2025/1/21 17:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAspose {
    @Value("${aspose.template-file}")
    private String templateFile;
    @Value("${aspose.out-path}")
    private String outPath;


    @Test
    public void testAspose() {
        InputStream is = null;
        try {
            ClassLoader classLoader = TestAspose.class.getClassLoader();
            String inFile = classLoader.getResource(templateFile).getPath();
            String outFile = outPath + "/aspose.pdf";

            Word2PdfAsposeUtil.doc2pdf(inFile, outFile);
        } catch (Exception e) {
            log.error("导出失败", e);
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
