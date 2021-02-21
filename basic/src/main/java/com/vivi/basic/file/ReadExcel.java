package com.vivi.basic.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 * @date 2021/1/22 9:33 上午
 */
public class ReadExcel {

    @Test
    public void readXlsx() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file = new File(path + "/area.xlsx");

        //String filePath = "C:\\Users\\admin\\Desktop\\xxx.xls";
        //如果是xls，使用HSSFWorkbook；如果是xlsx，使用XSSFWorkbook
        //File file=new File(filePath);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        //得到指定的Exel
        Workbook wb;
        String fileName = file.getName();
        String suffer = fileName.substring(fileName.lastIndexOf('.') + 1);
        if ("xls".equals(suffer)) {
            // 2003Excel
            wb = new HSSFWorkbook(new FileInputStream(file));
        } else if ("xlsx".equals(suffer)) {
            // 2007Excel
            wb = new XSSFWorkbook(OPCPackage.openOrCreate(file));
        }else {
            System.out.println("文件类型不对");
            return;
        }
        Map<Object,Object> map= new HashMap<>();
        //得到该Exel中的第一张表
        Sheet sh=wb.getSheetAt(0);
        //System.out.println(sh.getSheetName());
        //得到行数
        int rows=sh.getLastRowNum();
        //循环得到每一行
        String str = "INSERT INTO `common_dict`.`dict`(`GROUP_ID`,`CODE`,`CODE_VALUE`, `CODE_CN_DESC`,`CODE_EN_DESC`, `TYPE`, `TYPE_NAME`, `P_CODE`) VALUES";
        String strValue = "";
        for(int i=0;i<=rows;i++){
            //得到的当前行
            Row r=sh.getRow(i);
            int cols=r.getLastCellNum();//得到总共有多少列
            strValue += "('"+getCellValue(r.getCell(0))+"','"+getCellValue(r.getCell(1))+"','"
                    +getCellValue(r.getCell(2))+"','"+getCellValue(r.getCell(2))+"','"
                    +getCellValue(r.getCell(3))+"','"+getCellValue(r.getCell(4))+"','"+getCellValue(r.getCell(4))
                    +"','"+getCellValue(r.getCell(5))+"'"+"),";
//            System.out.println(cols+"列");
//            System.out.println(getCellValue(r.getCell(0))+ "," + getCellValue(r.getCell(1))+","
//                    +getCellValue(r.getCell(2))+","+getCellValue(r.getCell(3))
//                    +","+getCellValue(r.getCell(4))+","+getCellValue(r.getCell(5)));
//            System.out.println(getCellValue(r.getCell(cols)));
            //循环得到每行中的指定列，下面是得到第一列和第二列
//            map.put(r.getCell(0),r.getCell(1));
        }
        System.out.printf(str+strValue);
//
//        System.out.println("行数："+map.size());
//        for (Map.Entry<Object,Object> entry : map.entrySet()) {
//            System.out.println("表名： " + entry.getKey() + "，主键：" + entry.getValue());
//        }

    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型

            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    // 数字
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cell.getNumericCellValue());
                    break;
                case STRING:
                    // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case FORMULA:// 公式
                    cellValue = cell.getCellFormula() + "";
                    break;
                case BLANK:
                    // 空值
                    cellValue = "";
                    break;
                case _NONE:  // 故障
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue;
    }

}
