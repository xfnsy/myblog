package com.banxian.myblog.common.util;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtil {

    public static void main(String[] args) throws Exception {

        File file = new File("D:\\WorkPlace\\txdemo\\src\\main\\resources\\static\\shenpi.xlsx");
        Workbook wb = new XSSFWorkbook(new FileInputStream(file));
        // 两个sheet 1,审批表 2,查档对象附表
        Sheet sheet = wb.getSheetAt(0);
        int size = 20;
        // 默认查档对象开始行和结束行,一共8行
        int defaultStartRow = 4;
        int defaultEndRow = 11;
        int defaultRows = 8;
        // 当前行数和结束行数
        int nowRow = 4;
        int nowEndRow = defaultStartRow + size - 1;
        // 格式处理,行数大于默认行数8的时候,插入需要的行数
        if (size > defaultRows) {
            // 清除后面需要重新合并的区域
            PoiExcelUtil.removeRegion(sheet,defaultStartRow,defaultEndRow,0,0);
            // 插入需要的行数并复制格式
            PoiExcelUtil.insertRows(sheet,defaultEndRow,size-defaultRows);
            // 合并相关区域
            for (int i = defaultEndRow + 1; i <= nowEndRow; i++) {
                // 根据格式要求合并多行行单元格
                PoiExcelUtil.mergerSingleRow(sheet,i,2,6);
                PoiExcelUtil.mergerSingleRow(sheet,i,7,9);
            }
            PoiExcelUtil.mergerSingleCol(sheet,0,defaultStartRow,nowEndRow);
        }

        // 内容插入
        // 查档对象
        for (; nowRow <= nowEndRow; nowRow++) {
            Row row = sheet.getRow(nowRow);
            row.getCell(1).setCellValue("王大");
            row.getCell(2).setCellValue("陕西组织部 部长");
            row.getCell(7).setCellValue("中共党员");
        }
        //查档人员
        Row row13 = sheet.getRow(defaultRows >= size ? 12 : nowRow);
        row13.getCell(1).setCellValue("王二");
        row13.getCell(2).setCellValue("陕西常委会 会长");
        row13.getCell(7).setCellValue("中共党员");
        // 查找事由
        Row row15 = sheet.getRow(defaultRows >= size ? 14 : (nowRow += 2));
        row15.getCell(1).setCellValue("查找事由");
        // 查找内容
        Row row16 = sheet.getRow(defaultRows >= size ? 15 : ++nowRow);
        row16.getCell(1).setCellValue("查找内容");

        // 第二张sheet,从第三行开始设置查档对象内容
        // 序号	姓名	工作单位及职务	备注
        Sheet sheet2 = wb.getSheetAt(1);
        System.out.println(sheet2.getSheetName());
        for (int i = 3, y = 1; size > 0; i++, y++, size--) {
            Row row = sheet2.getRow(i);
            // 序号
            row.getCell(0).setCellValue(y);
            // 姓名
            row.getCell(1).setCellValue("姓名");
            // 工作单位及职务
            row.getCell(2).setCellValue("工作单位和职位");
            // 备注
            row.getCell(3).setCellValue("备注");
        }
        BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(new File("E:\\temp\\shenpi.xlsx")));
        wb.write(bof);
        bof.flush();
        bof.close();
    }


}