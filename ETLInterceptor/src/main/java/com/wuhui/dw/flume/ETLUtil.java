package com.wuhui.dw.flume;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 从工具类用来检查数据格式
 */

public class ETLUtil {
    public static boolean validStartLog(String source) {
        if(StringUtils.isBlank(source)){
            return  false;
        }
        String trimStr = source.trim();
        if(trimStr.startsWith("{")&&trimStr.endsWith("}")){
            return  true;
        }
        return false;
    }

    public static boolean validEventLog(String source) {
        if(StringUtils.isBlank(source)){
            return  false;
        }
        String trimStr = source.trim();
        String[] words = trimStr.split("\\|");

        if(words.length!=2){
            return  false;
        }
        if(words[0].length()!=13 || !NumberUtils.isDigits(words[0])){
            return false;
        }
        if(words[1].startsWith("{") && words[1].startsWith("}")){
            return true;
        }
        return false;
    }
}
