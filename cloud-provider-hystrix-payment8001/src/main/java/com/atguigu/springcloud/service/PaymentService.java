package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfoOK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfoOK, id：" + id;
    }

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
}
