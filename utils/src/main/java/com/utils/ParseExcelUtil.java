package com.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParseExcelUtil {

    private ParseExcelUtil(){

    }

    /**
     * 根据文件的路径创建Workbook对象
     * @param filePath   文件路径
     * @return
     */
    public static Workbook getExcelWorkBook(String filePath){
        InputStream ins = null;
        Workbook book = null;
        try{
            ins = new FileInputStream(new File(filePath));
            book = WorkbookFactory.create(ins);
            ins.close();
            return book;
        }catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (InvalidFormatException e2) {
            e2.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static XSSFWorkbook get2007ExcelWorkBook(String filePath){
        InputStream ins = null;
        XSSFWorkbook book = null;
        try{
            ins = new FileInputStream(new File(filePath));
            book = new XSSFWorkbook(ins);
            ins.close();
            return book;
        }catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将工作簿对象的每个sheet的名字当做map的key值，行列信息作为value
     * @param workbook  工作簿对象
     * @return
     */
    public static Map<String,List<List<String>>> getExcelSheetMapData(Workbook workbook){
        Map<String,List<List<String>>> map = new HashMap<String,List<List<String>>>();
        if(workbook == null){
            return map;
        }
        // 循环Sheet对象
        for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
            Sheet sheet = workbook.getSheetAt(sheetNum);
            List<List<String>> lists = getSheetToList(sheet);
            if(lists.size() == 0){
                continue;
            }
            String sheetName = sheet.getSheetName();
            map.put(sheetName,lists);
        }
        return map;
    }

    /**
     * 将sheet信息拼装为list，最外层list每个元素是行，里层list每个元素是单元格值
     * @param sheet   excel sheet页
     * @return
     * @throws IOException
     */
    public static List<List<String>> getSheetToList(Sheet sheet){
        return getSheetToList(sheet,0);
    }

    /**
     * 将sheet信息拼装为list，最外层list每个元素是行，里层list每个元素是单元格值
     * @param sheet   excel sheet页
     * @param rowNum  要从第几行循环
     * @return
     */
    public static List<List<String>> getSheetToList(Sheet sheet,int rowNum){
        List<List<String>> lists = new ArrayList<List<String>>();
        if(sheet == null){
            return lists;
        }
        // 循环行
        for (int rows = rowNum; rows <= sheet.getLastRowNum(); rows++) {
            Row row = sheet.getRow(rows);
            // 循环列Cell
            lists.add(getRowData(row));
        }
        return lists;
    }

    /**
     * 获取单元格内容
     * @param cell    单元格对象
     * @return
     */
    public static String getValue(Cell cell) {
        String str = "";
        if(cell == null){
            return str;
        }
        int cellType = cell.getCellType();

        switch (cellType){
            case Cell.CELL_TYPE_BLANK :
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                str = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_STRING :
                str = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC :
                str = String.valueOf(cell.getNumericCellValue());
                break;
        }
        return str;
    }

    /**
     * 获取单元格内容
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell){
        Object value = null;
        int cellType = cell.getCellType();
        switch(cellType){
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                value = cell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
        }
        return value;
    }


    /**
     * 根据行对象获取行数据
     * @param row  行对象
     * @return
     */
    public static List<String> getRowData(Row row){
        List<String> list = new ArrayList<String>();
        if(row == null){
            return list;
        }

        for(int cellNum = 0; cellNum <= row.getLastCellNum() - 1; cellNum++){
            Cell cell = row.getCell(cellNum);
            list.add(getValue(cell));
        }
        return list;
    }

    /**
     * 根据sheet行数获取行信息
     * @param sheet     sheet页
     * @param rowIndex  需要获取的行数从0开始
     * @return
     */
    public static List<String> getRowData(Sheet sheet,int rowIndex) throws Exception {
        List<String> list = new ArrayList<String>();
        if (sheet == null){
            return list;
        }

        // 获取整个sheet页的最大行数
        int rowSumNum = sheet.getLastRowNum();
        if (rowIndex > rowSumNum-1){
            throw new Exception("要获取的行数大于excel总行数");
        }

        Row row = sheet.getRow(rowIndex);

        return getRowData(row);
    }

    public static List<Map<String,String>> getExcelMapData(Sheet sheet,List<String> headTitle){

        return null;
    }


}
