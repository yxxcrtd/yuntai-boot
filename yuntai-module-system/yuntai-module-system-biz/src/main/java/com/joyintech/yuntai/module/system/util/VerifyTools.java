package com.joyintech.yuntai.module.system.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * 校验工具类
 *
 * @author zhaohuihua
 * @version 150916
 */
public class VerifyTools {

    /** 静态工具类私有构造方法 **/
    private VerifyTools() {
    }






    /** 返回第一个非空的参数; 如果全都为空, 返回最后一个参数 **/
    @SuppressWarnings("unchecked")
    public static <T> T nvl(T... objects) {
        T last = null;
        for (T object : objects) {
            last = object;
            if (isNotBlank(object)) {
                return object;
            }
        }
        return last;
    }

    /**
     * 判断字符串是否为空<br>
     * 零长度的字符串将被判定为空
     *
     * @param string 目标对象
     * @return true or false
     */
    public static boolean isBlank(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 判断字符串是否为非空
     *
     * @param string 目标对象
     * @return true or false
     */
    public static boolean isNotBlank(String string) {
        return string != null && string.length() > 0;
    }

    /**
     * 判断对象是否为空<br>
     * 零长度的字符串, length=0的数组, 空的Collection, 空的Map, 空的Iterable都将被判定为空
     *
     * @param object 目标对象
     * @return true or false
     */
    public static boolean isBlank(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof CharSequence) {
            CharSequence string = (CharSequence) object;
            return string.length() == 0;
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object instanceof Iterable) {
            return !((Iterable<?>) object).iterator().hasNext();
        } else if (object instanceof Enumeration) {
            return !((Enumeration<?>) object).hasMoreElements();
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否为非空
     *
     * @param object 目标对象
     * @return true or false
     */
    public static boolean isNotBlank(Object object) {
        return !isBlank(object);
    }

    /**
     * 只有一个为空就返回true
     *
     * @param objects 参数
     * @return 是否存在空值
     */
    public static boolean isAnyBlank(Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }

        for (Object object : objects) {
            if (isBlank(object)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 全都为空就返回true
     *
     * @param objects 目标对象
     * @return 是否全都为空
     */
    public static boolean isAllBlank(Object... objects) {
        if (objects == null) {
            return true;
        }

        for (Object object : objects) {
            if (isNotBlank(object)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 全都不为空就返回true
     *
     * @param objects 目标对象
     * @return 是否全都不为空
     */
    public static boolean isNoneBlank(Object... objects) {
        return !isAnyBlank(objects);
    }

    /**
     * 判断是不是集合对象, 能转换为list就是集合对象
     *
     * @param object 目标对象
     * @return 是否集合对象
     */
    public static boolean isCollection(Object object) {
        if (object == null) {
            return false;
        } else if (object.getClass().isArray()) {
            return true;
        } else if (object instanceof Collection) {
            return true;
        } else if (object instanceof Iterator) {
            return true;
        } else if (object instanceof Iterable) {
            return true;
        } else if (object instanceof Enumeration) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 判断对象是否存在于列表中
     *
     * @param object 目标对象
     * @param objects 列表
     * @return 是否存在
     */
    @SafeVarargs
    public static <T> boolean isExists(T object, T... objects) {
        if (objects == null) {
            return false;
        }
        for (Object i : objects) {
            if (i == null && object == null) {
                return true;
            } else if (i != null && i.equals(object)) {
                return true;
            } else if (object != null && object.equals(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否不存在于列表中
     *
     * @param object 目标对象
     * @param objects 列表
     * @return 是否不存在
     */
    @SafeVarargs
    public static <T> boolean isNotExists(T object, T... objects) {
        return !isExists(object, objects);
    }

    /**
     * 判断两个对象是不是相等
     *
     * @param o 第一个对象
     * @param n 第二个对象
     * @return 是否相等
     */
    public static boolean equals(Object o, Object n) {
        if (o == null && n == null) {
            return true;
        } else if (isBlank(o) && isBlank(n)) {
            return true;
        } else if (o == null || n == null) {
            return false;
        } else {
            return o.equals(n);
        }
    }

    /**
     * 判断两个对象是不是不相等
     *
     * @param o 第一个对象
     * @param n 第二个对象
     * @return 是否不相等
     */
    public static boolean notEquals(Object o, Object n) {
        return !equals(o, n);
    }





    /** 是不是JsonArray字符串 **/
    public static boolean isJsonArrayString(String value) {
        return checkJsonArrayString(value);
    }

    /** 是不是JsonArray字符串 **/
    public static boolean isJsonArrayString(Object value) {
        return value instanceof String && checkJsonArrayString((String) value);
    }

    private static boolean checkJsonArrayString(String string) {
        return checkStringFeature(string, '[', ']');
    }

    /**
     * 是不是XML字符串 (只是简单判断以&lt;开头且以&gt;结尾)
     *
     * @param value 判断目标
     * @return 判断结果
     * @since 5.5.13
     */
    public static boolean isXmlString(String value) {
        return checkXmlString(value);
    }

    /**
     * 是不是XML字符串 (只是简单判断以&lt;开头且以&gt;结尾)
     *
     * @param value 判断目标
     * @return 判断结果
     * @since 5.5.13
     */
    public static boolean isXmlString(Object value) {
        return value instanceof String && checkXmlString((String) value);
    }

    private static boolean checkXmlString(String string) {
        return checkStringFeature(string, '<', '>');
    }

    private static boolean checkStringFeature(String value, char first, char last) {
        if (value == null) {
            return false;
        }
        String string = value.trim();
        if (string.length() > 0 && string.charAt(0) == first && string.charAt(string.length() - 1) == last) {
            return true;
        }
        return false;
    }
}

