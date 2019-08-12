package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;


    /**
     * 根据月份查找会员
     *
     * @param list
     *
     * @return
     */
    @Override
    public List<Integer> findMemberCountByMonth(List<String> list, List<String> monthBetween) {

        //循环查询，每个月的会员数量
        List<Integer> monthCount = new ArrayList<>();
        if (monthBetween == null ||monthBetween.size() <= 0){
            for (String m : list) {
                m = m + "-31";//拼接为2019-04-31，查询每天的会员数量
                Integer count = memberDao.findCountByMonth(m);
                monthCount.add(count);
            }
        }else {
            for (String s : monthBetween) {
                s = s + "-31";//拼接为2019-04-31，查询每天的会员数量
                Integer count = memberDao.findCountByMonth(s);
                monthCount.add(count);
            }
        }
        return monthCount;

    }
}
