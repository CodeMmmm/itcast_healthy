package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    /**
     * 查询是否重复预约
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 新增
     * @param order
     */
    void add(Order order);

    /**
     * 返回成功信息
     * @param id
     * @return
     */
    Map<String,Object> findById(Integer id);

    /**
     * 根据套餐id查找预约该套餐的数量
     * @param id
     * @return
     */
    Integer findCountByStemealId(Integer id);

    /**
     * 本月预约数
     * @param month
     * @return
     */
    Integer findOrderCountByMonth(String month);

    /**
     * 今日预约数
     * @param today
     * @return
     */
    Integer findOrderCountByDate(String today);

    /**
     * 本周预约数
     * @param thisWeekMonday
     * @return
     */
    Integer findOrderCountAfterDate(String thisWeekMonday);

    /**
     * 今日到诊数
     * @param today
     * @return
     */
    Integer findVisitsCountByDate(String today);

    /**
     * 本周到诊数
     * @param thisWeekMonday
     * @return
     */
    Integer findVisitsCountAfterDate(String thisWeekMonday);

    /**
     * 有条件的分页查询
     * @param queryString
     * @return
     */
    Page<Map> findPageByCondition(String queryString);
}
