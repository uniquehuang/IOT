package com.ctw.service;

import com.ctw.entity.Device;

import java.util.List;

public interface DeviceService {

    //插入一个对象
    void insertDevice(Device device);

    //根据id查找
    Device findDeviceById(int deviceId);

    //根据id修改
    Device updateDevice(Device device);

    //插入一个对象
    boolean removeDevice(int deviceId);

    List<Device> findDevices();
}
