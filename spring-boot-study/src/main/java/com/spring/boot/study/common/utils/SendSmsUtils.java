package com.spring.boot.study.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.spring.boot.study.common.CloseResource;
import com.spring.boot.study.common.Constants;
import com.utils.JedisService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendSmsUtils {

    @Autowired
    private JedisService jedisService;
    private String messageContent = "您的验证码是: $code，$timeout分钟内有效，请勿泄露";
    // 验证码 有效期
    @Value("${token.message.timeout}")
    private Integer timeout;

    public void sendValidateCode(String mobile) throws ClientException {
//        this.sendMessage(mobile, Constants.SIGN_NAME, Constants.IDENTIFY_TEMPLATE_CODE, getRandomCode(6));
        String code = getRandomCode(6);
//        boolean sendSuccess = this.sendMessage(mobile, messageContent.replace("$code", code)
//                .replace("$timeout", String.valueOf(timeout / 60)));
        if(true) {
            jedisService.setStr(Constants.MESSAGE_CODE + mobile, code, timeout);
            System.out.println("mobile: " + mobile + ", code: " + code);
        }
    }

    public boolean sendMessage(String mobile, String content) {
        boolean result = false;
        String url = jedisService.hget(Constants.SYS_CONFIGURATIONS, "send_message_api_url");
        String privateKey = jedisService.hget(Constants.SYS_CONFIGURATIONS, "send_message_private_key");
        Long businessType = Long.parseLong(jedisService.hget(Constants.SYS_CONFIGURATIONS, "send_message_business_type"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("content", content + " 回TD退");
        params.put("sms_type", "advertisement");
        params.put("source", "web-api");
        String dataJsonStr = JSONObject.toJSONString(params);
        String sign = DigestUtils.md5Hex(dataJsonStr + privateKey);
        JSONObject postJson = new JSONObject();
        postJson.put("data", dataJsonStr);
        postJson.put("business_type", businessType);
        postJson.put("sign", sign);
        String postString = postJson.toString();
        System.out.println("postData: " + postString);
        StringEntity stringEntity = new StringEntity(postString, StandardCharsets.UTF_8);
        stringEntity.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpPost.setEntity(stringEntity);
        HttpEntity responseEntity = null;
        String httpResult = "";
        try {
            response = httpClient.execute(httpPost);
            responseEntity = response.getEntity();
            httpResult = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseResource.closeHttpClient(httpClient, response);
        }
        if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            JSONObject httpJsonResult = JSONObject.parseObject(httpResult);
            if(httpJsonResult.getIntValue("code") == 0) {
                result = true;
            }
        }
        return result;
    }

    private String getRandomCode(int count) {
        String code = RandomStringUtils.randomNumeric(count);
        return code;
    }

    public void sendMessage(String mobile, String signName, String templateCode, String code) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = jedisService.hget(Constants.SYS_CONFIGURATIONS,"aliyun_access_key_id");//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = jedisService.hget(Constants.SYS_CONFIGURATIONS,"aliyun_access_key_secret");//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            jedisService.setStr(Constants.MESSAGE_CODE + mobile, code);
            System.out.println("mobile: " + mobile + ", code: " + code);
        }
    }
}
