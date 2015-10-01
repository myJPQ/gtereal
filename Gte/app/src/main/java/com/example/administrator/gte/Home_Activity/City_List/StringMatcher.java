package com.example.administrator.gte.Home_Activity.City_List;

/**
 * Created by J on 15/10/1.
 */
public class StringMatcher {


    //vale item 文本
    //keyword 索引表中的字符
    public static boolean match(String value, String keyword) {

        if (value == null || keyword == null)
            return false;
        if (keyword.length() > value.length())
            return false;
//i时value的指针 j是keyword的指针
        int i = 0, j = 0;
        do {
            // 如果是韩文字符并且在韩文初始数组里面
                if (keyword.charAt(j) == value.charAt(i)){
                i++;
                j++;
            }else if (j > 0)
                break;
            else
                i++;

        } while (i < value.length() && j < keyword.length());
        // 如果最后j等于keyword的长度说明匹配成功
        return (j == keyword.length()) ? true : false;

    }
}
