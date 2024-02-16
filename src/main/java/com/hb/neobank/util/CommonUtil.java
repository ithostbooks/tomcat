package com.hb.neobank.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

public class CommonUtil {

    public static boolean checkNullEmpty(List<?> resultList) {
        return (resultList != null && !resultList.isEmpty());
    }

    public static boolean checkNullEmpty(HashMap<?, ?> resultList) {
        return (resultList != null && !resultList.isEmpty());
    }

    public static <T> T getSingleResult(List<T> resultList) {
        if (checkNullEmpty(resultList)) {
            return resultList.get(0);
        }
        return null;
    }

    public static boolean checkNullEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

    public static boolean checkNullEmpty(Object obj) {
        return (obj != null && !obj.toString().isEmpty());
    }

    public static boolean checkNullEmpty(StringBuilder obj) {
        return (obj != null && obj.length() > 0);
    }

    public static boolean checkNullEmpty(int val) {
        return val != 0;
    }
    public static boolean isNumeric(String strNum) {
        return checkNullEmpty(strNum) && strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static int getIntValue(Object val) {
        if (val instanceof BigInteger) {
            return ((BigInteger) val).intValue();
        } else if (val instanceof Integer) {
            return ((Integer) val).intValue();
        } else if (val instanceof String) {
            return Integer.parseInt((String) val);
        }
        return 0;
    }
}
