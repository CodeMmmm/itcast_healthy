package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 微信预约
     * @param map
     * @return
     */
    Result submit(Map map) throws Exception;

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
     * 查询预约分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 电话预约
     * @param setmealId
     * @param map
     * @return
     */
    Result submit(Integer setmealId, Map map) throws Exception;

}
