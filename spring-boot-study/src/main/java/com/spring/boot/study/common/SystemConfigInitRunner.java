package com.spring.boot.study.common;

import com.spring.boot.study.dao.master.sys.SysConfigurationsDao;
import com.spring.boot.study.model.master.SysConfigurations;
import com.utils.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemConfigInitRunner implements CommandLineRunner {

    @Autowired
    private JedisService jedisService;
    @Autowired
    private SysConfigurationsDao sysConfigurationsDao;
    @Value("${spring.multiple}")
    private String multipleYamlTest;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("----------------- config multiple test: " + multipleYamlTest);
        sysConfigInit();
    }


    @Async("taskExecutor")
    public void sysConfigInit() {
        List<SysConfigurations> sysConfigurationList = sysConfigurationsDao.getAllUsedConfigurations();
        if (sysConfigurationList != null && sysConfigurationList.size() > 0) {
            for (SysConfigurations sysConfiguration : sysConfigurationList) {
                jedisService.hset(Constants.SYS_CONFIGURATIONS, sysConfiguration.getKey(),
                        sysConfiguration.getValue(), 0);
            }
        }
    }
}
