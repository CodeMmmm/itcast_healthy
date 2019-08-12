package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;
    @Override
    public PageResult findRoleAll(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        PageHelper.startPage(currentPage,pageSize);
        Page<Role> page= roleDao.findRole(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Permission> findPermissionAll() {
        return permissionDao.findPermissionAll();
    }

    @Override
    public void addRole(Integer[] menuIds, Integer[] permissions, Role role) {
        roleDao.addRole(role);
        Integer id = role.getId();
        insertPaermissions(permissions, id);

        addRoleAndMenu(menuIds, id);
    }

    private void addRoleAndMenu(Integer[] menuIds, Integer id) {
        Set<Integer> set = new HashSet<>();
        if (menuIds!=null&&menuIds.length>0) {
            for (Integer menuId : menuIds) {
                roleDao.addRoleAndMenu(menuId,id);
                Integer ParentMenuId =menuDao.findParentMenuIdbByMenuId(menuId);
                if (ParentMenuId!=null) {
                    set.add(ParentMenuId);
                }
            }
        }
        System.out.println(set);
        for (Integer parentMenuId : set) {
            roleDao.addRoleAndMenu(parentMenuId,id);
        }
    }

    @Override
    public Role findoneRole(Integer roleId) {

        return roleDao.findoneRole(roleId);
    }

    @Override
    public Map roleAndPermissionByRoleId(Integer roleId) {
        //根据角色id查询权限
         List<Integer> PermissionsIds = roleDao.roleAndPermissionByRoleId(roleId);
        //根据角色id查询菜单
        List<Integer> menuIds= roleDao.roleAndMenuByRoleId(roleId);
        Map map =new HashMap();
        map.put("permissionsIds",PermissionsIds);
        map.put("menuIds",menuIds);
        return map;
    }

    @Override
    public void edit(Integer[] menuIds, Integer[] permissionsIds, Role role) {
        //更新角色数据
        roleDao.updateByRole(role);
        Integer id = role.getId();
        //删除中间表
        roleDao.deleteroleAndPermissionByRoleId(id);
        roleDao.deleteroleAndMenuByRoleId(id);
        insertPaermissions(permissionsIds,id);
           addRoleAndMenu(menuIds,id);

    }

    @Override
    public void delete(Integer roleId) {
        //先删除中间表数据
        roleDao.deleteroleAndPermissionByRoleId(roleId);
        roleDao.deleteroleAndMenuByRoleId(roleId);
        //在删除角色
        roleDao.delete(roleId);
    }

    @Override
    public List<Role> findRolesAll() {

        return roleDao.findRolesAll();
    }

    private void insertPaermissions(Integer[] permissions, Integer id) {
        if (permissions!=null&&permissions.length>0) {
            for (Integer permission : permissions) {
                roleDao.addRoleAndpermission(id,permission);
            }
        }
    }
}
