package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询会员
     * @param queryString
     * @return
     */
    Page<Member> findByCondition(String queryString);

    /**
     * 根据id查询会员
     * @param id
     * @return
     */
    Member findItemById(Integer id);

    /**
     * 更新会员信息
     * @param member
     */
    void update(Member member);

    /**
     * 删除会员
     * @param id
     */
    void deleteMember(Integer id);

    List<Map> getSex();

    Integer findBrithday(@Param("first") String first, @Param("second") String second);
}
