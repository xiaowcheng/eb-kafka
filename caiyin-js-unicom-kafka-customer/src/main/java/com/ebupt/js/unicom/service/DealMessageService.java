package com.ebupt.js.unicom.service;

import com.ebupt.js.unicom.vo.KafkaVo;
import org.springframework.stereotype.Service;

public interface DealMessageService {
    
    public void dealMessage(KafkaVo kafkaVo);
}
