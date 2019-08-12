package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import org.springframework.stereotype.Component;

/**
 * 定时清理过时预约数据
 */
@Component
public class ClearOrdersetting {
    @Reference
    private OrderSettingService orderSettingService;

    public void clearOldOrdersetting() throws Exception {
        // 获取当前月份的第一天，
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        orderSettingService.clearOldOrdersetting(firstDay4ThisMonth);
    }
}
