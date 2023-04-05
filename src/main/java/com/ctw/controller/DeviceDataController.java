package com.ctw.controller;

import com.ctw.entity.Device;
import com.ctw.entity.DeviceData;
import com.ctw.entity.Result;
import com.ctw.service.DeviceDataService;
import com.ctw.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "设备表数据控制器")
@RestController
@RequestMapping("/deviceData")
public class DeviceDataController {

    @Resource
    DeviceDataService deviceDataService;
    @Resource
    DeviceService deviceService;

    @ApiOperation("插入设备数据")
    @PostMapping("/insertDeviceData")
    public Result<DeviceData> insertDevice(
            @ApiParam(value = "设备id", required = true)
            @RequestParam(value = "deviceId") int deviceId,
            @ApiParam(value = "数据", required = true)
            @RequestParam(value = "data") String data
    ) {
        Device device = deviceService.findDeviceById(deviceId);
        Result<DeviceData> result = new Result<>();
        if (device == null) {
            result.failure("该设备id不存在");
        } else {
            DeviceData deviceData = deviceDataService.insertDeviceData(deviceId, data);
            result.success(deviceData, "插入数据成功");
        }
        return result;
    }

    @ApiOperation("根据设备id查找")
    @GetMapping("/getDeviceDataById")
    public Result<List<DeviceData>> getDeviceById(
            @ApiParam(value = "设备Id", required = true)
            @RequestParam(value = "deviceId") int deviceId
    ) {
        Device device = deviceService.findDeviceById(deviceId);
        Result<List<DeviceData>> result = new Result<>();
        if (device == null) {
            result.failure("该设备id不存在");
        } else {
            List<DeviceData> list = deviceDataService.findDataListById(deviceId);
            result.success(list);
        }
        return result;
    }

}
