package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass =PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public PageResult checkPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<Permission> permissions = permissionDao.checkPage(queryString);
        return new PageResult(permissions.getTotal(),permissions.getResult());
    }

    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    /**
     * 回显数据
     * @param id
     * @return
     */
    @Override
    public Permission findoneByid(Integer id) {

        return permissionDao.findoneByid(id);
    }

    @Override
    public void editPermission(Permission permission) {
        permissionDao.editPermission(permission);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionDao.deletePermission(id);
    }
}
