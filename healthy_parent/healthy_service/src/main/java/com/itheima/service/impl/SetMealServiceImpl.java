package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService{

    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 查询分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增套餐
     * @param checkgroupIds
     * @param setmeal
     */
    @Override
    public void add(List<Integer> checkgroupIds, Setmeal setmeal) {
        // 先新增setmeal记录，返回自增主键
        setMealDao.add(setmeal);
        jedisPool.getResource().srem(RedisConstant.setmeal_List);
        if(checkgroupIds != null && checkgroupIds.size() > 0){
            // 再向中间表添加记录
            this.setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
        // 存入数据库的文件名存入redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    /**
     * 查询套餐列表数据
     * @return
     */
    @Override
    public List<Setmeal> getSetmeal() {

        List<Setmeal> list=null;
        String srtmealList = jedisPool.getResource().get(RedisConstant.setmeal_List);
        list= (List<Setmeal>) JSON.parse(srtmealList);
        if (list==null){
            list = setMealDao.getSetmeal();
            String listjson = (String) JSON.toJSONString(list);
            jedisPool.getResource().set(RedisConstant.setmeal_List,listjson);
        }


        return list;
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal=null;
        String s = jedisPool.getResource().get(RedisConstant.setmeal_List);
        List<Setmeal> list = (List<Setmeal>) JSON.parseArray(s,Setmeal.class);
        if (list!=null){
            for (Setmeal setmeal1 : list) {
                if (setmeal1.getId().equals(id)){
                    setmeal=setmeal1;
                    break;
                }
            }
        }else {
           setmeal= setMealDao.findById(id);
        }
        return setmeal;
    }

    /**
     * 查找出预约的套餐名和数量
     * @return
     */
    @Override
    public List<Map> findSetmealCount() {
        return setMealDao.findSetmealCount();
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Setmeal> findAllSetmeal() {
        return setMealDao.findAllSetmeal();
    }

    /**
     * 中间表新增记录
     * @param id
     * @param checkgroupIds
     */
    public void setSetmealAndCheckGroup(Integer id,List<Integer> checkgroupIds){
        Map<String,Integer> map = new HashMap<>();
        map.put("setmealId",id);
        for (Integer checkgroupId : checkgroupIds) {
            map.put("checkgroupId",checkgroupId);
            setMealDao.setSetmealAndCheckGroup(map);
        }
    }
}
