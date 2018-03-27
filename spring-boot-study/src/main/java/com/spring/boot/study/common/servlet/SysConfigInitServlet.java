package com.spring.boot.study.common.servlet;


import com.spring.boot.study.common.Constants;
import com.spring.boot.study.dao.sys.SysConfigurationsMapper;
import com.spring.boot.study.model.SysConfigurations;
import com.utils.JedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "sysConfigInitServlet", urlPatterns = "/sys/config/init", loadOnStartup = 2)
public class SysConfigInitServlet extends HttpServlet{

    @Autowired
    private JedisService jedisService;
    @Autowired
    private SysConfigurationsMapper sysConfigurationsMapper;



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("-----------sysConfigurationInit");
        sysConfigInit();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String init = req.getParameter("init");
        if("true".equals(init)) {
            sysConfigInit();
        }
    }

    private void sysConfigInit() {
        List<SysConfigurations> sysConfigurationList = sysConfigurationsMapper.getAllUsedConfigurations();
        if(sysConfigurationList != null && sysConfigurationList.size() > 0) {
            for(SysConfigurations sysConfiguration : sysConfigurationList) {
            jedisService.hset(Constants.SYS_CONFIGURATIONS, sysConfiguration.getKey(),
                    sysConfiguration.getValue(), 0);
            }
        }
    }

}
