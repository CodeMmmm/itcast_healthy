package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {
    /**
     * 根据月份查找会员数
     *
     * @param list
     * @param monthList
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> list, List<String> monthList);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据id查询会员
     * @param id
     * @return
     */
    Member findItemById(Integer id);

    /**
     * 更新会员
     * @param member
     */
    void update(Member member);

    /**
     * 删除会员
     * @param id
     */
    void deleteMember(Integer id);
}
