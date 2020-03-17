package com.ebupt.js.unicom.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException {
 
    private static final long serialVersionUID = -7864604160297181941L;
 
    /** 错误码 */
    protected final ErrorCode errorCode;
 
    /**
     * 这个是和谐一些不必要的地方,冗余的字段
     * 尽量不要用
     */
    private String code;
 
    /**
     * 无参默认构造UNSPECIFIED
     */
    public BizException() {
        super(Remarks.OTHER.getDesc());
        this.errorCode = Remarks.OTHER;
    }
 
    /**
     * 指定错误码构造通用异常
     * @param errorCode 错误码
     */
    public BizException(final ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }
}