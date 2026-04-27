package com.sky.test;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {

    /**
     * 写入excel
     * @throws IOException
     */
    public static void write() throws IOException {
        XSSFWorkbook excel = new XSSFWorkbook();
        XSSFSheet sheet = excel.createSheet("测试");

        XSSFRow row = sheet.createRow(1);
        //创建单元格并且写入文件内容
        row.createCell(1).setCellValue("姓名");
        row.createCell( 2).setCellValue("城市");

        //创建一个新行
        row = sheet.createRow( 2);
        row.createCell( 1).setCellValue("张三");
        row.createCell( 2).setCellValue("北京");

        row = sheet.createRow( 3);
        row.createCell( 1).setCellValue("李四");
        row.createCell( 2).setCellValue("南京");

        excel.write(new FileOutputStream("D:/test.xlsx"));

        excel.close();
        System.out.println("写入成功");

    }

    /**
     * 读取excel
     * @throws IOException
     */
    public static void read() throws IOException {
        //读取磁盘上已经存在的Excel文件
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("D:\\test.xlsx")));
        //读取Excel文件中的第一个Sheet页
        XSSFSheet sheet = excel.getSheetAt( 0);

        //获取Sheet中最后一行的行号
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum ; i++) {
            //获得某一行
            XSSFRow row = sheet.getRow(i);
            //获得单元格对象
            String cellValue1 = row.getCell( 1).getStringCellValue();
            String cellValue2 = row.getCell( 2).getStringCellValue();
            System.out.println(cellValue1 + " " + cellValue2);
        }
    }

    /**
     * 测试
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //write();
        read();
    }
}
