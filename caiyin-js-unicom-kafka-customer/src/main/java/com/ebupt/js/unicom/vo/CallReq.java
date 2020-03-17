package com.ebupt.js.unicom.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class CallReq {
    
    //呼叫所属第三方应用ID
    private String appKey;
    //通知目的地址
    private  String notifyURL;
    //呼叫状态事件
    private CallEventType callEvent;
}
