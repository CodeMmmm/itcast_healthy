package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);
    PageResult findUserAll(QueryPageBean queryPageBean);

    void addUser(User user, Integer[] rolesIds);

    User findoneUser(Integer userId);

    List<Integer> userAndRoleByUserId(Integer userId);

    void edit(User user, Integer[] rolesIds);

    void findUserName(String username);

    void delete(Integer userId);


}
