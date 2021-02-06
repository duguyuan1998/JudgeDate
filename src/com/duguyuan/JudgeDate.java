package com.duguyuan;

import java.util.Scanner;

public class JudgeDate {

    /**
     * 判断一个日期格式是否合法
     */
    public static boolean judgeDate(String date) {
        String date_str = date;
        int length = date_str.length();
        // 判断日期字符串长度是否合理
        if (length < 8 || length > 11) {
            return false;
        }
        // 判断日期字符串的每个字符是否合法, 并初步判断日期格式类型
        char c = ' ';
        /**
         * 1 1 x ==>> 代表 YYYY-MM-DD 格式 ==> 并且 x 代表两个'-' 的索引位置之差
         * 2 2 y ==>> 代表 YYYY/MM/DD 格式 ==> 并且 y 代表两个'/' 的索引位置之差
         * x y z ==>> 代表 YYYY年MM月DD日 并且 xyz(z > y > z)分别为'年' '月' '日'字符的索引位置
         */
        int[] indexes = new int[3];
        for (int i = 0; i < length; i++) {
            c = date_str.charAt(i);
            if (c == '-' || c == '/') {
                int flag = (c == '-') ? 1 : 2;
                if(i == 0 || i == length - 1 || (indexes[0] != 0 && indexes[0] != flag)) {
                    return false;
                }
                if (indexes[0] == 0) {
                    indexes[0] = flag;
                    indexes[2] = i;
                } else if (indexes[0] == flag && indexes[1] == 0) {
                    indexes[1] = flag;
                    indexes[2] = i - indexes[2];
                    if (indexes[2] <= 1) {
                        return false;
                    }
                } else { // 出现两次以上的'-'或'/'则日期格式错误
                    return false;
                }
            } else if (c == '年') {
                if (i == 0 || indexes[0] != 0) {
                    return false;
                }
                indexes[0] = i;
            } else if (c == '月') {
                if (indexes[1] != 0) {
                    return false;
                }
                indexes[1] = i;
            } else if (c == '日') {
                if (i != length - 1 || indexes[2] != 0) {
                    return false;
                }
                indexes[2] = i;
            } else if (c < '0' || c > '9') {
                return false;
            }
        }
        // 统一处理为 YYYY-MM-DD日期格式类型
        if (indexes[0] == 1 && indexes[1] == 1) {
        } else if (indexes[0] == 2 && indexes[1] == 2) {
            date_str = date_str.replaceAll("/", "-");
        } else if ((indexes[0] != 0) && (indexes[1] > indexes[0] + 1) && (indexes[2] > indexes[1] + 1) && (indexes[2] == length - 1) ) {
            date_str = date_str.substring(0, indexes[0]) + "-" + date_str.substring(indexes[0] + 1, indexes[1]) + "-" + date_str.substring(indexes[1] + 1, indexes[2]);
        } else {
            return false;
        }
        String[] yearMonthDay = date_str.split("-");
        try {
            int year = Integer.parseInt(yearMonthDay[0]);
            int month = Integer.parseInt(yearMonthDay[1]);
            int day = Integer.parseInt(yearMonthDay[2]);
            if (dateIsLegal(year, month, day)) {
                System.out.println(date + "是" + year + "年的第" + dateDay(year, month, day) + "天");
                return true;
            }
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 根据年月日判断日期是否合法
     */
    public static boolean dateIsLegal(int year, int month, int day) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) {
            days[1]++;
        }
        if (month <= 0 || month > 12) {
            return false;
        }
        if (day > 0 && day <= days[month - 1]) {
            return true;
        }
        return false;
    }

    /**
     * 计算一个合法的日期是当年的第多少天
     */
    public static int dateDay(int year, int month, int day) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) {
            days[1]++;
        }
        int manyDays = 0;
        for (int i = 0; i < month - 1; i++) {
            manyDays += days[i];
        }
        return manyDays + day;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        if (!judgeDate(date)) {
            System.out.println("您输入的内容不是一个日期");
        }
    }
}
