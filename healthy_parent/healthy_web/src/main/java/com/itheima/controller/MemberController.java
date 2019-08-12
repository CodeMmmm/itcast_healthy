package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;


    /**
     * 添加会员
     * @param member
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody Member member){
        try {
            memberService.add(member);
            return new Result(true,MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
        }
    }
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage",method = RequestMethod.POST)
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult= memberService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_MEMBER_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MEMBER_FAIL);
        }
    }

    /**
     * 根据id查询会员
     * @param id
     * @return
     */
    @RequestMapping(value = "/findItemById",method = RequestMethod.GET)
    public Result findItemById(Integer id){
        try {
            Member member = memberService.findItemById(id);
            return new Result(true,MessageConstant.QUERY_MEMBER_SUCCESS,member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MEMBER_FAIL);
        }
    }

    /**
     * 更新会员信息
     * @param member
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Member member){
        try {
            memberService.update(member);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result deleteItem(Integer id){
        try {
            memberService.deleteMember(id);
            return new Result(true,MessageConstant.DELETE_MEMBER_SUCCESS);
        }catch (RuntimeException r){
            r.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
    }
}
