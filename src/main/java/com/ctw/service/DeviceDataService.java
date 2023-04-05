package com.ctw.service;

import com.ctw.entity.DeviceData;

import java.util.List;

public interface DeviceDataService {

    DeviceData insertDeviceData(int deviceId, String data);

    List<DeviceData> findDataListById(int deviceId);
}
