package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import com.alibaba.fastjson.JSON;

import com.csvreader.CsvReader;

import com.edu.exception.BusinessException;
import lombok.SneakyThrows;

import org.apache.commons.lang3.StringUtils;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.ss.usermodel.DateUtil;

import org.apache.poi.xssf.usermodel.*;

import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.lang.reflect.Method;

import java.net.URLEncoder;

import java.nio.charset.Charset;

import java.text.SimpleDateFormat;

import java.util.*;

public class ExcelUtils {

    /**

     * 使用浏览器选择路径下载

     * @param response

     * @param fileName

     * @param data

     * @throws Exception

     */

    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {

// 告诉浏览器用什么软件可以打开此文件

        response.setHeader("content-Type", "application/vnd.ms-excel");

// 下载文件的默认名称

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));

        exportExcel(data, response.getOutputStream());

    }

//浏览器下载，多页签sheet

    public static void exportExcelV2(HttpServletResponse response, String fileName, List<ExcelData> dataList) throws Exception {

// 告诉浏览器用什么软件可以打开此文件

        response.setHeader("content-Type", "application/vnd.ms-excel");

// 下载文件的默认名称

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));

        exportExcelV2(dataList, response.getOutputStream());

    }

    public static int generateExcel(ExcelData excelData, String path) throws Exception {

        File f = new File(path);

        FileOutputStream out = new FileOutputStream(f);

        return exportExcel(excelData, out);

    }

    private static int exportExcel(ExcelData data, OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();

        int rowIndex = 0;

        try {

            String sheetName = data.getName();

            if (null == sheetName) {

                sheetName = "Sheet1";

            }

            XSSFSheet sheet = wb.createSheet(sheetName);

            rowIndex = writeExcel(wb, sheet, data);

            wb.write(out);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

//此处需要关闭 wb 变量

            out.close();

        }

        return rowIndex;

    }

    private static int exportExcelV2(List<ExcelData> dataList, OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();

        int rowIndex = 0;

        try {

            for (int i = 0; i < dataList.size(); i++) {

                ExcelData data = dataList.get(i);

                String sheetName = data.getName();

                if (null == sheetName) {

                    sheetName = "Sheet" + i;

                }

                XSSFSheet sheet = wb.createSheet(sheetName);

                rowIndex = writeExcel(wb, sheet, data);

            }

            wb.write(out);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

//此处需要关闭 wb 变量

            out.close();

        }

        return rowIndex;

    }

/**

 * 表不显示字段

 * @param wb

 * @param sheet

 * @param data

 * @return

 */

// private static int writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {

// int rowIndex = 0;

// writeTitlesToExcel(wb, sheet, data.getTitles());

// rowIndex = writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);

// autoSizeColumns(sheet, data.getTitles().size() + 1);

// return rowIndex;

