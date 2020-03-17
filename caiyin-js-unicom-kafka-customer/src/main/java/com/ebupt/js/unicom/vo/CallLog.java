package com.ebupt.js.unicom.vo;

import lombok.Data;

@Data
public class CallLog {
    
    //订阅者电话
    private String src_msisdn;
    
    //对端电话
    private String dest_msisdn;
    
    //状态码 0：主叫 1：被叫
    private  String callStatus;
    
    //状态振铃码 2：振铃 4：挂机
    private  String callType;
    
    //呼叫结果
    private String result;
    
    //呼叫详情
    private String remarks;
    
    //呼叫时间 YYYYmmddhhmmssS
    private String callTime;
}
