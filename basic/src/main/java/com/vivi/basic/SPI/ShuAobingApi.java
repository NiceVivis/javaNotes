package com.vivi.basic.SPI;

/**
 * @author yangwei
 * @date 2021/2/19 2:47 下午
 */
public class ShuAobingApi implements Aobing{

    @Override
    public void say() {
        System.out.println("hello ShuAobingApi");
    }
}
