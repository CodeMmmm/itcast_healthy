package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入预约
     * @param excelFile
     * @return
     */
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile excelFile) throws IOException {
        try {
            // 将得到的List<String[]>转为List<Ordersetting>
            List<String[]> strings = POIUtils.readExcel(excelFile);
            System.out.println("================================");
            System.out.println("================================");
            System.out.println("================================");
            System.out.println("================================");
            System.out.println("================================");
            System.out.println(strings);
            if(strings != null && strings.size() > 0){
                List<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] strArr : strings) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strArr[0]), Integer.parseInt(strArr[1]));
                    orderSettings.add(orderSetting);
                }
                orderSettingService.upload(orderSettings);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 获取预约设置数据
     * @param date
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getOrderSettingByMonth",method = RequestMethod.GET)
    public Result getOrderSettingByMonth(String date) throws IOException {
        try {
            String preDate = date + "-1";
            String sufDate = date + "-31";
            List<Map<String,Object>> mapList = orderSettingService.getOrderSettingByMonth(preDate,sufDate);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,mapList);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/editNumberByDate",method = RequestMethod.POST)
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) throws IOException {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
