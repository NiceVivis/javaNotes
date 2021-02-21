package com.redis;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TraceUtils {
    public static InputStream openResourceInputStream(String file) throws IOException {
        if (new File(file).exists()) {
            return new FileInputStream(file);
        }

        URL u = TraceUtil.class.getResource(file);
        if (u == null) {
            u = TraceUtil.class.getResource("/" + file);
            if (u == null) {
                return null;
            }
        }
        return u.openStream();
    }

    static int[][] hundred = new int[99][];

    static {

        // 顺序 48-57 65-90 97-122 其实‘z’可以不用放进去的
        char[] strs = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        // 初始化
        for (int i = 0; i < hundred.length; i++) {
            hundred[i] = makeInt((i + 1), strs);
        }
    }

    private static int[] makeInt(int num, char[] strs) {
        // num 1-99 = max 1-61
        int max = Math.max((strs.length * num / 100), 1);

        int[] tempint = new int[max];

        for (int i = 0; i < max; i++) {
            tempint[i] = strs[i];
        }

        return tempint;
    }

    /**
     * @param tid
     * @param num 采样率
     * @return
     */
    public static boolean needSample(String tid, int num) {
        if (StringUtils.isEmpty(tid) || num == 100) {
            return true;
        }
        if (num == 0) {
            return false;
        }

        int x = tid.split("_")[1].charAt(0);// 48-57 65-90 97-122

        int[] is = hundred[num - 1];

        return (x >= is[0] && x <= is[is.length - 1]);
    }

    public static void main(String[] args) {

        String[] tids = new String[] {
                "12_0E", "12_1j", "ee_2J", "34_3k", "33_40", "44_5j", "dd_6o",
                "12_7E", "12_8j", "ee_9J",
                "34_ak", "33_b0", "44_cj", "dd_do", "12_eE", "12_fj", "ee_gJ",
                "34_hk", "33_i0", "44_jj", "dd_ko", "12_lE", "12_mj", "ee_nJ",
                "34_ok", "33_p0", "44_qj", "dd_io", "12_sE", "12_tj", "ee_uJ",
                "34_vK", "33_w0", "44_xJ", "DD_yO", "12_zE",
                "34_AK", "33_B0", "44_CJ", "DD_DO", "12_EE", "12_FJ", "EE_GJ",
                "34_HK", "33_I0", "44_JJ", "DD_KO", "12_LE", "12_MJ", "EE_NJ",
                "34_OK", "33_P0", "44_QJ", "DD_IO", "12_SE", "12_TJ", "EE_UJ",
                "34_VK", "33_W0", "44_XJ", "DD_YO", "12_ZE"
        };

        System.out.println("测试量:"+tids.length);

        int count = 0;

        for (int i = 0; i < tids.length; i++) {
            /***
             * 假装流入
             */
            if (needSample(tids[i], 50)) {
                count++;
                System.out.println("/sys/打个正常的日志.log 采样 " + tids[i] + " 采样率:" + 50 + "%");
            }
        }
        System.out.println("=== count:" + count);
        count = 0;
        for (int i = 0; i < tids.length; i++) {
            if (needSample(tids[i], 1)) {
                count++;
                System.out.println("/sys/打个正常的日志.log 采样 " + tids[i] + " 采样率:" + 1 + "%");
            }
        }
        System.out.println("=== count:" + count);
        count = 0;
        for (int i = 0; i < tids.length; i++) {
            if (needSample(tids[i], 99)) {
                count++;
                System.out.println("/sys/打个正常的日志.log 采样 " + tids[i] + " 采样率:" + 99 + "%");
            }
        }
        System.out.println("=== count:" + count);
        count = 0;
        for (int i = 0; i < tids.length; i++) {
            if (needSample(tids[i], 98)) {
                count++;
                System.out.println("/sys/打个正常的日志.log 采样 " + tids[i] + " 采样率:" + 98 + "%");
            }
        }
        System.out.println("=== count:" + count);
        count = 0;
        for (int i = 0; i < tids.length; i++) {
            if (needSample(tids[i], 0)) {
                count++;
                System.out.println("/sys/打个正常的日志.log 采样 " + tids[i] + " 采样率:" + 0 + "%");
            }
        }
        System.out.println("=== count:" + count);
        count = 0;
        for (int i = 0; i < tids.length; i++) {
            if (needSample(tids[i], 100)) {
                count++;
                System.out.println("/sys/打个正常的日志.log 采样 " + tids[i] + " 采样率:" + 100 + "%");
            }
        }
        System.out.println("=== count:" + count);
    }
}

