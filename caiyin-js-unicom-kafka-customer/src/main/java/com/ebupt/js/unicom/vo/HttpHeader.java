package com.ebupt.js.unicom.vo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Slf4j
@Component
public class HttpHeader {
    
    public static HttpHeaders instance;
    
    
    private static String userid;
    
    private static String password;
    
    @Value("${userid}")
    public void setUserid(String userid) {
        HttpHeader.userid = userid;
    }
    
    @Value("${password}")
    public void setPassword(String password) {
        HttpHeader.password = password;
    }
    
    private HttpHeader() {
    }
    public static HttpHeaders getInstance() {
        if (instance == null) {
            instance = new HttpHeaders();
            String user = userid+":"+password;
    
            //这里设置的是以payLoad方式提交数据，对于Payload方式，提交的内容一定要是String，且Header要设为“application/json”
            String md5Password = DigestUtils.md5DigestAsHex(user.getBytes());
//            log.error(user);
//            log.error("####{}--{}",user,md5Password);
            instance.setContentType(MediaType.APPLICATION_JSON);
            instance.add("Authorization","Basic "+md5Password);
        }
        return instance;
    }
}
