package com.example.dome.controller;

import com.example.dome.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* @author wcy
 * \* @date: 2020-06-05 14:52
 * \* Description:  类
 * \
 */
@RestController
public class TaskController {
    @Autowired
    private AsyncTaskService asyncTaskService;

    @RequestMapping("/task")
    public String task(){
        for (int i =0;i<10;i++){
            asyncTaskService.intTask(i);
            asyncTaskService.stringTask(String.valueOf(i));
        }
        return null;
    }

}
