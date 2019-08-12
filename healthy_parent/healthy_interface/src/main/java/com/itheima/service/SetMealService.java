package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    /**
     * 查询分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增套餐
     * @param checkgroupIds
     * @param setmeal
     */
    void add(List<Integer> checkgroupIds, Setmeal setmeal);

    /**
     * 查询套餐列表数据
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查找出预约的套餐名和数量
     * @return
     */
    List<Map> findSetmealCount();

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> findAllSetmeal();
}
