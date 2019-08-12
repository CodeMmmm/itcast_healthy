package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 分页查询检查项
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Member> page = memberDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增会员
     * @param member
     */
    @Override
    public void add(Member member) {
        //获得当前日期
        Date regTime = new Date();
        member.setRegTime(regTime);
        memberDao.add(member);
    }

    /**
     * 根据id查询会员
     * @param id
     * @return
     */
    @Override
    public Member findItemById(Integer id) {
        return memberDao.findItemById(id);
    }

    /**
     * 更新会员信息
     * @param member
     */
    @Override
    public void update(Member member) {
        memberDao.update(member);
    }

    /**
     * 删除会员
     * @param id
     */
    @Override
    public void deleteMember(Integer id) {
        memberDao.deleteMember(id);
    }
}