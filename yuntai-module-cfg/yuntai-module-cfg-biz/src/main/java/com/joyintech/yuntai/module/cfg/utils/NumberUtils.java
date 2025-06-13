package com.joyintech.yuntai.module.cfg.utils;

public class NumberUtils {

    // 方法：根据输入的数字返回对应的字母序列
    public static String getSequence(int index) {
        // 单字母序列的范围是从1到26
        if (index >= 1 && index <= 26) {
            return String.valueOf((char) ('a' + index - 1));
        }

        // 双字母序列的范围是从27到702 (26 * 26)
        int twoLetterEndIndex = 26 * 26 + 26;
        if (index <= twoLetterEndIndex) {
            int internalIndex = index - 26 - 1; // 调整为从0开始的内部索引
            char first = (char) ('a' + internalIndex / 26);
            char second = (char) ('a' + internalIndex % 26);
            return String.valueOf(first) + String.valueOf(second);
        }

        // 三字母序列的范围是从703到17576 (26 * 26 * 26)
        int threeLetterEndIndex = 26 * 26 * 27 + 26;
        if (index <= threeLetterEndIndex) {
            int internalIndex = index - twoLetterEndIndex - 1; // 调整为从0开始的内部索引
            int firstValue = internalIndex / (26 * 26);
            int secondValue = (internalIndex % (26 * 26)) / 26;
            int thirdValue = internalIndex % 26;
            char first = (char) ('a' + firstValue);
            char second = (char) ('a' + secondValue);
            char third = (char) ('a' + thirdValue);
            return String.valueOf(first) + String.valueOf(second) + String.valueOf(third);
        }

        // 如果索引超出三字母序列的范围，抛出异常或返回空字符串（根据需要）
//        throw new IllegalArgumentException("Index out of range for three-letter sequences");
        return null;
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(getSequence(1));  // 输出: a
        System.out.println(getSequence(26));  // 输出: z
        System.out.println(getSequence(27)); // 输出: aa
        System.out.println(getSequence(702)); // 输出: zz
        System.out.println(getSequence(703)); // 输出: aaa
        System.out.println(getSequence(18278)); // 输出: zzz
    }
}
