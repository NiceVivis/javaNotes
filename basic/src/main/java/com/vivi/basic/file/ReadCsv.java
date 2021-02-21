package com.vivi.basic.file;

import com.csvreader.CsvReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangwei
 * @date 2021/1/22 3:11 下午
 */
public class ReadCsv {

    @Test
    public void readCsv1(){

        try {
            String path = getClass().getClassLoader().getResource("area.csv").getPath();
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            String item[] = new String[0];
            while((line=reader.readLine())!=null){
                item= line.split("，");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

                String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                System.out.println(last);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readCsv2(){

        String path = getClass().getClassLoader().getResource("area.csv").getPath();

        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(path);

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                System.out.println(csvReader.getRawRecord());
                // 读这行的某一列
                System.out.println(csvReader.get("Link"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void readCsv3(){
        try {
            String path = getClass().getClassLoader().getResource("area.csv").getPath();
            File csv = new File(path); // CSV文件

            BufferedReader br = new BufferedReader(new FileReader(csv));

// 读取直到最后一行
            String line = "";
            while ((line = br.readLine()) != null) {
// 把一行数据分割成多个字段
                StringTokenizer st = new StringTokenizer(line, ",");

                while (st.hasMoreTokens()) {
// 每一行的多个字段用TAB隔开表示
                    System.out.print(st.nextToken() + "\t");
                }
                System.out.println();
            }
            br.close();

        } catch (FileNotFoundException e) {
// 捕获File对象生成时的异常
            e.printStackTrace();
        } catch (IOException e) {
// 捕获BufferedReader对象关闭时的异常
            e.printStackTrace();
        }
    }
}

