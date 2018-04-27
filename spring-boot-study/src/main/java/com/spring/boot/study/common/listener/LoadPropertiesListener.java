package com.spring.boot.study.common.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class LoadPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private final YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
    private final PropertiesPropertySourceLoader propertyLoader = new PropertiesPropertySourceLoader();


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        ConfigurableEnvironment environment = applicationEnvironmentPreparedEvent.getEnvironment();
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        /**
         * jar包下不能读取classpath下的文件夹为File
         */
//        Resource path = new ClassPathResource("/");
//        List<String> yamlFiles = new ArrayList<>();
//        List<String> propertyFiles = new ArrayList<>();
//        try {
//            File file = path.getFile();
//            file.listFiles((configFile -> {
//                if(configFile.isFile()) {
//                    String fileName = configFile.getName();
//                    if(fileName.endsWith("yml")) {
//                        yamlFiles.add(fileName);
//                    } else if(fileName.endsWith("properties")) {
//                        propertyFiles.add(fileName);
//                    }
//                }
//                return true;
//            }));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        yamlFiles.remove("application.yml");
//        propertyFiles.remove("application.properties");
//        if(yamlFiles.size() > 0) {
//            for(String configName : yamlFiles) {
//                Resource yamlResource = new ClassPathResource(configName);
//                PropertySource<?> propertySource = loadYaml(yamlResource, configName);
//                mutablePropertySources.addLast(propertySource);
//            }
//        }
//        if(propertyFiles.size() > 0) {
//            for(String configName : propertyFiles) {
//                Resource propertyResource = new ClassPathResource(configName);
//                PropertySource<?> propertySource = loadProperty(propertyResource, configName);
//                mutablePropertySources.addLast(propertySource);
//            }
//        }
        Resource jdbcConfig = new ClassPathResource("jdbc.yml");
        mutablePropertySources.addLast(loadYaml(jdbcConfig, "jdbc.yml"));
        Resource redisConfig = new ClassPathResource("redis.yml");
        mutablePropertySources.addLast(loadYaml(redisConfig, "redis.yml"));
        Resource differenceConfig = new ClassPathResource("difference-commons.yml");
        mutablePropertySources.addLast(loadYaml(differenceConfig, "difference-commons.yml"));
        Resource noDifferenceConfig = new ClassPathResource("no-difference-commons.yml");
        mutablePropertySources.addLast(loadYaml(noDifferenceConfig, "no-difference-commons.yml"));
    }

    private PropertySource<?> loadYaml(Resource path, String sourceName) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.yamlLoader.load("custom-yaml-resource-" + sourceName, path).get(0);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load yaml configuration from " + path, ex);
        }
    }

    private PropertySource<?> loadProperty(Resource path, String sourceName) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.propertyLoader.load("custom-property-resource-" + sourceName, path).get(0);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load property configuration from " + path, ex);
        }
    }
}
