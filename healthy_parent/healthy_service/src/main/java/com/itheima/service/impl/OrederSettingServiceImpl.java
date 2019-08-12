package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrederSettingServiceImpl implements OrderSettingService{

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约
     * @param orderSettings
     */
    @Override
    public void upload(List<OrderSetting> orderSettings) {
        // 根据日期，如果数据库中有该日期，更新次数，没有则新增
        for (OrderSetting orderSetting : orderSettings) {
            this.editNumber(orderSetting);
        }
    }

    /**
     * 获取预约设置数据
     * @param preDate
     * @param sufDate
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String preDate, String sufDate) {
//        [
//        { date: 1, number: 120, reservations: 1 },
//        { date: 3, number: 120, reservations: 1 },
//        { date: 4, number: 120, reservations: 120 },
//        { date: 6, number: 120, reservations: 1 },
//        { date: 8, number: 120, reservations: 1 }
//                ];
        // 查询到现有月份的数据对象集合
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(preDate,sufDate);
        //  遍历集合，转换成map集合
        if (orderSettingList != null && orderSettingList.size() > 0){
            List<Map<String,Object>> mapList = new ArrayList<>();
            for (OrderSetting orderSetting : orderSettingList) {
                Map<String,Object> orderMap = new HashMap<>();
                orderMap.put("date",orderSetting.getOrderDate().getDate());
                orderMap.put("number",orderSetting.getNumber());
                orderMap.put("reservations",orderSetting.getReservations());
                mapList.add(orderMap);
            }
            return mapList;
        }
        return null;
    }

    /**
     * 单个设置预约
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        this.editNumber(orderSetting);
    }

    /**
     * 清理过时预约数据
     * @param firstDay4ThisMonth
     */
    @Override
    public void clearOldOrdersetting(String firstDay4ThisMonth) {
        orderSettingDao.clearOldOrdersetting(firstDay4ThisMonth);
    }

    /**
     * 设置预约
     * @param orderSetting
     */
    public void editNumber(OrderSetting orderSetting){
        // 查询是否已有记录
        int count = orderSettingDao.findByDate(orderSetting.getOrderDate());
        if (count > 0){
            // 该日期有预约，更新次数
            orderSettingDao.updateByDate(orderSetting);
        }else {
            // 新增记录
            orderSettingDao.save(orderSetting);
        }
    }
}
