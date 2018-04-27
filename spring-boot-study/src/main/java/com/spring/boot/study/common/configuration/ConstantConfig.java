package com.spring.boot.study.common.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.constant")
public class ConstantConfig {

    private String welcomeWord;
    private User user;
    private List<String> comments = new ArrayList<>();

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getWelcomeWord() {
        return welcomeWord;
    }

    public void setWelcomeWord(String welcomeWord) {
        this.welcomeWord = welcomeWord;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private String name;
        private Integer age;
        private Address address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", " + address +
                    '}';
        }
    }

    public static class Address {
        private String province;
        private String city;
        private String area;
        private String street;


        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", street='" + street + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ConstantConfig{" +
                "welcomeWord='" + welcomeWord + '\'' +
                ", " + user +
                '}';
    }
}
