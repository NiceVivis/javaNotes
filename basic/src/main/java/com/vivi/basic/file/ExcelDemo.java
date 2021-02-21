package com.vivi.basic.file;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author yangwei
 * @date 2020/12/4 10:04 下午
 *
 * sql区分中英文：
 * 1、匹配是否包含中文
 * HEX(phone) REGEXP 'e[4-9][0-9a-f]{4}'
 *
 * 2、正则匹配是否包含英文
 * phone REGEXP '[a-zA-Z]+'
 */
public class ExcelDemo {

    @Test
    public void readtextData() throws IOException {
        Map<String, String> mapDelaer = readDelaer();
        Map<Integer, String> mapNewPerson = readNewPerson();
        Map<Integer,String> mapStr = new HashMap();
        Set<String> dealCode  = mapDelaer.keySet();
        System.out.printf(""+mapNewPerson);


        for (Map.Entry<Integer, String> entry:mapNewPerson.entrySet()){
            System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
            //System.out.println(""+entry.getKey()+","+dealCode.contains(entry.getKey()));
            if (dealCode.contains(entry.getValue())){
                mapStr.put(entry.getKey(),mapDelaer.get(entry.getValue()));
            }
        }

        System.out.println(""+mapStr);
        for (Map.Entry<Integer,String> stringEntry:mapStr.entrySet()){
            System.out.println("update recommandbuy_newperson_info set dealer_code = '"+stringEntry.getValue()+"' where id = "+stringEntry.getKey());
        }
    }

    @Test
    public void readCsvData(){

    }

    public static void main(String[] args) {
        String str = "3369211\n" +
                "3414310\n" +
                "3418439\n" +
                "3445868\n" +
                "3465599\n" +
                "3577174\n" +
                "3580479\n" +
                "3821665\n" +
                "4141991\n" +
                "4158385\n" +
                "4175787\n" +
                "4175801\n" +
                "3577174";
        String[] strs = str.split("\n");
        System.out.println(""+strs);
        for (String s:strs){
            System.out.println();
        }
    }

    public static void excel() throws IOException {
//        Map<String, String> mapDelaer = readDelaer();
//        Map<String, Integer> mapNewPerson = readNewPerson();
//        //System.out.println(JSON.toJSONString(map));
//        Map<Integer,String> mapStr = new HashMap();
//        for (Map.Entry<String,String> entry:mapDelaer.entrySet()){
//            for (Map.Entry<String,Integer> entryDealer:mapNewPerson.entrySet()){
//                System.out.println(""+entry.getKey()+","+entryDealer.getKey()+","+entry.getKey().equals(entryDealer.getKey()));
//                if (entry.getKey().equals(entryDealer.getKey())){
//                    mapStr.put(entryDealer.getValue(),entry.getValue());
//                }
//            }
//        }

//        System.out.println(""+mapStr);
//        for (Map.Entry<Integer,String> stringEntry:mapStr.entrySet()){
//            System.out.println("update recommandbuy_newperson_info set dealer_code = '"+stringEntry.getValue()+"' where id = "+stringEntry.getKey());
//        }
    }

    private static Map<String,String> readDelaer() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file = new File(path + "/dealer");
        //File file = new File("/Users/dasouche/IdeaProjects/javaNotes/basic/src/main/resources/dealer");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        Map<String, String> stringIntegerHashMap = new HashMap<>();
        // 一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            String[] strArray = tempString.split(" ");
            stringIntegerHashMap.put(strArray[1], strArray[0]);
        }
        reader.close();
        return stringIntegerHashMap;
    }

    private static Map<Integer, String> readNewPerson() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file = new File(path + "/newperson.txt");
        //File file = new File("/Users/dasouche/IdeaProjects/javaNotes/basic/src/main/resources/newperson.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        Map<Integer, String> stringIntegerHashMap = new HashMap<>();
        // 一次读入一行，直到读入null为文件结束
        int len = 0;
        while ((tempString = reader.readLine()) != null) {
            len++;
            String[] strArray = tempString.split(" ");
//            stringIntegerHashMap.put(strArray[1], Integer.valueOf(strArray[0]));
            stringIntegerHashMap.put(Integer.valueOf(strArray[0]),strArray[1]);
        }
        reader.close();
        return stringIntegerHashMap;
    }

    private static Map<String,List<Integer>> readText() throws IOException {
        File file = new File("/Users/zm/IdeaProjects/study-test/server/src/main/java/com/souche/study/test/study/test.text");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        HashMap<String, List<Integer>> map = new HashMap<>();
        // 一次读入一行，直到读入null为文件结束
        int len = 0;
        while ((tempString = reader.readLine()) != null) {
            TestDemo testDemo = new TestDemo();
            String[] s = tempString.split(" ");
            testDemo.setId(Integer.valueOf(s[0]));
            testDemo.setName(s[1]);
            List<Integer> memberIds = map.computeIfAbsent(testDemo.getName(), l -> new ArrayList<>());
            memberIds.add(testDemo.getId());
        }
        reader.close();
        System.out.println(len);
        return map;
    }

    public void readCsv(){
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File file = new File(path + "/area.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while((line=reader.readLine())!=null){
                String item[] = line.split(";");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

                String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                System.out.println(last);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readExcle() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File xlsFile = new File(path + "/area.xlsx");
        // 工作表
        Workbook workbook = WorkbookFactory.create(xlsFile);

        // 表个数。
        int numberOfSheets = workbook.getNumberOfSheets();

        // 遍历表。
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);

            // 行数。
            int rowNumbers = sheet.getLastRowNum() + 1;

            // Excel第一行。
            Row temp = sheet.getRow(0);
            if (temp == null) {
                continue;
            }

            int cells = temp.getPhysicalNumberOfCells();

            String str = "INSERT INTO `common_dict`.`dict`(`GROUP_ID`,`CODE`,`CODE_VALUE`,`CODE_EN_DESC`, `TYPE`, `P_CODE`, `CODE_CN_DESC`) VALUES";
            String strValue = "";
            // 读数据。
            for (int row = 0; row < rowNumbers; row++) {
                Row r = sheet.getRow(row);
                String str1 = "";
                for (int col = 0; col < cells; col++) {

                    str1 += "'"+getCellValue(r.getCell(col))+"'";
                    //System.out.print(r.getCell(col).toString()+" ");
                }

                strValue += "('"+str1+"'),";
                // 换行。
            }
            System.out.println(str+strValue);
        }

    }

    public void exportTxt(){

    }

    @Data
    static
    class TestDemo{
        private Integer id;
        private String name;
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
