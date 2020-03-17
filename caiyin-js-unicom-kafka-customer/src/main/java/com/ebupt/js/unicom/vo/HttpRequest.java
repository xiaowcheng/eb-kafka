package com.ebupt.js.unicom.vo;

import lombok.Data;

@Data
public class HttpRequest {
    
    private Object header;
    
    private CallLog body;
    
    public HttpRequest(Object header, CallLog body) {
        this.header = header;
        this.body = body;
    }
}
