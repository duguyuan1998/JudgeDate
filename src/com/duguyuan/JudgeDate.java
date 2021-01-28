package com.duguyuan;

import java.util.Scanner;

public class JudgeDate {

    /**
     * 判断一个日期格式是否合法
     */
    public static boolean judgeDate(String date) {
        String date_str = date;
        int length = date_str.length();
        if (length != 10 && length != 11) {
            return false;
        }
        if (length == 11) {
            if (date_str.charAt(4) == '年' && date_str.charAt(7) == '月' && date_str.charAt(10) == '日') {
                date_str = date_str.substring(0, 4) + "-" + date_str.substring(5, 7) + "-" + date_str.substring(8, 10);
            } else {
                return false;
            }
        }
        if (length == 10) {
            if ((date_str.charAt(4) == date_str.charAt(7)) && (date_str.charAt(4) == '-' || date_str.charAt(7) == '/')) {
                if (date_str.charAt(4) == '/') {
                    date_str = date_str.replaceAll("/", "-");
                }
            }else {
                return false;
            }
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
