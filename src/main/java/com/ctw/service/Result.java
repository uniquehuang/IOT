package com.ctw.service;

/**
 * @author cjq
 * @program: SSMStudy
 * @description: 返回html消息类
 * @date 2021-04-20 09:06:57
 */
public class Result {
    private int status = 1;    //状态，成功：0，失败：1
    private String msg;    //消息
    private Object data;    //数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}
