package com.vivi.basic.SPI;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author yangwei
 * @date 2021/2/19 2:48 下午
 */
public class SPIMain {
    public static void main(String[] args) {
        ServiceLoader serviceLoader = ServiceLoader.load(Aobing.class);
        Iterator<Aobing> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Aobing aobing = iterator.next();
            aobing.say();
        }
    }
}
