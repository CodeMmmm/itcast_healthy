package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;
    @RequestMapping("/findPage")
    public Result findCheckGroupAll(@RequestBody QueryPageBean queryPageBean){
        Result result = new Result();
        try {
            PageResult pageResult = roleService.findRoleAll(queryPageBean);
            result.setFlag(true);
            result.setData(pageResult);
            result.setMessage(MessageConstant.QUERY_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/findPermissionAll")
    public Result findPermissionAll(){
        Result result = new Result();
        try {
            List<Permission> pageResult = roleService.findPermissionAll();
            result.setFlag(true);
            result.setData(pageResult);
            result.setMessage(MessageConstant.QUERY_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/addRole")
    public Result addRole(Integer[] menuIds,Integer[] permissionsIds,@RequestBody Role role){
        Result result = new Result();
        try {
            roleService.addRole(menuIds,permissionsIds,role);
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/findoneRole")
    public Result findoneRole(Integer roleId){
        Result result = new Result();
        try {
           Role role=  roleService.findoneRole(roleId);
            result.setFlag(true);
            result.setData(role);
            result.setMessage(MessageConstant.QUERY_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/roleAndPermissionByRoleId")
    public Result roleAndPermissionByRoleId(Integer roleId){
        Result result = new Result();
        try {
           Map map=  roleService.roleAndPermissionByRoleId(roleId);
            result.setFlag(true);
            result.setData(map);
            result.setMessage(MessageConstant.QUERY_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/edit")
    public Result edit(Integer[] menuIds,Integer[] permissionsIds,@RequestBody Role role){
        Result result = new Result();
        try {
            roleService.edit(menuIds,permissionsIds,role);
            result.setFlag(true);

            result.setMessage(MessageConstant.UPDATE_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.UPDATE_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/delete")
    public Result delete(Integer roleId){
        Result result = new Result();
        try {
           roleService.delete(roleId);
            result.setFlag(true);
            result.setMessage(MessageConstant.QUERY_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ROLE_FAIL);
        }
        return result;
    }
    @RequestMapping("/findRoleAll")
    public Result findRoleAll(){
        Result result = new Result();
        try {
          List<Role> list = roleService.findRolesAll();
            result.setFlag(true);
            result.setData(list);
            result.setMessage(MessageConstant.QUERY_ROLE_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ROLE_FAIL);
        }
        return result;
    }

}
