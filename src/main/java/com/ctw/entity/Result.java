package com.ctw.entity;

import lombok.Data;

@Data
public class Result<T> {
    private int resultCode;
    private String resultMsg;
    private T resultData;

    public void success(T resultData) {
        this.setResultCode(200);
        this.setResultMsg("请求成功");
        this.setResultData(resultData);
    }

    public void success(T resultData, String resultMsg) {
        this.setResultCode(200);
        this.setResultMsg(resultMsg);
        this.setResultData(resultData);
    }

    public void success(String resultMsg) {
        this.setResultCode(200);
        this.setResultMsg(resultMsg);
    }

    public void failure(T resultData) {
        this.setResultCode(400);
        this.setResultMsg("请求错误");
        this.setResultData(resultData);
    }

    public void failure(T resultData, String resultMsg) {
        this.setResultCode(400);
        this.setResultMsg(resultMsg);
        this.setResultData(resultData);
    }

    public void failure(String resultMsg) {
        this.setResultCode(400);
        this.setResultMsg(resultMsg);
    }

}
