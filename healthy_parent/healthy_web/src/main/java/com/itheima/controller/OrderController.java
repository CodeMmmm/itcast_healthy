package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orderWeb")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 查询分页
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage",method = RequestMethod.POST)
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = orderService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_SUCCESS);
        }
    }

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public Result submit(@RequestParam List<Integer> setmealIds, @RequestBody Map map){
        try {
            if(setmealIds == null || setmealIds.size() == 0){
                return new Result(false,MessageConstant.NOT_SET_SETMEAL);
            }
            if(setmealIds.size() > 1){
                return new Result(false,MessageConstant.ONLY_SELECT_ONESETMEAL_ONCE);
            }
            map.put("orderType","电话预约");
            Result result = orderService.submit(setmealIds.get(0),map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDER_FAIL);
        }
    }
}
