package com.ctw.service;

public interface MqttService {

    void send(String topic, String content) throws Exception;

    void handler(String message) throws Exception;
}
