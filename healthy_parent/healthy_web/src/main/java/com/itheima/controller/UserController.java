package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Reference
    private UserService userService;
    @RequestMapping(value = "/getUsername",method = RequestMethod.GET)
    public Result getUsername(){
        try {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,principal.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        Result result = new Result();
        try {
            PageResult pageResult = userService.findUserAll(queryPageBean);
            result.setFlag(true);
            result.setData(pageResult);
            result.setMessage(MessageConstant.QUERY_USER_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_USER_FAIL);
        }
        return result;
    }
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody com.itheima.pojo.User user, Integer[] rolesIds ){
        Result result = new Result();
        try {
            //先将Use存入,返回id
            String password = encoder.encode(user.getPassword());
            //加密
            user.setPassword(password);
            userService.addUser(user,rolesIds);
            result.setFlag(true);

            result.setMessage(MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_USER_FAIL);
        }
        return result;
    }
    @RequestMapping("/findoneUser")
    public Result findoneUser(Integer userId){
        Result result = new Result();
        try {
            com.itheima.pojo.User user = userService.findoneUser(userId);
            result.setFlag(true);
            result.setData(user);
            result.setMessage(MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_USER_FAIL);
        }
        return result;
    }
    @RequestMapping("/userAndRoleByUserId")
    public Result userAndRoleByUserId(Integer userId){
        Result result = new Result();
        try {
            List<Integer> list= userService.userAndRoleByUserId(userId);
            result.setFlag(true);
            result.setData(list);
            result.setMessage(MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_USER_FAIL);
        }
        return result;
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody com.itheima.pojo.User user, Integer[] rolesIds){
        Result result = new Result();
        try {
            userService.edit(user,rolesIds);
            result.setFlag(true);

            result.setMessage(MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_USER_FAIL);
        }
        return result;
    }
    @RequestMapping("/findUserName")
    public Result findUserName(String username ){
        Result result = new Result();
        try {
            userService.findUserName(username);
            result.setFlag(true);

            result.setMessage("用户名可以用");
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/delete")
    public Result delete(Integer userId){
        Result result = new Result();
        try {
            userService.delete(userId);
            result.setFlag(true);

            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage("删除失败");
        }
        return result;
    }

}
