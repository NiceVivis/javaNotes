package com.vivi.basic.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * break跳出循环
 * continue跳出本次循环
 * return 跳出循环并且返回之前的数据
 */
public class BreakOrContinue {
    public static void main(String[] args) {
        List<String> idList = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<Integer> searchIdList = new ArrayList<>();
        ids.add("789");
        ids.add("758");
        ids.add("655");
        ids.add("654");
        ids.add("653");
        ids.add("652");
        ids.add("647");
        ids.add("788");
        ids.add("787");
        ids.add("748");
        ids.add("646");
        int size = 5;
        int endIndex = 0;
        for (String id : ids){
            System.out.println("idList.size = "+idList.size()+",size="+size);
            if (idList.size() >= size){
                break;
            }
            endIndex++;

            boolean isExist = searchIdList.contains(Integer.valueOf(id));
            if (isExist) {
                continue;
            }
            idList.add(id);
        }
        System.out.println("endIndex2 = "+endIndex);

        int sum = 0;
        for (String id : ids){

            if (id == "647"){
                break;
            }
            sum++;
        }
        System.out.println("break--> sum = "+sum);

        int num = 0;
        for (String id : ids){

            if (id == "647"){
                continue;
            }
            num++;
        }
        System.out.println("continue--> num = "+num);

        int cnum = 0;
        for (String id : ids){

            if (id == "647"){
                System.out.println("return--> cnum = "+cnum);
                return;
            }
            cnum++;
        }
    }

}
