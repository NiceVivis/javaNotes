package com.vivi.basic.redis;

import com.redis.HashUtil;

import java.util.UUID;

/**
 * @author yangwei
 * @date 2021/1/7 2:37 下午
 */
public class UUIDUtil {

    /**
     * 生成10位UUID
     * @return
     */
    public static String getID() {
        UUID uuid = UUID.randomUUID();
        return HashUtil.convertToHashStr(uuid.getMostSignificantBits(), 5) + HashUtil.convertToHashStr(uuid.getLeastSignificantBits(), 5);
    }

    /**
     * 转换目前32位UUID为10位UUID
     * @param uuidStr
     * @return
     */
    public static String convertID(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return HashUtil.convertToHashStr(uuid.getMostSignificantBits(), 5) + HashUtil.convertToHashStr(uuid.getLeastSignificantBits(), 5);
    }
}
