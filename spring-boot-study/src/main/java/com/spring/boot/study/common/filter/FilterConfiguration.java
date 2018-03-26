package com.spring.boot.study.common.filter;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "exclude")
public class FilterConfiguration {


    Set<String> uri = new HashSet<>();

    public Set<String> getUri() {
        return uri;
    }

    public void setUri(Set<String> uri) {
        this.uri = uri;
    }

}
