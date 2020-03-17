package com.ebupt.js.unicom.service;

import com.ebupt.js.unicom.exception.BizException;
import com.ebupt.js.unicom.exception.Remarks;
import com.ebupt.js.unicom.util.HttpUtil;
import com.ebupt.js.unicom.util.RedisUtil;
import com.ebupt.js.unicom.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DealMessageServiceImpl implements DealMessageService {
    
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private HttpUtil httpUtil;
    
    @Value("${redis.timeout}")
    private Integer timeOut;
    
    @Override
    public void dealMessage(KafkaVo kafkaVo) {
        String remark = "0";
        String result = "0";
        try {
            
            int flag = Remarks.SUCCESS.getCode();
            //参数格式校验
            kafkaVo.validate();
    
            //1.redis 频控
            String key = kafkaVo.getPhoneNumber() + "_"+ kafkaVo.getPhoneNumber_R() + "_"+ kafkaVo.getMomt() + "_" + kafkaVo.getServiceType();
            boolean hasKey = redisUtil.hasKey(key);
            if (hasKey) {
                log.error("redis {} 中存在数据，频控生效", key);
                throw new BizException(Remarks.FREQUENCY);
            }
            //存储redis
            redisUtil.set(key, "1", timeOut);
    
            //2.发往 呼叫
            httpUtil.CallMessage(kafkaVo);
        }catch (BizException e){
            log.error(e.getErrorCode().getDesc());
            remark = String.valueOf(e.getErrorCode().getCode());
            result = "1";
        
        }catch (Exception e){
            e.printStackTrace();
            log.error("未知异常");
            remark = String.valueOf(Remarks.OTHER.getCode());
            result = "1";
        }finally {
            //呼叫日志
            CallLog callLog = new CallLog();
            callLog.setSrc_msisdn(kafkaVo.getPhoneNumber());
            callLog.setDest_msisdn(kafkaVo.getPhoneNumber_R());
            callLog.setCallStatus(kafkaVo.getMomt());
            callLog.setCallType(kafkaVo.getServiceType());
            callLog.setCallTime(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(kafkaVo.getOccurTime())), ZoneId.systemDefault())));
//            callLog.setCallTime(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
            callLog.setResult(result);
    
            callLog.setRemarks(remark);
            try {
                httpUtil.CallLog(callLog);
            } catch (Exception e) {
                log.error("呼叫日志下发失败",e);
            }
        }
    }
    
}