// }

    /**

     * 表显示字段

     * @param wb

     * @param sheet

     * @param data

     * @return

     */

    private static int writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {

        int rowIndex = 0;

        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());

        rowIndex = writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);

        autoSizeColumns(sheet, data.getTitles().size() + 1);

        return rowIndex;

    }

    /**

     * 设置表头

     *

     * @param wb

     * @param sheet

     * @param titles

     * @return

     */

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {

        int rowIndex = 0;

        int colIndex = 0;

        Font titleFont = wb.createFont();

//设置字体

        titleFont.setFontName("等线");

//设置粗体

//titleFont.setBoldweight(Short.MAX_VALUE);

//设置字号

        titleFont.setFontHeightInPoints((short) 11);

//设置颜色

//titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = wb.createCellStyle();

//水平居中

        titleStyle.setAlignment(HorizontalAlignment.CENTER);

//垂直居中

        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

//设置图案颜色

//titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));

//设置图案样式

//titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        titleStyle.setFont(titleFont);

//setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        Row titleRow = sheet.createRow(rowIndex);

        titleRow.setHeightInPoints(18);//行高

        colIndex = 0;

        for (String field : titles) {

            Cell cell = titleRow.createCell(colIndex);

            cell.setCellValue(field);

            cell.setCellStyle(titleStyle);

            colIndex++;

        }

        rowIndex++;

        return rowIndex;

    }

    /**

     * 设置内容

     *

     * @param wb

     * @param sheet

     * @param rows

     * @param rowIndex

     * @return

     */

    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {

        int colIndex;

        Font dataFont = wb.createFont();

        dataFont.setFontName("等线");

        dataFont.setFontHeightInPoints((short) 11);

//dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();

        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        dataStyle.setFont(dataFont);

//setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        for (List<Object> rowData : rows) {

            Row dataRow = sheet.createRow(rowIndex);

            dataRow.setHeightInPoints(25);

            colIndex = 0;

            for (Object cellData : rowData) {

                Cell cell = dataRow.createCell(colIndex);

                if (cellData != null) {

                    cell.setCellValue(cellData.toString());

                } else {

                    cell.setCellValue("");

                }

                cell.setCellStyle(dataStyle);

                colIndex++;

            }

            rowIndex++;

        }

        return rowIndex;

    }

    /**

     * 自动调整列宽

     *

     * @param sheet

     * @param columnNumber

     */

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {

        for (int i = 0; i < columnNumber; i++) {

            int orgWidth = sheet.getColumnWidth(i);

            sheet.autoSizeColumn(i, true);

            int newWidth = (int) (sheet.getColumnWidth(i) + 100);

            if (newWidth > orgWidth) {

                sheet.setColumnWidth(i, newWidth);

            } else {

                sheet.setColumnWidth(i, orgWidth);

            }

        }

    }

    /**

     * 设置边框

     *

     * @param style

     * @param border

     * @param color

     */

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {

        style.setBorderTop(border);

        style.setBorderLeft(border);

        style.setBorderRight(border);

        style.setBorderBottom(border);

        style.setBorderColor(BorderSide.TOP, color);

        style.setBorderColor(BorderSide.LEFT, color);

        style.setBorderColor(BorderSide.RIGHT, color);

        style.setBorderColor(BorderSide.BOTTOM, color);

    }

    /**

     * 导出excel工具类

     * 将需要导出的javabean 的对应属性的getXXX()添加注解 @ExcelColumn(name = "表头名字", order = 3, width=5000)

     * name:表头名字

     * order:导出的列显示在第几行,从1开始

     * width:导出的excel的列宽,默认5000

     * @param response

     * @param fileName 导出的excel文件的名称,不带后缀

     * @param list 需要导出的数据的集合

     * @param clazz 导出的数据集合的class对象

     * @param <T>

     * @throws IOException

     */

    public static <T> void exportExcelV3(HttpServletResponse response, String fileName, List<T> list, Class<T> clazz) throws IOException {

// 告诉浏览器用什么软件可以打开此文件

        response.setHeader("content-Type", "application/vnd.ms-excel");

// 下载文件的默认名称

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));

//创建excel对象

        ServletOutputStream out = response.getOutputStream();

        XSSFWorkbook book = new XSSFWorkbook();

        XSSFSheet sheet = book.createSheet();

        Font titleFont = book.createFont();

//设置字体

        titleFont.setFontName("等线");

//设置粗体

//titleFont.setBoldweight(Short.MAX_VALUE);

//设置字号

        titleFont.setFontHeightInPoints((short) 11);

//设置颜色

//titleFont.setColor(IndexedColors.BLACK.index);

//设置标题样式

        XSSFCellStyle titleStyle = book.createCellStyle();

//水平居中

        titleStyle.setAlignment(HorizontalAlignment.LEFT);

//垂直居中

        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

//设置图案颜色

//titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));

//设置图案样式

//titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        titleStyle.setFont(titleFont);

//自动换行样式

        CellStyle nwrapText = book.createCellStyle();

        nwrapText.setWrapText(true);

        nwrapText.setAlignment(HorizontalAlignment.LEFT);

        nwrapText.setVerticalAlignment(VerticalAlignment.CENTER);

