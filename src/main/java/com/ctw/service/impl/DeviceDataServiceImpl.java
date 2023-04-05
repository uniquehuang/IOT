package com.ctw.service.impl;

import com.ctw.entity.DeviceData;
import com.ctw.service.DeviceDataService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Resource
    MongoTemplate template;

    @Override
    public DeviceData insertDeviceData(int deviceId, String data) {
        DeviceData deviceData = new DeviceData();
        Query query = new Query();
        //获取最大的Id
        query.with(Sort.by(Sort.Direction.DESC, "deviceDataId"));
        query.limit(1);
        DeviceData maxIdDevice = template.findOne(query, DeviceData.class);
        if (maxIdDevice == null) {
            deviceData.setDeviceDataId(1);
        } else {
            deviceData.setDeviceDataId(maxIdDevice.getDeviceDataId() + 1);
        }
        deviceData.setDeviceId(deviceId);
        deviceData.setData(data);
        deviceData.setCreateTime(new Date());
        return template.insert(deviceData);
    }

    @Override
    public List<DeviceData> findDataListById(int deviceId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(deviceId));
        return template.find(query, DeviceData.class);
    }
}
