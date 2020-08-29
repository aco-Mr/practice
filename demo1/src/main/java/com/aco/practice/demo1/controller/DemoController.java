package com.aco.practice.demo1.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.aco.practice.basic.util.ApiResponseResult;
import com.aco.practice.basic.util.ExcelStyleUtil;
import com.aco.practice.basic.util.RedisKeyUtil;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.response.vo.EasypoiVo;
import com.aco.practice.demo1.domain.response.vo.ExportTestVo;
import com.aco.practice.demo1.handle.UserContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: HaoJianXu
 * @Date: 2020/5/31 14:03
 */
@RestController
@RequestMapping(value = "/demo")
@Api(tags = "测试接口控制层")
@Slf4j
public class DemoController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "demo 测试接口", notes = "demo 测试接口")
    @GetMapping(value = "demo")
    public void test(){
        UserEntity userInfo = (UserEntity) UserContextHolder.getInstance().getContext().get("user");
        log.info("用户信息：{}",userInfo);
    }

    @ApiOperation(value = "测试redis接口", notes = "测试redis接口")
    @GetMapping(value = "redis-test")
    public ApiResponseResult setAndGetRedis(String str){
        String redisKey = RedisKeyUtil.getRedisKey(str);
        redisTemplate.opsForValue().set(redisKey,str,30, TimeUnit.SECONDS);
        log.info("测试日志：{}",redisKey);
        return ApiResponseResult.ok(redisTemplate.opsForValue().get(redisKey));
    }

    @ApiOperation(value = "测试导出Excel接口")
    @GetMapping(value = "export")
    public void exportExcel(String tittle, HttpServletResponse response){
        //初始化数据
        //列头
        List<String> columns = Arrays.asList("1", "2", "3");
        List<EasypoiVo> easypoiVos = new ArrayList<>();
        // 数据
        List<ExportTestVo> dataList = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++){
            ExportTestVo exportTestVo = new ExportTestVo();
            EasypoiVo easypoiVo = new EasypoiVo();
            //列头
            easypoiVo.setColumn("测试Column" + columns.get(i));
            //列头key
            easypoiVo.setKey(columns.get(i));
            exportTestVo.setName("测试" + (i + 1));
            exportTestVo.setList(columns);
            dataList.add(exportTestVo);
            easypoiVos.add(easypoiVo);
        }

        //动态表头
        List<ExcelExportEntity> colList = new ArrayList<>();
        ExcelExportEntity exportGroup;
        exportGroup = new ExcelExportEntity("日期姓名","tittle");
        exportGroup.setHeight(20);
        exportGroup.setWidth(30);
        colList.add(exportGroup);
        for (int i = 0; i < easypoiVos.size(); i++){
            //创建表头
            EasypoiVo easypoiVo = easypoiVos.get(i);
            exportGroup = new ExcelExportEntity(easypoiVo.getColumn(),easypoiVo.getKey());
            exportGroup.setHeight(20);
            exportGroup.setWidth(30);
            colList.add(exportGroup);
        }
        //组装数据
        List<Map<String,Object>> list = new ArrayList<>();
        for (ExportTestVo exportTestVo : dataList) {
            Map<String,Object> map = new HashMap<>();
            //获取key集合
            Set<Object> collect = colList.stream().map(ExcelExportEntity::getKey).collect(Collectors.toSet());
            for (Object obj : collect) {
                String key = String.valueOf(obj);
                for (String str : exportTestVo.getList()) {
                    if (str.equals(key)){
                        map.put(key,exportTestVo.getName() + "column" + str);
                        map.put("tittle","测试列头." + str);
                        list.add(map);
                        break;
                    }
                }
            }
        }

        ServletOutputStream os = null;
        try {
            String sheetName = "sheetName";
            ExportParams exportParams = new ExportParams(tittle, sheetName);
            //设置样式
            exportParams.setStyle(ExcelStyleUtil.class);
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams,colList,list);
            // 添加下划线
            HSSFSheet sheet = (HSSFSheet) workbook.getSheet(sheetName);
            HSSFRow row = sheet.getRow(1);
            HSSFCell cell = row.getCell(0);
            cell.setCellValue("日期            姓名");
            //画线(由左上到右下的斜线)  在A1的第一个cell（单位  分类）加入一条对角线
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short)0, 1, (short)0, 1);
            HSSFSimpleShape shape1 = patriarch.createSimpleShape(a);
            shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
            shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID) ;

            //导出
            os = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename="+new String("test1".getBytes(), StandardCharsets.ISO_8859_1)+".xls");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("导出失败");
        } finally {
            try {
                assert os != null;
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
