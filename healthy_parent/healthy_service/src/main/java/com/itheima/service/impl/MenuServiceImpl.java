package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import com.itheima.pojo.User;

import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<Menu> findmenuByUsername(String username) {
     User user =  menuDao.findUserByUsername(username);
        Integer userId = user.getId();
        //获得该用户所有的父菜单
     List<Menu> list= menuDao.findmenuByUser(userId);
     //将菜单放入其父菜单中
     for (Menu menu : list) {
         List<Menu>  children =  menuDao.findmenuByMenuId(menu.getId(),userId);
         menu.setChildren(children);
        }
        return list;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<Map> page= menuDao.findMenuPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Menu> findMenuAll() {
        List<Menu> list=menuDao.findMenuAll();
        return list;
    }

    @Override
    public void addMenu(Map menu, Integer[] menuIds) {
        Integer level = (Integer) menu.get("level");
        if (level==2){
            //为子菜单
            if (menuIds.length>0){
                //子菜单下面没有子菜单让客户重新选择菜单等级
                throw new RuntimeException("新建菜单为子菜单,不能多选子菜单");
            }
            menuDao.addMenu(menu);
            return;
        }
        menuDao.addMenu(menu);
       Integer path =  menuDao.findMaxmenu()+1;
        menu.put("path",path);
        menu.put("priority",path);
        menuDao.addParentMenu(menu);
        int i=1;
        for (Integer menuId : menuIds) {
            String path2="/"+path+"-"+i;
            menuDao.updateMenu(path2,menuId,  menu.get("id"));
            i++;
        }
    }

    /**
     * 查询一条菜单
     * @param menuid
     * @return
     */
    @Override
    public Menu findonemenu(Integer menuid) {
        return menuDao.findonemenu(menuid);
    }

    @Override
    public List<Map> findAll() {
        return menuDao.findAll();
    }

    @Override
    public Integer[] menuAndMenuByMenuId(Integer menuid) {

        return menuDao.menuAndMenuByMenuId(menuid);
    }

    @Override
    public void edit(Integer[] menuIds, Menu menu) {
        Integer id = menu.getId();
        menuDao.updateMenuAll(menu);
        if (menu.getParentMenuId()==null&&menuIds.length>0) {

            //先将数据库中关联的全删
            menuDao.updatePath(id);
            int i=1;
            String path =menu.getPath();
            for (Integer menuId : menuIds) {
                String path2="/"+path+"-"+i;
                menuDao.updateMenu(path2,menuId,id);
                i++;
            }
        }
    }

    @Override
    public void delete(Integer menuId) {
       //先查询是否为一级菜单
        Menu findonemenu = menuDao.findonemenu(menuId);
        if (findonemenu.getParentMenuId()==null) {
            //清空关联记录
            menuDao.updatePath(menuId);
        }
        menuDao.delete(menuId);
    }
}
