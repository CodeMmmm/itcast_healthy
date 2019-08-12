package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;

import com.itheima.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;
    @RequestMapping("/menuAll")
    public Result menuAll(){
        Result result = new Result();
        try {
            //通过安全容器获得登录对象
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal.getUsername();
            List<Menu> menuList = menuService.findmenuByUsername(username);
            Map<String,Object> map= new HashMap<>();
            map.put("username",username);
            map.put("menuList",menuList);
            result.setFlag(true);
            result.setData(map);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(MessageConstant.GET_MENU_FAIL);
        }
        return result;
    }
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        Result result = new Result();
        try {
            //通过安全容器获得登录对象
            PageResult page = menuService.findPage(queryPageBean);
            result.setFlag(true);
            result.setData(page);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(MessageConstant.GET_MENU_FAIL);
        }
        return result;
    }
    @RequestMapping("/findparentMenuAll")
    public Result findparentMenuAll(){
        Result result = new Result();
        try {

            List<Menu> page = menuService.findMenuAll();
            result.setFlag(true);
            result.setData(page);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(MessageConstant.GET_MENU_FAIL);
        }
        return result;
    }

    @RequestMapping("/addMenu")
    public Result addMenu(@RequestBody Map menu,Integer[] menuIds){
        Result result = new Result();

        try {
            //通过安全容器获得登录对象
           menuService.addMenu(menu,menuIds);
            result.setFlag(true);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/findonemenu")
    public Result findonemenu(Integer menuid){
        Result result = new Result();

        try {
          Menu menu =  menuService.findonemenu(menuid);
            result.setFlag(true);
            result.setData(menu);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        Result result = new Result();

        try {

         List<Map> menu=  menuService.findAll();
            result.setFlag(true);
            result.setData(menu);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/menuAndMenuByMenuId")
    public Result menuAndMenuByMenuId(Integer menuid){
        Result result = new Result();

        try {

        Integer[] menu=  menuService.menuAndMenuByMenuId(menuid);
            result.setFlag(true);
            result.setData(menu);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/edit")
    public Result edit(Integer[] menuIds,@RequestBody Menu menu){
        Result result = new Result();

        try {

      menuService.edit(menuIds,menu);
            result.setFlag(true);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/delete")
    public Result delete(Integer menuId){
        Result result = new Result();

        try {

      menuService.delete(menuId);
            result.setFlag(true);
            result.setMessage(MessageConstant.GET_MENU_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);

            result.setMessage(e.getMessage());
        }
        return result;
    }



}
