package com.spring.boot.study.controller;

import com.utils.URLParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
public class ResourceGetController {

    @ResponseBody
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    public Map<String, Object> path() {
        Map<String, Object> result = new HashMap<>();
        //这种方式在项目以jar包方式运行，内嵌web容器时不管用
        String path = this.getClass().getResource("/").getPath();
        System.out.println("path: " + path);
        BufferedReader bufferedReader = null;
        try {
//            File file = new File(path + "mapper/AreasDao.xml");
//            bufferedReader = new BufferedReader(new FileReader(file));
            //jar里面的文件读取方式
            InputStream inputStream = new ClassPathResource("mapper/master/AreasMapper.xml").getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(bufferedReader.readLine() + "\n" + bufferedReader.readLine() + "\n" + bufferedReader.readLine());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                }
            }
        }
        result.put("message", "success");
        return result;
    }

    /**
     * https://www.cnblogs.com/keyi/p/6282838.html
     * 读取jar包中的文件
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/properties")
    public Map<String, Object> readJarProperties() {
        Map<String, Object> result = new HashMap<>();
        Properties properties = new Properties();
        try {
            InputStream inputStream = URLParser.class.getResourceAsStream("utils.properties");
//            InputStream inputStream = URLParser.class.getClassLoader().getResource("com/utils/utils.properties").openStream();

//            InputStream inputStream1 = Long.class.getResource("/com/welcome.properties").openStream();
            InputStream inputStream1 = URLParser.class.getResource("../welcome.properties").openStream();
//            InputStream inputStream1 = this.getClass().getClassLoader().getResource("com/welcome.properties").openStream();


            SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream, inputStream1);

            properties.load(sequenceInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.forEach((key, value) -> result.put((String) key, value));
        return result;
    }

}
