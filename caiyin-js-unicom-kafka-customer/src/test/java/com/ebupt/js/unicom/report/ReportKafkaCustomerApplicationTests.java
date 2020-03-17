package com.ebupt.js.unicom.report;

import com.alibaba.fastjson.JSON;
import com.ebupt.js.unicom.exception.BizException;
import com.ebupt.js.unicom.exception.Remarks;
import com.ebupt.js.unicom.vo.CallLog;
import com.ebupt.js.unicom.vo.HttpRequest;
import com.ebupt.js.unicom.vo.KafkaVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class ReportKafkaCustomerApplicationTests {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Value("${appkey}")
    String a ;
    
    @Test
    void contextLoads() {
        
        System.out.println(a);
    }
    
    @Test
    void restTemplate(){
        
        CallLog callLog = new CallLog();
        callLog.setSrc_msisdn("13898989898");
        callLog.setDest_msisdn("13989898989");
        callLog.setCallStatus("0");
        callLog.setCallType("1");
        callLog.setCallTime(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
        callLog.setResult("1");
    
        callLog.setRemarks("4");
        HttpRequest httpRequest = new HttpRequest(null,callLog);
        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(httpRequest));
        ResponseEntity<String> res = restTemplate.postForEntity("http://10.1.63.59:19741/sendresult/zjyd_result", requestEntity, String.class);
    }
    
    
    
    public static void main(String[] args) {
        
        String a= DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong("1575165580892")), ZoneId.systemDefault()));
        System.out.println(a);
        System.out.println(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
        
        //固话
        String regexp=  "^(\\+\\d{2}-)?0\\d{2,3}-\\d{7,8}$";
        //国内手机号
        String CN = "^(\\+?0{0,2}86\\-?)?1[3456789]\\d{9}$";
        
        String phone = "+008613896930864";
        
        try{
            if (phone.matches(regexp)){
                System.out.println("固话");
            }else if(phone.matches(CN)){
                throw new BizException(Remarks.OTHER);
//            System.out.println("国内手机号");
            }else{
                System.out.println("境外来电");
            }
        }catch (BizException e){
            System.out.println("-----------");
            System.out.println(e.getErrorCode().getCode());
        }
        catch (Exception e){
        
        
        }
        
        
    
        System.out.println(Remarks.INTERNATIONCALL.getCode());
    }
    
    @Test
    public void rtPostObject(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://47.xxx.xxx.96/register/checkEmail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", "844072586@qq.com");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());
    }
    
    
    @Test
    public  void paramTest(){
        KafkaVo kafkaVo = new KafkaVo();
        
        kafkaVo.setServiceType("5");
        
        kafkaVo.setPhoneNumber("12313");
        
        kafkaVo.setMomt("0");
        
        kafkaVo.setPhoneNumber_R("008613896930864");
        
        kafkaVo.setOccurTime("221321");
        
        kafkaVo.validate();
    }
    
}
