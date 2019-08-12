package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {
    User findUserByUsername(String username);

    /**
     * 根据用户id查询该用户的所有父菜单
     * @param
     * @return
     */
    List<Menu> findmenuByUser(Integer userId);
    /**
     * 根据父菜单id查询该用户的所有子菜单
     * @param menuid,userId
     * @return
     */
    List<Menu> findmenuByMenuId(@Param("menuId") Integer menuid, @Param("userId") Integer userId);
    /**
     * 条件查询菜单
     * @param
     * @return
     */
    Page<Map> findMenuPage(String queryString);
    /**
     * 条件所有子菜单
     * @param
     * @return
     */
    List<Menu> findMenuAll();
    /**
     * 创建子菜单
     * @param
     * @return
     */
    void addMenu(Map menu);

    /**
     * 查询父菜单最大数
     * @param
     * @return
     */
    Integer findMaxmenu();
    /**
     * 创建父菜单
     * @param
     * @return
     */
    void addParentMenu(Map menu);
    /**
     * 更新父菜单子菜单
     * @param
     * @return
     */
    void updateMenu(@Param("path") String path2, @Param("id") Integer menuId, @Param("parentMenuId") Object id);
    /**
     * 获取一条菜单
     * @param
     * @return
     */
    Menu findonemenu(Integer menuid);
    /**
     * 所有子菜单
     * @param
     * @return
     */
    List<Map> findAll();
    /**
     * 查询菜单关联的id
     * @param
     * @return
     */

    Integer[] menuAndMenuByMenuId(Integer menuid);

    void updateMenuAll(Menu menu);

    void updatePath(Integer id);

    void delete(Integer menuId);

    Integer findParentMenuIdbByMenuId(Integer menuId);
}