//非自动换行样式

        CellStyle wrapText = book.createCellStyle();

        wrapText.setWrapText(false);

//水平居中

        wrapText.setAlignment(HorizontalAlignment.LEFT);

//垂直居中

        wrapText.setVerticalAlignment(VerticalAlignment.CENTER);

//时间yyyy-MM-dd HH:mm:ss样式

        CellStyle dateTimeStyle = book.createCellStyle();

        dateTimeStyle.setDataFormat(book.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

        dateTimeStyle.setWrapText(true);

//水平居中

        dateTimeStyle.setAlignment(HorizontalAlignment.LEFT);

//垂直居中

        dateTimeStyle.setVerticalAlignment(VerticalAlignment.CENTER);

//设置显示两位小数样式

        CellStyle doubleStyle = book.createCellStyle();

        doubleStyle.setDataFormat(book.createDataFormat().getFormat("0.00"));

//水平居中

        doubleStyle.setAlignment(HorizontalAlignment.CENTER);

//垂直居中

        doubleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Method[] methods = clazz.getDeclaredMethods();

        try {

            for(int i=0;i<list.size()+1;i++){

                XSSFRow row = sheet.createRow(i);

                for(Method me:methods){

                    if (me.isAnnotationPresent(ExcelColumn.class)){

                        ExcelColumn column = me.getAnnotation(ExcelColumn.class);

                        Cell cell = row.createCell(column.order());

                        cell.setCellStyle(wrapText);

                        if(i==0){

//设置表头

                            sheet.setColumnWidth(column.order(),column.width());

                            cell.setCellStyle(titleStyle);

                            cell.setCellValue(column.name());

                        }else{

//设置值

                            Object obj = me.invoke(list.get(i-1));

                            if (null != obj && obj.toString() != ""){

                                if (obj instanceof Date){

                                    cell.setCellValue((Date)obj);

                                    cell.setCellStyle(dateTimeStyle);

                                }else if(obj instanceof Double){

                                    cell.setCellValue((Double)obj);

                                    cell.setCellStyle(doubleStyle);

                                } else {

                                    if(column.width() >= 8000){ // 宽度大于 8000 自动换行

                                        cell.setCellStyle(nwrapText);

                                        String str = "";

                                        if(StringUtil.isNotEmpty(obj.toString())) {

                                            String[] tmp = obj.toString().split("\\r\\n\\r\\n");

                                            if (tmp.length > 1) {

                                                str = tmp[0]+convertStr(tmp[1]);

                                            }else {

                                                str = obj.toString();

                                            }

                                        }

                                        cell.setCellValue(new XSSFRichTextString(str));

                                    }else {

                                        cell.setCellStyle(wrapText);

                                        cell.setCellValue(obj.toString());

                                    }

                                }

                            }

                        }

                    }

                }

            }

            book.write(out);

        } catch (Exception e) {

            e.printStackTrace();

        }finally {

            out.close();

        }

    }

    private static String convertStr(String str){

        List<Map> list = JSON.parseArray(str,Map.class);

        StringBuffer sb = new StringBuffer();

        for (int i=0;i<list.size();i++){

            Map map = list.get(i);

            String question = (String)map.get("question");

            String answer = (String)map.get("answer");

            sb.append("\r\n\r\n问: ").append(question);

            String[] strs = answer.split(",");

            for(int j = 0;j<strs.length;j++){

                if(j==0){

                    sb.append("\r\n答: ").append(strs[j]);

                }else {

                    sb.append("\r\n ").append(strs[j]);

                }

            }

        }

        return sb.toString();

    }

    private static final String EXTENSION_XLS = "xls";

    private static final String EXTENSION_XLSX = "xlsx";

    private static final String EXTENSION_CSV = "csv";

    @SneakyThrows

    public static List<Map<Integer,Object>> readExcel(MultipartFile file) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Map<Integer,Object>> list = new ArrayList<>();

        String fileName = file.getOriginalFilename();

        String str = fileName.substring(fileName.lastIndexOf(".")+1);

        InputStream ins = file.getInputStream();

        Workbook wb = null;

        if(EXTENSION_XLS.equals(str)){

            wb = new XSSFWorkbook(ins);

        }else if(EXTENSION_XLSX.equals(str)){

            wb = new XSSFWorkbook(ins);

        }else if(EXTENSION_CSV.equals(str)){

            wb = csvToException(file);

        } else {

            throw new BusinessException("上传失败,文件格式有误");

        }

        Sheet sheet = wb.getSheetAt(0);

        int rowNum = sheet.getLastRowNum();

        Row firstRow = sheet.getRow(0);

        int colcount=firstRow.getPhysicalNumberOfCells();

        System.out.println("标题列数"+firstRow.getPhysicalNumberOfCells());

        for (int i = 0; i <= rowNum; i ++){

            Row row = sheet.getRow(i);

            if(null == row){

                continue;

            }

//判断一行当中是否所有单元格为空值

            if (checkRowNull(row,firstRow)){

                continue;

            }

            int colNum = colcount;

            Map<Integer,Object> map = new HashMap<>();

            for(int j = 0; j < colNum; j ++){

                Cell cell = row.getCell(j);

                if (null == cell){

                    continue;

                }

                if(cell.getCellTypeEnum().compareTo(CellType.NUMERIC) == 0 && DateUtil.isCellDateFormatted(cell)){

                    map.put(j, sdf.format(cell.getDateCellValue()));

                }else{

                    cell.setCellType(CellType.STRING);

                    map.put(j, cell.getStringCellValue());

                }

            }

            list.add(map);

        }

        return list;

    }

    private static boolean checkRowNull(Row row,Row firstRow){

        int count = 0;

//单元格数量

        int cellCount = firstRow.getLastCellNum() - firstRow.getFirstCellNum();

//判断多少个单元格为空

        for (int c = 0; c < cellCount; c++) {

            Cell cell = row.getCell(c);

            if (cell == null || cell.getCellTypeEnum().compareTo(CellType.BLANK) == 0 || StringUtils.isBlank(cell+"")){

                count += 1;

            }

        }

        if (count >= cellCount) {

            return true;

        }

        return false;

    }

    /**

     * @Description 获取csv数据

     * @author itw_liuhc

     * @Date 2022/8/30

     **/

    public static ArrayList<String[]> getCsvDataList(MultipartFile multipartFile) throws IOException{

        ArrayList<String[]> csvDataList = new ArrayList<>();

        CsvReader csvReader = new CsvReader(multipartFile.getInputStream(), ',', Charset.forName("utf-8"));

        while (csvReader.readRecord()) {

            csvDataList.add(csvReader.getValues());

        }

        csvReader.close();

        return csvDataList;

    }

    /**

     * @Description 生成excel文件再删

     * @author itw_liuhc

     * @Date 2022/8/30

     **/

    public static XSSFWorkbook csvToException(MultipartFile multipartFile) throws IOException {

        ArrayList<String[]> csvDataList = getCsvDataList(multipartFile);

        String csvFileNameHaveSuffix = multipartFile.getOriginalFilename();

        String csvFileName = csvFileNameHaveSuffix.substring(0, csvFileNameHaveSuffix.indexOf("."));

        XSSFWorkbook workbook = new XSSFWorkbook();

        String outputExcelFileName = csvFileName + ".xlsx";

        File file = new File(outputExcelFileName);

        FileOutputStream out = new FileOutputStream(file);

        XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

        for (int rowNum = 0; rowNum < csvDataList.size(); rowNum++) {

            String[] csvFileRowData = csvDataList.get(rowNum);

            XSSFRow row = spreadsheet.createRow(rowNum);

            for (int columnNum = 0; columnNum < csvFileRowData.length; columnNum++) {

                XSSFCell cell = row.createCell(columnNum);

                cell.setCellValue(csvFileRowData[columnNum]);

            }

        }

        workbook.write(out);

        out.close();

        file.delete();

        return workbook;

    }

}
