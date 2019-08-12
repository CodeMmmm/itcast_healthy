package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.ValidateCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Reference
    private ValidateCodeService validateCodeService;

    /**
     * 预约发送验证码
     */
    @RequestMapping(value = "/0",method = RequestMethod.POST)
    public Result send4Order(String telephone){
        try {
            validateCodeService.send4Order(telephone);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);

        }
    }

    /**
     * 登录发送验证码
     */
    @RequestMapping(value = "/send4Login",method = RequestMethod.POST)
    public Result send4Login(String telephone){
        try {
            validateCodeService.send4Login(telephone);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);

        }
    }
}
