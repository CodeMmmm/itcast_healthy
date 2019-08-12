package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;


import java.util.List;
import java.util.Map;

public interface MenuService {
     List<Menu> findmenuByUsername(String username);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Menu> findMenuAll();

    void addMenu(Map menu, Integer[] menuIds);

    Menu findonemenu(Integer menuid);

    List<Map> findAll();

    Integer[] menuAndMenuByMenuId(Integer menuid);

    void edit(Integer[] menuIds, Menu menu);

    void delete(Integer menuId);
}
