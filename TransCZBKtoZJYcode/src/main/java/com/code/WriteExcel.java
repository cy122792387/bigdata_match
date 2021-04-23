package com.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
  private static final String EXCEL_XLS = "xls";
  private static final String EXCEL_XLSX = "xlsx";

  public static void writeExcelSingleandMultiple(List<Map> dataList, int cloumnCount, String finalXlsxPath) {
    OutputStream out = null;
    try {
      // 获取总列数
      int columnNumCount = cloumnCount;
      // 读取Excel文档
      File finalXlsxFile = new File(finalXlsxPath);
      Workbook workBook = getWorkbok(finalXlsxFile);
      // sheet 对应一个工作页
      Sheet sheet = workBook.getSheetAt(0);
      // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
      out = new FileOutputStream(finalXlsxPath);
      workBook.write(out);
      /**
       * 往Excel中写新数据
       */
      for (int j = 0; j < dataList.size(); j++) {
        // 创建一行：从第二行开始，跳过属性列
        Row row = sheet.createRow(j + 2);
        // 得到要插入的每一条记录
        Map dataMap = dataList.get(j);
        String stem = dataMap.get("题干").toString();
        String type = dataMap.get("题型").toString();
//        String answer = dataMap.get("答案").toString();
        for (int k = 0; k <= columnNumCount; k++) {
          // 在一行内循环
          Cell first = row.createCell(1);
          first.setCellValue(stem);

          Cell second = row.createCell(2);
          second.setCellValue(type);
          row.createCell(5).setCellValue(dataMap.get("答案").toString());
          if (type.equals("单选题") || type.equals("多选题")) {
            row.createCell(7).setCellValue(dataMap.get("A").toString());
            row.createCell(8).setCellValue(dataMap.get("B").toString());
            row.createCell(9).setCellValue(dataMap.get("C").toString());
            row.createCell(10).setCellValue(dataMap.get("D").toString());
          }
        }
      }
      // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
      out = new FileOutputStream(finalXlsxPath);
      workBook.write(out);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.flush();
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 判断Excel的版本,获取Workbook
   * @throws IOException
   */
  public static Workbook getWorkbok(File file) throws IOException {
    Workbook wb = null;
    FileInputStream in = new FileInputStream(file);
    if (file.getName().endsWith(EXCEL_XLS)) {     //Excel 2003
      wb = new HSSFWorkbook(in);
    } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
      wb = new XSSFWorkbook(in);
    }
    return wb;
  }
}