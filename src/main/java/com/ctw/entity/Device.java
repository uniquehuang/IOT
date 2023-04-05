package com.ctw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Device {
    private int deviceId;
    private String deviceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    private Boolean isOnline;
    private Integer dataType;//0,1,2,3
}
