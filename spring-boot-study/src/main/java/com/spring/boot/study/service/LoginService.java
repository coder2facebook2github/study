package com.spring.boot.study.service;


import com.spring.boot.study.common.Constants;
import com.spring.boot.study.dao.master.sys.SysUserDao;
import com.spring.boot.study.model.master.SysUser;
import com.spring.boot.study.model.master.vo.LoginVo;
import com.utils.JedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private JedisService jedisService;
    @Value("${token.timeout}")
    private Integer timeout;
    @Value("${token.image.count}")
    private Integer tryCount;
    @Value("${token.image.timeout}")
    private Integer imageTimeout;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public Map<String, Object> validateImageCode(LoginVo loginVo) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> randomCodeMap = jedisService.get(Constants.IMAGE_TOKEN + loginVo.getToken());
            int count = (int)randomCodeMap.get("count");
            if(count >= tryCount) {
                result.put(Constants.MESSAGE, "验证码过期，请重新获取验证码");
                return result;
            }
            String code = (String)randomCodeMap.get("code");
            if(code.equals(loginVo.getCode())) {
                result.put(Constants.MESSAGE, Constants.SUCCESS);
            } else {
                result.put(Constants.MESSAGE, "验证码有误");
                randomCodeMap.put("count", count + 1);
                jedisService.set(Constants.IMAGE_TOKEN + loginVo.getToken(), randomCodeMap, imageTimeout);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            result.put(Constants.MESSAGE, "验证码过期，请重新获取验证码");
            return result;
        }
        return result;
    }

    public Map<String, Object> validateUser(LoginVo loginVo) {
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = sysUserDao.getByMobile(loginVo.getUsername());
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

    public SysUser getSysUserByMobile(String mobile) {
        return sysUserDao.getByMobile(mobile);
    }

    public boolean checkPassword(String mobile, String password) {
        SysUser user = sysUserDao.getByMobile(mobile);
        String submitPassword = DigestUtils.md5Hex(password + user.getSalt());
        return user.getPassword().equals(submitPassword);
    }

    public void setTokenExpiryTime(long userId, String token) {
        jedisService.set(Constants.USER_TOKEN + userId, token, timeout);
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
        DefaultClaims body = (DefaultClaims) Jwts.parser().setSigningKey(secretKey).parse(jwtStr).getBody();
        long userId = body.get("userId", Long.class);
        return userId;
    }

    private SecretKey generateKey() {
        String jwtSecret = jedisService.hget(Constants.SYS_CONFIGURATIONS, "jwt_secret");
        SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(), signatureAlgorithm.getJcaName());
        return secretKey;
    }




}
