package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    /**
     * 根据日期查询是否设置预约次数
     * @param orderDate
     * @return
     */
    int findByDate(Date orderDate);

    /**
     * 根据日期更新预约次数
     */
    void updateByDate(OrderSetting orderSetting);

    /**
     * 新增预约次数
     * @param orderSetting
     */
    void save(OrderSetting orderSetting);

    /**
     * 获取预约设置数据
     * @param preDate
     * @param sufDate
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(@Param("preDate") String preDate, @Param("sufDate") String sufDate);

    /**
     * 根据日期查询是否已设置预约
     * @param date
     * @return
     */
    OrderSetting CheckOrderByDate(Date date);

    /**
     * 设置已预约次数
     * @param orderSetting
     */
    void editReservationsByOrderDate(OrderSetting orderSetting);

    /**
     * 清理过时预约数据
     * @param firstDay4ThisMonth
     */
    void clearOldOrdersetting(String firstDay4ThisMonth);
}
