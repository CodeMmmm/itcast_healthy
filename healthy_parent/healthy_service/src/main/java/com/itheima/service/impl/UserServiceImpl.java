package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUserName(username);
        Set<Role> set= roleDao.findRoleByUserId(user.getId());
        for (Role role : set) {
            role.setPermissions(permissionDao.findPermissionsByRoleId(role.getId()));
        }
        user.setRoles(set);
        return user;
    }


    @Override
    public PageResult findUserAll(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<User> page= userDao.findUserByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void addUser(User user, Integer[] rolesIds) {

        userDao.addUser(user);
        Integer id = user.getId();
        //将添加关联
        addUserAndRole(rolesIds, id);
    }

    private void addUserAndRole(Integer[] rolesIds, Integer id) {
        if (rolesIds!=null&&rolesIds.length>0) {
            for (Integer rolesId : rolesIds) {
                userDao.addUserAndRole(id,rolesId);
            }
        }
    }

    @Override
    public User findoneUser(Integer userId) {
        return userDao.findoneUser(userId);
    }

    @Override
    public List<Integer> userAndRoleByUserId(Integer userId) {
        return roleDao.findRolesIdByUserId(userId);
    }

    @Override
    public void edit(User user, Integer[] rolesIds) {
        userDao.updateUser(user);
        Integer id = user.getId();
        //删除中间表
        userDao.deleteUserAndRole(id);
        //在添加中间表数据
        addUserAndRole(rolesIds,id);
    }

    @Override
    public void findUserName(String username) {
        User userByUserName = userDao.findUserByUserName(username);
        if (userByUserName!=null) {
            throw new RuntimeException("用户名已存在");
        }
    }

    @Override
    public void delete(Integer userId) {
        userDao.deleteUserAndRole(userId);
        userDao.deleteUserById(userId);
    }


}
