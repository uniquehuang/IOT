package com.ctw.util;

import java.util.ArrayList;
import java.util.List;

public class SingleMessage {

    private static volatile SingleMessage message;

    private final List<String> list;

    private SingleMessage() {
        list = new ArrayList<>();
    }

    public static SingleMessage getMessage() {
        if (message == null) {
            synchronized (SingleMessage.class) {
                if (message == null) {
                    message = new SingleMessage();
                }
            }
        }
        return message;
    }

    public void addMessage(String s) {
        list.add(s);
    }

    public List<String> getList() {
        return list;
    }
}
