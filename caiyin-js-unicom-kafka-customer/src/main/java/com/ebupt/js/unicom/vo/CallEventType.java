package com.ebupt.js.unicom.vo;

import lombok.Data;

@Data
public class CallEventType {
    
    //呼叫标识 至少72小时唯一 不超过40
    private String callIdentifer;
    
    //主叫号码
    private String calling;
    
    //被叫号码
    private String called;
    
    //呼叫流程 mo：主叫 mt：被叫
    private String direction;
    
    //呼叫状态事件
    private String event;
    
    //时间戳
    private String timeStamp;
}
