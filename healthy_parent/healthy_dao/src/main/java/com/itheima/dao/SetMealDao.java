package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    /**
     * 查询分页
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

    /**
     * 新增套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 新增中间表记录
     * @param map
     */
    void setSetmealAndCheckGroup(Map<String, Integer> map);

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
     * 获取热门套餐
     * @return
     */
    List<Map> findHotmeal();

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> findAllSetmeal();
}
