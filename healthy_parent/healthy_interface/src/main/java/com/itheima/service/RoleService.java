package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;


import java.util.List;
import java.util.Map;

public interface RoleService {
    PageResult findRoleAll(QueryPageBean queryPageBean);

    List<Permission> findPermissionAll();

    void addRole(Integer[] menuIds, Integer[] permissions, Role role);

    Role findoneRole(Integer roleId);

    Map roleAndPermissionByRoleId(Integer roleId);

    void edit(Integer[] menuIds, Integer[] permissionsIds, Role role);

    void delete(Integer roleId);

    List<Role> findRolesAll();
}
