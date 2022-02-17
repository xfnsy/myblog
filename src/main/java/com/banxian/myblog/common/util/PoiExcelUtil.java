package com.banxian.myblog.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * poi操作工具类
 * excel 行与列开始都时从0开始
 *
 * @author wangpeng
 * @datetime 2020-12-23
 */
@SuppressWarnings("all")
public class PoiExcelUtil {


    private static Logger logger = LoggerFactory.getLogger(PoiExcelUtil.class);

    /**
     * Excel 2007年以后文件后缀名
     */
    private static final String EXCEL_07_AFTER_SUFF_NAME = ".xlsx";
    /**
     * Excel 2007年以前文件后缀名
     */
    private static final String EXCEL_07_BEFORE_SUFF_NAME = ".xls";

    /**
     * 默认日期时间格式
     */
    private static final String DEFAULT_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期解析工具
     */
    private static SimpleDateFormat defaultDateTimeFormat = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_PATTERN);

    /**
     * 默认列尺寸
     */
    private static final Integer DEFAULT_COLUMN_SIZE = 10;


    /**
     * 创建默认表,并设置表头
     */
    private static Sheet createDefaultSheet(Workbook workbook, String sheetName, String[] headNames, Integer[] columnSize) {
        // 创建表
        Sheet sheet = workbook.createSheet(sheetName);
        // // 设置列头和列宽（第一行）
        Row rowheard = sheet.createRow(0);
        for (int i = 0; i < headNames.length; i++) {
            // 设置列宽
            if (columnSize == null) {
                sheet.setColumnWidth(i, DEFAULT_COLUMN_SIZE * 512);
            } else {
                sheet.setColumnWidth(i, columnSize[i] * 512);
            }
            Cell cell = rowheard.createCell(i);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(cellStyle);
            // 列头赋值
            cell.setCellValue(headNames[i]);
        }
        return sheet;
    }

    /**
     * 填充数据
     *
     * @param sheet     表
     * @param attrNames 参数名
     * @param datas     数据集合
     * @param rowLimit  行数限制
     * @param <E>       类
     */
    private static <E> void fillingDate(Sheet sheet, String[] attrNames, List<E> datas, Integer rowLimit) throws Exception {
        int rownum = 1;

        for (E e : datas) {
            Class clasz = e.getClass();
            // 判断是否是map数据结构
            boolean isMap = Map.class.isAssignableFrom(clasz);
            Row row = sheet.createRow(rownum++);
            for (int i = 0; i < attrNames.length; i++) {
                Cell cell = row.createCell(i);
                Object data;
                // Map的数据直接取值
                if (isMap) {
                    Map<String, Object> map = (Map<String, Object>) e;
                    data = map.get(attrNames[i]);
                } else {
                    // 实体类通过get方法获取
                    // 首字母大写
                    char[] cs = attrNames[i].toCharArray();
                    cs[0] -= 32;
                    String methodName = "get" + String.valueOf(cs);
                    data = clasz.getMethod(methodName).invoke(e);
                }
                if (data != null) {
                    String simpleName = data.getClass().getSimpleName();
                    switch (simpleName) {
                        case "Double":
                            cell.setCellValue((Double) data);
                            break;
                        case "Integer":
                            cell.setCellValue((Integer) data);
                            break;
                        case "Timestamp":
                        case "Date":
                            String dateStr = defaultDateTimeFormat.format((Date) data);
                            if (dateStr.endsWith("00:00:00")) {
                                dateStr = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT_PATTERN)).toString();
                            }
                            cell.setCellValue(dateStr);
                            break;
                        default:
                            cell.setCellValue(data.toString());
                    }

                }
            }
            // 达到限制条数，跳出循环
            if (rowLimit != null && rownum >= rowLimit) {
                break;
            }
        }

    }

    /**
     * 下载导出
     *
     * @param workbook     工作簿
     * @param downFileName 文件名
     * @param request      请求
     * @param response     响应
     */
    private static void downWrite(Workbook workbook, String downFileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 浏览器文件名编码处理
        downFileName = downFileName + EXCEL_07_AFTER_SUFF_NAME;
        if (request.getHeader("USER-AGENT").contains("like Gecko")) {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");//IE浏览器
        } else {
            downFileName = new String(downFileName.getBytes(), "iso-8859-1");// 火狐和谷歌
        }
        response.setHeader("content-Type", request.getServletContext().getMimeType(downFileName));
        response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.close();
    }

    /**
     * 根据excel上传文件获取对应workbook
     *
     * @param upFile 上传文件
     * @return workbook
     */
    public static Workbook getWorkbook(MultipartFile upFile) throws Exception {
        String originalFilename = upFile.getOriginalFilename();
        String suffName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (EXCEL_07_AFTER_SUFF_NAME.equals(suffName)) {
            return new XSSFWorkbook(upFile.getInputStream());
        } else if (EXCEL_07_BEFORE_SUFF_NAME.equals(suffName)) {
            return new HSSFWorkbook(upFile.getInputStream());
        }
        return null;
    }

    /**
     * excel表格读取时，空行判断
     *
     * @param row    行
     * @param colNum 列书
     * @return 空否
     */
    public static Boolean isBlankRow(Row row, int colNum) {
        //  单元格格式化
        DataFormatter dataFormatter = new DataFormatter();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < colNum; i++) {
            s.append(dataFormatter.formatCellValue(row.getCell(i)).trim());
        }
        return s.toString().trim().length() == 0;
    }


    /**
     * 多组数据导出基本,目前只能导出同列数据,以后有需要在头名称和列尺寸上使用map结构
     *
     * @param sheetNames   多个sheet的列数组
     * @param headNames    头名称数组名称
     * @param columnSize   列尺寸大小数组名称
     * @param attrNames    参数数组
     * @param datasMap     多组数据map
     * @param downFileName 想在文件名称
     * @param rowLimit     列限制
     * @param request      请求
     * @param response     响应
     * @param <E>          对象类型
     */
    public static <E> void exportExcelList(String[] sheetNames, String[] headNames, Integer[] columnSize, String[] attrNames, Map<String, List<E>> datasMap, String downFileName, Integer rowLimit, HttpServletRequest request, HttpServletResponse response) {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);//缓存
        workbook.setCompressTempFiles(true);// 可压缩
        try {
            for (String sheetName : sheetNames) {
                // 创建默认表,并设置表头
                Sheet sheet = createDefaultSheet(workbook, sheetName, headNames, columnSize);
                // 填充数据
                fillingDate(sheet, attrNames, datasMap.get(sheetName), rowLimit);
            }
            // 下载导出
            downWrite(workbook, downFileName, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("excel导出失败:---", e);
        }
    }

    public static <E> void exportExcelList(String[] sheetNames, String[] headNames, String[] attrNames, Map<String, List<E>> datasMap, String downFileName, HttpServletRequest request, HttpServletResponse response) {
        exportExcelList(sheetNames, headNames, null, attrNames, datasMap, downFileName, null, request, response);
    }

    /**
     * 一组数据导出
     */
    public static <E> void exportExcel(String sheetName, String[] headNames, Integer[] columnSize, String[] attrNames, List<E> datas, String downFileName, Integer rowLimit, HttpServletRequest request, HttpServletResponse response) {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);//缓存
        workbook.setCompressTempFiles(true);// 可压缩
        try {
            // 创建默认表,并设置表头
            Sheet sheet = createDefaultSheet(workbook, sheetName, headNames, columnSize);
            // 填充数据
            fillingDate(sheet, attrNames, datas, rowLimit);
            // 下载导出
            downWrite(workbook, downFileName, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("excel导出失败:---", e);
        }
    }

    public static <E> void exportExcel(String sheetName, String[] headNames, String[] attrNames, List<E> datas, String downFileName, HttpServletRequest request, HttpServletResponse response) {
        exportExcel(sheetName, headNames, null, attrNames, datas, downFileName, null, request, response);
    }


    /**
     * 插入空白行,表现为插入的行不能获取到,需要取手动创建row和cell
     *
     * @param sheet       表
     * @param startRowNum 要插入的行
     * @param rowNum      要插入的行数
     */
    public static void insertBlankRows(Sheet sheet, int startRowNum, int rowNum) {
        // 这里加一表示当前行后面
        sheet.shiftRows(startRowNum + 1, sheet.getLastRowNum(), rowNum);
    }

    /**
     * 插入真实行并赋予格式
     *
     * @param sheet       表
     * @param startRowNum 要插入的行
     * @param rowNum      要插入的行数
     * @param styRowNum   样式行,默认为插入那一行
     */
    public static void insertRows(Sheet sheet, int startRowNum, int rowNum, Integer styRowNum) {
        insertBlankRows(sheet, startRowNum, rowNum);
        Row styRow = sheet.getRow(styRowNum == null ? startRowNum : styRowNum);
        short cellNum = styRow.getLastCellNum();
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.createRow(++startRowNum);
            row.setRowStyle(styRow.getRowStyle());
            row.setHeight(styRow.getHeight());
            for (int j = 0; j < cellNum; j++) {
                // 单元格格式全部初始化为String
                row.createCell(j, CellType.STRING).setCellStyle(styRow.getCell(j).getCellStyle());
            }
        }
    }

    /**
     * 插入真实行并赋予格式
     *
     * @param sheet       表
     * @param startRowNum 要插入的行
     * @param rowNum      要插入的行数
     */
    public static void insertRows(Sheet sheet, int startRowNum, int rowNum) {
        insertRows(sheet, startRowNum, rowNum, null);
    }

    /**
     * 合并单行
     */
    public static void mergerSingleRow(Sheet sheet, int rowNum, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(rowNum, rowNum, firstCol, lastCol);
        sheet.addMergedRegion(region);
    }

    /**
     * 合并单列
     */
    public static void mergerSingleCol(Sheet sheet, int colNum, int firstRow, int lastRow) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, colNum, colNum);
        sheet.addMergedRegion(region);
    }

    /**
     * 合并区域
     *
     * @param sheet    表格
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static void mergerRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(region);
    }

    /**
     * 删除合并区域
     *
     * @param sheet    表格
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static void removeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        Integer regionIndex = getRegionIndex(sheet, firstRow, lastRow, firstCol, lastCol);
        if (regionIndex != null) {
            sheet.removeMergedRegion(regionIndex);
        }
    }

    /**
     * 获取合并区域的index
     *
     * @param sheet    表格
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static Integer getRegionIndex(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (int i = 0; i < mergedRegions.size(); i++) {
            CellRangeAddress p = mergedRegions.get(i);
            if (p.getFirstColumn() == firstCol && p.getLastColumn() == lastCol
                    && p.getFirstRow() == firstRow && p.getLastRow() == lastRow) {
                return i;
            }
        }
        return null;
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static XSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, XSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new XSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        Row row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        CellStyle style = wb.createCellStyle();

        CellStyle cellstyle = wb.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);

        // 声明列对象
        Cell cell = null;

        // 创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                Cell createCell = row.createCell(j);
                createCell.setCellValue(values[i][j]);
                createCell.setCellStyle(cellstyle);
            }
        }
        return wb;
    }

    public static void main(String[] args) throws Exception {
        // 格式化cell值为string
        DataFormatter dataFormatter = new DataFormatter();
    }


}
