package com.ctw.controller;

import com.ctw.entity.Device;
import com.ctw.entity.Result;
import com.ctw.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "设备表控制器")
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    DeviceService deviceService;

    @ApiOperation("测试类")
    @GetMapping("/test")
    public Result<Device> test() {
        Result<Device> result = new Result<>();
        Device device = new Device();
        device.setDeviceName("温湿度传感器");
        device.setIsOnline(false);
        device.setDataType(0);
        device.setUpdateTime(new Date());
        deviceService.insertDevice(device);
        result.success(device);
        return result;
    }

    @ApiOperation("插入设备")
    @PostMapping("/insertDevice")
    public Result<Device> insertDevice(
            @ApiParam(value = "设备名称")
            @RequestParam(value = "deviceName", required = false) String deviceName,
            @ApiParam(value = "是否在线")
            @RequestParam(value = "isOnline", required = false) Boolean isOnline,
            @ApiParam(value = "数据类型")
            @RequestParam(value = "dataType", required = false) Integer dataType
    ) {
        Result<Device> result = new Result<>();
        if (dataType > 3 || dataType < 0) {
            result.failure("数据类型有误，取值0 ~ 3");
            return result;
        }
        Device device = new Device();
        device.setDeviceName(deviceName);
        device.setIsOnline(isOnline);
        device.setDataType(dataType);
        device.setUpdateTime(new Date());
        deviceService.insertDevice(device);
        result.success(device);
        return result;
    }

    @ApiOperation("根据设备id查找")
    @GetMapping("/getDeviceById")
    public Result<Device> getDeviceById(
            @ApiParam(value = "设备Id", required = true)
            @RequestParam(value = "deviceId") int deviceId
    ) {
        Result<Device> result = new Result<>();
        Device device = deviceService.findDeviceById(deviceId);
        if (device == null) {
            result.failure("该id设备不存在");
        } else {
            result.success(device, "查找成功");
        }
        return result;
    }

    @ApiOperation("查找所有设备")
    @GetMapping("/getDevices")
    public Result<List<Device>> getDevices() {
        Result<List<Device>> result = new Result<>();
        List<Device> devices = deviceService.findDevices();
        if (devices == null) {
            result.failure("设备列表为空");
        } else {
            result.success(devices, "查找成功");
        }
        return result;
    }

    @ApiOperation("更新设备")
    @PostMapping("/updateDeviceById")
    public Result<Device> updateDeviceById(
            @ApiParam(value = "设备Id", required = true)
            @RequestParam(value = "deviceId") int deviceId,
            @ApiParam(value = "设备名称")
            @RequestParam(value = "deviceName", required = false) String deviceName,
            @ApiParam(value = "是否在线")
            @RequestParam(value = "isOnline", required = false) Boolean isOnline,
            @ApiParam(value = "数据类型")
            @RequestParam(value = "dataType", required = false) Integer dataType
    ) {
        Result<Device> result = new Result<>();
        if (deviceService.findDeviceById(deviceId) == null) {
            result.failure("该id设备不存在");
            return result;
        }
        if (dataType != null && (dataType > 3 || dataType < 0)) {
            result.failure("数据类型有误，取值0 ~ 3");
            return result;
        }

        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceName(deviceName);
        device.setIsOnline(isOnline);
        device.setDataType(dataType);
        Device updateDevice = deviceService.updateDevice(device);
        if (updateDevice == null) {
            result.failure("修改失败");
        } else {
            result.success(updateDevice, "修改成功");
        }
        return result;
    }

    @ApiOperation("根据id删除设备")
    @PostMapping("/removeDeviceById")
    public Result<Device> removeDeviceById(
            @ApiParam(value = "设备Id", required = true)
            @RequestParam(value = "deviceId") int deviceId
    ) {
        Result<Device> result = new Result<>();
        if (deviceService.findDeviceById(deviceId) == null) {
            result.failure("该id设备不存在");
            return result;
        }
        if (deviceService.removeDevice(deviceId)) {
            result.success("删除成功");
        } else {
            result.failure("删除失败");
        }
        return result;
    }


}
