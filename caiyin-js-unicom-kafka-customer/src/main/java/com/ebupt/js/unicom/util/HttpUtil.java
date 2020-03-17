package com.ebupt.js.unicom.util;

import com.alibaba.fastjson.JSON;
import com.ebupt.js.unicom.exception.BizException;
import com.ebupt.js.unicom.exception.Remarks;
import com.ebupt.js.unicom.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class HttpUtil {
    
//    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    public HttpUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    
//    public static HttpUtil httpUtil;
//    @PostConstruct
//    public void init(){
//        httpUtil = this;
//        httpUtil.restTemplate = this.restTemplate;
////        httpUtil.loggingClientHttpRequestInterceptor = this.loggingClientHttpRequestInterceptor;
//    }
    
    @Value("${appkey}")
    private String appKey;
    
    @Value("${notifyURL}")
    private String notifyurl;
    
    @Value("${callmessageurl}")
    private String callmessageurl;
    
    @Value("${calllogurl}")
    private String calllogurl;
    
    //发往呼叫逻辑
    public void CallMessage(KafkaVo kafkaVo)throws BizException,Exception{
    
        CallEventType callEventType = new CallEventType();
        callEventType.setCalling("0".equals(kafkaVo.getMomt())?kafkaVo.getPhoneNumber():kafkaVo.getPhoneNumber_R());
        callEventType.setCalled("0".equals(kafkaVo.getMomt())?kafkaVo.getPhoneNumber_R():kafkaVo.getPhoneNumber());
        callEventType.setCallIdentifer(UUID.randomUUID().toString());
        callEventType.setDirection("0".equals(kafkaVo.getMomt())?"MO":"MT");
        callEventType.setEvent("2".equals(kafkaVo.getServiceType())?"Ringing":"Release");
        callEventType.setTimeStamp(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)).toString());
    
        CallReq callReq = new CallReq();
        callReq.setAppKey(appKey);
        callReq.setNotifyURL(notifyurl);
        
        callReq.setCallEvent(callEventType);
    
        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(callReq), HttpHeader.getInstance());
    
        httpPost(callmessageurl,requestEntity);
    }
    
    //发往日志
    public void CallLog(CallLog callLog)throws BizException,Exception{
        HttpRequest httpRequest = new HttpRequest(null,callLog);
        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(httpRequest));
    
        httpPost(calllogurl,requestEntity);
    }
    
    private void httpPost(String url,HttpEntity<String> requestEntity) throws BizException,Exception{
       
            ResponseEntity<String> res = restTemplate.postForEntity(url, requestEntity, String.class);
            if(res.getStatusCodeValue() != 200 ){
                log.error("http return error:"+res.getStatusCodeValue());
                throw new BizException(Remarks.OTHER);
            }
    }
}
