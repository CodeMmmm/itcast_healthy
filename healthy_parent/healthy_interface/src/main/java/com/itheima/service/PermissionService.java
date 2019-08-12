package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

public interface PermissionService
{

 /***
  * 分页查询
  * @param queryPageBean
  * @return
  */
 PageResult checkPage(QueryPageBean queryPageBean);

 /**
  * 增加权限
  * @param permission
  */
 void addPermission(Permission permission);

 Permission findoneByid(Integer id);

 /**
  * 更新
  * @param permission
  */
 void editPermission(Permission permission);

 void deletePermission(Integer id);
}
