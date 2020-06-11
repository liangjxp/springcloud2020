package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testa")
    public String testA(){
        return "------testA";
    }

    @GetMapping("/testb")
    public String testB(){
        return "------testB";
    }

    @GetMapping("/testd")
    public String testD(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试RT");
        return "------testD";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotkey")
    // value 通常与Mapping保持相同，去掉/，热点资源名使用它
    // 一定配blockHandler，否则抛异常至前端，不友好
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }

    public String deal_testHotkey(String p1, String p2, BlockException exception) {
        return "-----deal_testHotKey";
//        sentinel系统默认提示：Blocked by Sentinel(flow limiting)
    }
}
