package com.itheima.dao;

import com.itheima.pojo.Member;

public interface MemberDao {
    /**
     * 根据电话号码判断是否已注册
     * @param telephone
     * @return
     */
    Member isMember(String telephone);

    /**
     * 新用户注册
     * @param member
     */
    void add(Member member);

    /**
     * 根据月份查找会员数
     * @param month
     * @return
     */
    Integer findCountByMonth(String month);

    /**
     * 当日新增会员数
     * @param reportDate
     * @return
     */
    Integer findMemberCountByDate(String reportDate);

    /**
     * 总会员数
     * @return
     */
    Integer findMemberTotalCount();

    /**
     * 本周新增会员数
     * @param thisWeekMonday
     * @return
     */
    Integer findMemberCountAfterDate(String thisWeekMonday);

    public Integer findMemberCountBeforeDate(String date);

}
