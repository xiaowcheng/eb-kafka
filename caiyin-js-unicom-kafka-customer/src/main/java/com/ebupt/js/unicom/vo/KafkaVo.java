package com.ebupt.js.unicom.vo;

import com.ebupt.js.unicom.exception.BizException;
import com.ebupt.js.unicom.exception.Remarks;
import lombok.Data;

@Data
public class KafkaVo {
    //用户最近一次产生信令的时间 毫秒数，即将标准unix时间转成毫秒
    private String occurTime;
    //满足条件手机号
    private String phoneNumber;
    //对端电话
    private String phoneNumber_R;
    //MO/MT状态码 0：主叫 1：被叫
    private String momt;
    //振铃状态码 2：振铃 4：挂机
    private String serviceType;
    
    public boolean validate(){
        //固话
        String regexp = "^(\\+\\d{2}-)?0\\d{2,3}-\\d{7,8}$";
        //国内手机号
        String CN = "^(\\+?0{0,2}86\\-?)?1[3456789]\\d{9}$";
    
        if (phoneNumber_R  == null) {
            throw new BizException(Remarks.PARAMERROR);
        } else if (phoneNumber_R.matches(regexp)) {
            throw new BizException(Remarks.TELEPHONE);
        } else if (phoneNumber_R.matches(CN)) {
        
        } else {
            throw new BizException(Remarks.INTERNATIONCALL);
        }
    
        if(momt == null || !("0".equals(momt) || "1".equals(momt))){
            throw new BizException(Remarks.MOMTERROR);
        }
        
        if(phoneNumber == null){
            throw new BizException(Remarks.PARAMERROR);
        }else if( !phoneNumber.matches("^\\+?\\d{1,15}")){
            throw new BizException(Remarks.PARAMERROR);
        }
        
        if(serviceType == null || !("2".equals(serviceType) || "4".equals(serviceType)) ){
            throw new BizException(Remarks.PARAMERROR);
        }
        
        return true;
    };
}
