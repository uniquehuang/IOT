package com.ctw.service.impl;

import com.ctw.service.MqttService;
import com.ctw.util.SingleMessage;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

public class MqttServiceImpl implements MqttService {

    @Resource
    MqttPahoMessageHandler mqttHandler;

    @Override
    public void send(String topic, String content) throws Exception {
        // 构建消息
        Message<String> messages = MessageBuilder
                .withPayload(content)
                .setHeader(MqttHeaders.TOPIC, topic)
                .build();
        // 发送消息
        mqttHandler.handleMessage(messages);
    }

    @Override
    public void handler(String message) throws Exception {
        System.out.println("收到消息：" + message);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        String s = new String(bytes);
        System.out.println(s);
        SingleMessage.getMessage().addMessage(message);
    }
}
