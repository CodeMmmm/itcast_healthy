package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.MemberService;
import com.itheima.service.OrderService;
import com.itheima.service.ReportService;
import com.itheima.service.SetMealService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setMealService;

    @Reference
    private OrderService orderService;

    @Reference
    private ReportService reportService;

    @RequestMapping(value = "/getMemberReport",method = RequestMethod.GET)
    public Result getMemberReport(){
        try {
            // 先将月份放入
            Calendar calendar = Calendar.getInstance();
            // 将当前日历变为12个月前
            calendar.add(Calendar.MONTH,-12);
            List<String> monthList = new ArrayList<>();
            for (int i=1; i<=12; i++){
                calendar.add(Calendar.MONTH,1);
                monthList.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("months",monthList);
            // 再将每个月的会员数放入
            List<Integer> monthCount = memberService.findMemberCountByMonth(monthList);
            map.put("memberCount",monthCount);
            System.out.println(map);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/getSetmealReport",method = RequestMethod.GET)
    public Result getSetmealReport(){
        try {
            Map<String,Object> resMap = new HashMap<>();
            // 直接找出Map<Stringm,Object>
            List<Map> mapList = setMealService.findSetmealCount();
            List<String> setmealNames = new ArrayList<>();
            if(mapList != null && mapList.size()>0){
                for (Map map : mapList) {
                    String name = (String) map.get("name");
                    setmealNames.add(name);
                }
            }
            resMap.put("setmealNames",setmealNames);
            resMap.put("setmealCount",mapList);
            /*Map<String,Object> map = new HashMap<>();
            // 先查找出所有的套餐名称
            List<Setmeal> setmealList = setMealService.getSetmeal();
            List<String> setmealNameList = new ArrayList<>();
            for (Setmeal setmeal : setmealList) {
                setmealNameList.add(setmeal.getName());
            }
            map.put("setmealNames",setmealNameList);
            // 再查找出所有的套餐名对应的预约数
            List<Map> setmealNameAndCountList = new ArrayList<>();
            for (Setmeal setmeal : setmealList) {
                Map<String,Object> setmealMap = new HashMap<>();
                // 放入name
                setmealMap.put("name",setmeal.getName());
                Integer count = orderService.findCountByStemealId(setmeal.getId());
                setmealMap.put("value",count);
                setmealNameAndCountList.add(setmealMap);
            }
            map.put("setmealCount",setmealNameAndCountList);*/
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/getBusinessReportData",method = RequestMethod.GET)
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            // 获取到运营数据的值
            Map<String,Object> map = reportService.getBusinessReportData();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) map.get("reportDate");
            Integer todayNewMember = (Integer) map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");

            // 获取到模板路径
            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            // 获取报表
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));

            XLSTransformer xlsTransformer = new XLSTransformer();
            xlsTransformer.transformWorkbook(xssfWorkbook,map);
            /*// 获取sheet
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            // 填充日期
            XSSFRow row = sheetAt.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheetAt.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheetAt.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheetAt.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheetAt.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheetAt.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            //热门套餐
            int rowNum = 12;
            for (Map map1 : hotSetmeal) {
                sheetAt.getRow(rowNum).getCell(4).setCellValue((String) map1.get("name"));
                sheetAt.getRow(rowNum).getCell(5).setCellValue((String) map1.get("setmeal_count").toString());
                sheetAt.getRow(rowNum).getCell(6).setCellValue((String) map1.get("proportion").toString());
                sheetAt.getRow(rowNum).getCell(7).setCellValue((String) map1.get("remark"));
                rowNum++;
            }*/

            // 输出报表
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            xssfWorkbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
