package com.ebupt.js.unicom.exception;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

public enum Remarks implements ErrorCode {
    SUCCESS(0,"成功"),
    INTERNATIONCALL(1,"国际号码"),
    TELEPHONE(2,"固定电话"),
    FREQUENCY(3,"redis频控"),
    OTHER(4,"其他错误"),
    MOMTERROR(5,"主被叫类型错误"),
    PARAMERROR(6,"参数格式错误");
    private int errorCode;
    private String errorDesc;
    
    private  Remarks(int errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
    public Remarks getByCode(int code){
        for (Remarks remark:
            Remarks.values() ) {
            if(code == remark.getCode()){
                return remark;
            }
        }
        return this.OTHER;
    }
    public boolean getByValue(String desc){
        for (Remarks remark:
             Remarks.values()) {
            if(StringUtils.equals(desc,remark.getDesc())){
                return true;
            }
        }
        return  false;
    }
    @Override
    public int getCode() {
        return errorCode;
    }
    @Override
    public String getDesc(){
        return errorDesc;
    }
}
