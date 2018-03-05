package com.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtil {

	public static void createHeader(XSSFWorkbook workBook, XSSFSheet sheet, String [] titles, Integer[] widths){
		XSSFRow titleRow = sheet.createRow(0);
		if(titles == null || titles.length == 0){
			return;
		}
		XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
		XSSFFont font = workBook.createFont();
		font.setColor(XSSFFont.COLOR_NORMAL);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		for(int i = 0; i < titles.length; i++){
			if(widths != null && widths[i] != null){
				sheet.setColumnWidth(i, widths[i]);
			}
			XSSFCell cell = titleRow.createCell(i, 0);
			cell.setCellStyle(cellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
		}
	}
}
