package com.redis;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 对一序列文本按字建tried树索引，以方便查找及提高匹配效率
 */
public class StringTriedUnit {
    private Character letter;
    private Map<Character, StringTriedUnit> children =
            new TreeMap<Character, StringTriedUnit>();

    /**
     * 增加字符串
     */
    public void addString(String str) {
        if(StringUtils.isEmpty(str)) {
            char c = 0;
            if(!children.containsKey(c)) {
                children.put(c, new StringTriedUnit());
            }
            return;
        }

        char c = Character.toUpperCase(str.charAt(0));
        String subStr = str.substring(1);
        StringTriedUnit tried = children.get(c);
        if(tried == null) {
            tried = new StringTriedUnit();
            tried.setLetter(c);
            children.put(c, tried);
        }
        tried.addString(subStr);
    }

    /**
     * 判断数组中的字符列表，是否被string所包含
     * 如"中国","历史"
     * 则match("ab你是中国人")则返回true
     */
    public boolean beMatched(String string) {
        return beMatched(string, true);
    }

    /**
     * @param string
     * @param topLevel 为true时，是顶层递归节点
     * @return
     */
    protected boolean beMatched(String string, boolean topLevel) {
        if(children.containsKey((char)0)) {
            return true;
        }
        if(StringUtils.isEmpty(string)) {
            return false;
        }

        for(int i=0; i<string.length(); i++) {
            char c = Character.toUpperCase(string.charAt(i));
            StringTriedUnit tried = children.get(c);
            if(tried != null) {
                String subStr = string.substring(i+1);
                if(tried.beMatched(subStr, false)) {
                    return true;
                }
            }

            if(!topLevel) {
                // 非顶层递归，直接返回false
                return false;
            }
        }
        return false;

    }

    /**
     * 判断字符串是否存在
     * @param str
     * @param fullContain 是否全量包含
     * @return
     */
    public boolean contain(String str, boolean fullContain) {
        if(StringUtils.isEmpty(str)) {
            if(!fullContain) {
                return true;
            }

            char c = 0;
            if(children.containsKey(c)) {
                return true;
            }
            return false;
        }

        char c = Character.toUpperCase(str.charAt(0));
        String subStr = str.substring(1);
        StringTriedUnit tried = children.get(c);
        if(tried == null) {
            return false;
        }

        return tried.contain(subStr, fullContain);
    }

    public void getSubStrings(List<String> subList, StringBuffer strHeadBuffer, String queryStr, int maxStrNum) {
        if(subList == null) {
            return;
        }

        if(subList.size() >= maxStrNum) {
            return;
        }

        if(StringUtils.isEmpty(queryStr)) {
            dumpSubStrings(subList, strHeadBuffer.toString(), maxStrNum);
        } else {
            char c = queryStr.charAt(0);
            String subStr = queryStr.substring(1);
            StringTriedUnit tried = children.get(c);
            if(tried == null) {
                return;
            }
            tried.getSubStrings(subList, strHeadBuffer.append(c), subStr, maxStrNum);
        }
    }

    public void dumpSubStrings(List<String> subList, String head, int maxStrNum) {
        if(subList.size() >= maxStrNum) {
            return;
        }

        for(Map.Entry<Character,StringTriedUnit> e : children.entrySet()) {
            if(e.getKey() == 0) {
                subList.add(head);
            } else {
                e.getValue().dumpSubStrings(subList, head + e.getKey(), maxStrNum);
            }
        }
    }

    public boolean isEnd() {
        return letter == null && (children == null || children.isEmpty());
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Map<Character, StringTriedUnit> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, StringTriedUnit> children) {
        this.children = children;
    }
}

