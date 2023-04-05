package com.ctw.service.impl;

import com.ctw.entity.Device;
import com.ctw.service.DeviceService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    MongoTemplate template;

    @Override
    public void insertDevice(Device device) {
        Query query = new Query();
        //获取最大的Id
        query.with(Sort.by(Sort.Direction.DESC, "deviceId"));
        query.limit(1);
        Device maxIdDevice = template.findOne(query, Device.class);
        //手动id自增
        device.setDeviceId(maxIdDevice.getDeviceId() + 1);
        template.insert(device);
    }

    @Override
    public Device findDeviceById(int deviceId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(deviceId));
        return template.findOne(query, Device.class);
    }

    @Override
    public Device updateDevice(Device device) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(device.getDeviceId()));
        Update update = new Update();
        if (device.getDeviceName() != null) {
            update.set("deviceName", device.getDeviceName());
        }
        if (device.getDataType() != null) {
            update.set("dataType", device.getDataType());
        }
        if (device.getIsOnline() != null) {
            update.set("isOnline", device.getIsOnline());
        }
        update.set("updateTime", new Date());
        if (template.updateFirst(query, update, Device.class).wasAcknowledged()) {
            return findDeviceById(device.getDeviceId());
        }
        return null;
    }

    @Override
    public boolean removeDevice(int deviceId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(deviceId));
        return template.remove(query, Device.class).getDeletedCount() >= 1;
    }

    @Override
    public List<Device> findDevices() {
        return template.findAll(Device.class);
    }
}
