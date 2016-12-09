package com.synaric.common.utils;

import android.text.TextUtils;

import java.util.List;

/**
 * 文字相关工具集。
 * <br/><br/>Created by Synaric on 2016/12/2 0002.
 */
public class ExtTextUtils {

    /**
     * 判断指定char是否是英文字母。
     */
    public static boolean isLetter(char c) {
        return c >= 65 && c <= 90 || c >= 97 && c <= 122;
    }

    /**
     * 寻找第一个以指定字母开头的数据，不区分大小写。
     * @param list 搜索的数据集。
     * @param prefix 必须为单个字母。如果为null或者""，则返回第一个数据。如果为非空非字母，则返回第
     *               一个非字母开头的数据。
     * @return 第一个匹配的数据的索引。如果没有找到，返回-1。
     */
    public static int findFirstStartsWith(List<OrderedItem> list, String prefix) {
        if (list == null || list.isEmpty()) return -1;
        if (TextUtils.isEmpty(prefix)) return 0;
        boolean prefixIsLetter = isLetter(prefix.charAt(0));
        int size = list.size();
        for (int i = 0; i < size; ++i) {
            OrderedItem item = list.get(i);
            String indexTag = item.getIndexTag();
            if (TextUtils.isEmpty(indexTag)) continue;
            if (prefixIsLetter) {
                if (indexTag.toUpperCase().startsWith(prefix.toUpperCase())) return i;
            } else {
                if (!isLetter(indexTag.charAt(0))) return i;
            }
        }
        return -1;
    }

    /**
     * 检测srcText是否包含targetText。
     */
    public static boolean contains(String srcText, CharSequence targetText) {
        return srcText != null && srcText.contains(targetText);
    }
}
