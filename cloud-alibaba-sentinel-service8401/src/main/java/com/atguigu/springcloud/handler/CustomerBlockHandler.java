package com.atguigu.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult HandlerException(BlockException exception) {
        return new CommonResult(4444, "自定义限流测试OK,global handlerException---0");
    }
    public static CommonResult HandlerException1(BlockException exception) {
        return new CommonResult(4444, "自定义限流测试OK,global handlerException---1");
    }
}
