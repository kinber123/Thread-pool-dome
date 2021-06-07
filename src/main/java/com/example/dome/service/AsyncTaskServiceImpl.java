package com.example.dome.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * \* @author wcy
 * \* @date: 2020-06-05 14:45
 * \* Description:  类
 * \
 */
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService{

    private static final Logger logger  = LoggerFactory.getLogger(AsyncTaskService.class);

    @Override
    @Async("taskExecutor")
    public void intTask(int i){
        try {
            Thread.sleep(1000);
            logger.info(Thread.currentThread().getName()+"---int--"+i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async("taskExecutor")
    public void stringTask(String str){
        try {
            Thread.sleep(2000);
            logger.info(Thread.currentThread().getName()+"---string--"+str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
