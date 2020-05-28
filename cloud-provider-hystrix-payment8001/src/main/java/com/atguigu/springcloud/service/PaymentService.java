package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfoOK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfoOK, id：" + id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfoTimeout(Integer id) {
        int timenumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timenumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + " paymentInfoTimeout, id：" + id +
                " 耗时： " + timenumber;
    }

    public String paymentInfoTimeoutHandler(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfoTimeoutHandler, id：" + id;
    }

    // 服务熔断，具体配置参数查看HystrixCommandProperties类
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),    // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),    // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),    // 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {

        if (id < 0) {
            throw new RuntimeException("******** id 不能为负数！");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试！id：" + id;
    }
}
