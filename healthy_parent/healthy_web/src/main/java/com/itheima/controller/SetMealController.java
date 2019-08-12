package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtil;
import org.apache.commons.io.FilenameUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setMeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传
     * @param imgFile
     * @return
     */
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile imgFile){
        try {
            // 获取文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 获取文件后缀名
            String suffix = FilenameUtils.getExtension(originalFilename);
            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString()+"."+suffix;
            // 文件上传
            QiniuUtil.upload2Qiniu(imgFile.getBytes(),fileName);
            // 上传成功的文件名存入redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 新增套餐
     * @param checkgroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestParam List<Integer> checkgroupIds, @RequestBody Setmeal setmeal){
        try {
            setMealService.add(checkgroupIds,setmeal);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 查询分页
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage",method = RequestMethod.POST)
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = setMealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 查询所有套餐
     * @return
     */
    @RequestMapping(value = "/findAllSetmeal",method = RequestMethod.GET)
    public Result findAllSetmeal(){
        try {
            List<Setmeal> list = setMealService.findAllSetmeal();
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }
}
