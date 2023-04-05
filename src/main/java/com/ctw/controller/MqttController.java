package com.ctw.controller;

import com.ctw.entity.Result;
import com.ctw.service.MqttService;
import com.ctw.util.SingleMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "Mqtt控制器")
@Slf4j
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Resource
    private MqttService mqttService;

    @ApiOperation("发送消息")
    @PostMapping("/mqttSend")
    public String testSend(
            @ApiParam(value = "主题", required = true)
            @RequestParam(value = "topic") String topic,
            @ApiParam(value = "内容", required = true)
            @RequestParam(value = "content") String content
    ) {
        try {
            this.mqttService.send(topic, content);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("发送失败", ex);
            return "发送失败";
        }
        return "发送成功";
    }

    @ApiOperation("轮询查询")
    @GetMapping("/polling")
    public Result<List<String>> polling() {
        Result<List<String>> result = new Result<>();
        List<String> list = SingleMessage.getMessage().getList();
        result.success(list);
        return result;
    }
}