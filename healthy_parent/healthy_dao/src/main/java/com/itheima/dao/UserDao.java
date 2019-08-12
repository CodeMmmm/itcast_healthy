package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);
    Page<User> findUserByCondition(String queryString);

    void addUserAndRole(@Param("userId") Integer id, @Param("roleId") Integer roleId);

    void addUser(User user);

    User findoneUser(Integer userId);

    void updateUser(User user);

    void deleteUserAndRole(Integer id);

    void deleteUserById(Integer userId);

    User findUserByUserName(String username);

}
