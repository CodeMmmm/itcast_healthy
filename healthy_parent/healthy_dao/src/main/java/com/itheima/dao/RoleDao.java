package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    /**
     * 根据用户id查找角色
     * @param id
     * @return
     */
    Set<Role> findRoleByUserId(Integer id);
    Page<Role> findRole(String queryString);

    void addRole(Role role);

    void addRoleAndpermission(@Param("roleid") Integer id, @Param("permissionid") Integer permission);

    Role findoneRole(Integer roleId);

    List<Integer> roleAndPermissionByRoleId(Integer roleId);

    void updateByRole(Role role);

    void deleteroleAndPermissionByRoleId(Integer id);

    void delete(Integer roleId);

    List<Integer> roleAndMenuByRoleId(Integer roleId);

    void addRoleAndMenu( @Param("menuid")Integer menuId, @Param("roleid")Integer id);

    void deleteroleAndMenuByRoleId(Integer id);

    List<Role> findRolesAll();

    List<Integer> findRolesIdByUserId(Integer userId);
}
