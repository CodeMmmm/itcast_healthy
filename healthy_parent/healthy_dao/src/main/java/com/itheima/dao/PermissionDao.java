package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    /**
     * 根据角色id查找权限
     * @param id
     * @return
     */
    Set<Permission> findPermissionsByRoleId(Integer id);
    /**
     * 根据条件查询权限项
     * @param queryString
     * @return
     */
    Page<Permission> checkPage(String queryString);

    void addPermission(Permission permission);

    Permission findoneByid(Integer id);
    /**
     * 更新权限项
     * @param  permission
     * @return
     */
    void editPermission(Permission permission);

    void deletePermission(Integer id);

    List<Permission> findPermissionAll();
}
