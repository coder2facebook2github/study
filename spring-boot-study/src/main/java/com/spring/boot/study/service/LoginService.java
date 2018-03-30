package com.spring.boot.study.service;


import com.spring.boot.study.common.Constants;
import com.spring.boot.study.dao.sys.SysUserMapper;
import com.spring.boot.study.model.SysUser;
import com.spring.boot.study.model.vo.LoginVo;
import com.utils.JedisService;
import io.jsonwebtoken.*;
import io.netty.handler.codec.base64.Base64Decoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private JedisService jedisService;
    @Value("${token.timeout}")
    private Integer timeout;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public Map<String, Object> validateUser(LoginVo loginVo) {
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = sysUserMapper.getByMobile(loginVo.getUsername());
        if (sysUser == null) {
            result.put(Constants.MESSAGE, "用户不存在");
        } else {
            String submitPassword = DigestUtils.md5Hex(loginVo.getPassword() + sysUser.getSalt());
            if (!submitPassword.equals(sysUser.getPassword())) {
                result.put(Constants.MESSAGE, "密码错误");
            } else {
                result.put(Constants.MESSAGE, Constants.SUCCESS);
                result.put("sysUser", sysUser);
            }
        }
        return result;
    }

    public void setTokenExpiryTime(long userId) {
        long currentMillis = System.currentTimeMillis();
        jedisService.set(Constants.USER_TOKEN + userId, currentMillis, timeout);
    }

    public String createJwt(SysUser sysUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("mobile", sysUser.getMobile());
        claims.put("userId", sysUser.getId());
        SecretKey secretKey = this.generateKey();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, secretKey);
        return jwtBuilder.compact();
    }

    public Long parseJwt(String jwtStr) {
        SecretKey secretKey = this.generateKey();
        Object body = Jwts.parser().setSigningKey(secretKey).parse(jwtStr).getBody();
        long userId = (long)((Map)body).get("userId");
        return userId;
    }

    private SecretKey generateKey() {
        String jwtSecret = jedisService.hget(Constants.SYS_CONFIGURATIONS, "jwt_secret");
        SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(), signatureAlgorithm.getJcaName());
        return secretKey;
    }

    public static void main(String[] args) {
        Base64.Decoder decoder = Base64.getDecoder();
        System.out.println(new String(decoder.decode("eyJtb2JpbGUiOiIxODUxMTcxNzUwNCIsInVzZXJJZCI6MywiaWF0IjoxNTIyNDA2ODcxfQ")));
        System.out.println(new String(decoder.decode("eyJhbGciOiJIUzI1NiJ9")));
        System.out.println(new String(decoder.decode("5zGsNEycEMUjQhtccshmB6OPRy-8A0r7BuiTC3UXuXk")));
    }



}
