package com.spring.boot.study.common.servlet;


import com.alibaba.fastjson.JSONObject;
import com.spring.boot.study.common.Constants;
import com.spring.boot.study.dao.master.sys.SysConfigurationsDao;
import com.spring.boot.study.model.master.SysConfigurations;
import com.utils.JedisService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet(name = "sysConfigInitServlet", urlPatterns = "/sys/config/init", loadOnStartup = 2)
public class SysConfigInitServlet extends HttpServlet {

    @Autowired
    private JedisService jedisService;
    @Autowired
    private SysConfigurationsDao sysConfigurationsDao;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("-----------sysConfigurationInit");
        sysConfigInit();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        if(!req.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            return;
        }
        InputStream inputStream = req.getInputStream();
        String requestBody = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        boolean init = JSONObject.parseObject(requestBody).getBoolean("init");
        if (init) {
            sysConfigInit();
        }
    }

    private void sysConfigInit() {
        List<SysConfigurations> sysConfigurationList = sysConfigurationsDao.getAllUsedConfigurations();
        if (sysConfigurationList != null && sysConfigurationList.size() > 0) {
            for (SysConfigurations sysConfiguration : sysConfigurationList) {
                jedisService.hset(Constants.SYS_CONFIGURATIONS, sysConfiguration.getKey(),
                        sysConfiguration.getValue(), 0);
            }
        }
    }

}
