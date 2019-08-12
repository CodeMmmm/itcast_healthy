package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 微信预约
     * @param map
     * @return
     */
    @Override
    public Result submit(Map map) throws Exception {
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
        // 1.判断该日期是否能预约，不能则返回
        OrderSetting orderSetting = orderSettingDao.CheckOrderByDate(date);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 2.判断该日期是否预约满员，满员则返回
        int number = orderSetting.getNumber();// 可预约人数
        int reservations = orderSetting.getReservations();// 已预约人数
        if(reservations >= number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        // 3.判断用户是否注册，没注册则注册，注册的话防止重复预约。
        String telephone = (String) map.get("telephone");
        Member member = memberDao.isMember(telephone);
        if(member != null){
            // 已注册,防止重复预约
            Order order = new Order(member.getId(),date,null,null,setmealId);
            List<Order> orederList = orderDao.findByCondition(order);
            if(orederList != null && orederList.size() > 0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {
            // 未注册，注册会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            // 返回自增主键
            memberDao.add(member);
        }
        // 4.在order中新增记录，同时该日期预约次数+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        Order order = new Order(member.getId(),date,(String)map.get("orderType"),"待体检",setmealId);
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 返回成功信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(Integer id) {
        return orderDao.findById(id);
    }

    /**
     * 根据套餐id查找预约该套餐的数量
     * @param id
     * @return
     */
    @Override
    public Integer findCountByStemealId(Integer id) {
        return orderDao.findCountByStemealId(id);
    }

    /**
     * 查询预约分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // id,orderDate,orderType,orderStatus,setmeal
        PageHelper.startPage(currentPage,pageSize);
        Page<Map> page = orderDao.findPageByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 电话预约
     * @param setmealId
     * @param map
     * @return
     */
    @Override
    public Result submit(Integer setmealId, Map map) throws Exception {
        String orderDate = ((String) map.get("orderDate")).split("T")[0];
        Date date = DateUtils.parseString2Date(orderDate);
        // 1.判断该日期是否能预约，不能则返回
        OrderSetting orderSetting = orderSettingDao.CheckOrderByDate(date);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 2.判断该日期是否预约满员，满员则返回
        int number = orderSetting.getNumber();// 可预约人数
        int reservations = orderSetting.getReservations();// 已预约人数
        if(reservations >= number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        // 3.判断用户是否注册，没注册则注册，注册的话防止重复预约。
        String telephone = (String) map.get("phoneNumber");
        Member member = memberDao.isMember(telephone);
        if(member != null){
            // 已注册,防止重复预约
            Order order = new Order(member.getId(),date,null,null,setmealId);
            List<Order> orederList = orderDao.findByCondition(order);
            if(orederList != null && orederList.size() > 0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {
            // 未注册，注册会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            // 返回自增主键
            memberDao.add(member);
        }
        // 4.在order中新增记录，同时该日期预约次数+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        Order order = new Order(member.getId(),date,(String)map.get("orderType"),"待体检",setmealId);
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }
}
