package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.Permission;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")

    public Result checkItem(@RequestBody QueryPageBean queryPageBean){

        Result result = new Result();
        try {
            PageResult pageResult= permissionService.checkPage(queryPageBean);
            result.setFlag(true);
            result.setData(pageResult);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
        }

        return result;
    }

    /**
     *
     * @param
     * @return
     */
    @RequestMapping("/add")

    public Result addPermission(@RequestBody Permission permission){

        Result result = new Result();
        try {
             permissionService.addPermission(permission);
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return result;
    }
    @RequestMapping("/findoneByid")

    public Result findoneByid(Integer id){

        Result result = new Result();
        try {
         Permission permission  =   permissionService.findoneByid(id);
            result.setFlag(true);
            result.setData(permission);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return result;
    }
    /**
     *
     * @param
     * @return
     */
    @RequestMapping("/edit")

    public Result editPermission(@RequestBody Permission permission){

        Result result = new Result();
        try {
            permissionService.editPermission(permission);
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return result;
    }
    /**
     *
     * @param
     * @return
     */
    @RequestMapping("/delete")

    public Result deletePermission(Integer id){

        Result result = new Result();
        try {
            permissionService.deletePermission(id);
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return result;
    }


}
