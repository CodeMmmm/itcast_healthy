package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 批量导入预约
     * @param orderSettings
     */
    void upload(List<OrderSetting> orderSettings);

    /**
     * 获取预约设置数据
     * @param preDate
     * @param sufDate
     * @return
     */
    List<Map<String,Object>> getOrderSettingByMonth(String preDate, String sufDate);

    /**
     * 单个设置预约
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);

    /**
     * 清理过时预约数据
     * @param firstDay4ThisMonth
     */
    void clearOldOrdersetting(String firstDay4ThisMonth);
}
