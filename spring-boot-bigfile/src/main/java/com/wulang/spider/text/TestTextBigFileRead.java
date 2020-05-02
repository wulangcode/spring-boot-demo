package com.wulang.spider.text;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wulang
 * @create 2020/3/2/20:11
 */
public class TestTextBigFileRead {

    public static void main(String[] args) {
        AtomicLong counter = new AtomicLong(0);
        String smallFilePath = "/Users/renhongqiang/Downloads/work-doc/2000W/demo.txt";
        String bigFilePath = "/Users/renhongqiang/Downloads/work-doc/2000W/test.csv";
        BigFileReader.Builder builder = new BigFileReader.Builder(bigFilePath, line -> System.out.println(String.format("total record: %s,line is: %s", counter.incrementAndGet(), line)));
        BigFileReader bigFileReader = builder
            .threadPoolSize(2)
            .charset(StandardCharsets.UTF_8)
            .bufferSize(1024).build();
        bigFileReader.start();
    }

    public static long getLineNumber(File file) {
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
                lineNumberReader.skip(Long.MAX_VALUE);
                long lines = lineNumberReader.getLineNumber() + 1;
                fileReader.close();
                lineNumberReader.close();
                return lines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
