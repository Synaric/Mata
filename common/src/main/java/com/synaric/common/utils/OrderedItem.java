package com.synaric.common.utils;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * <br/><br/>Created by Synaric on 2016/11/30 0030.
 */
public abstract class OrderedItem {

    private String indexTag;

    public String getIndexTag(String orderBy) {
        if (indexTag == null) {
            if (orderBy == null) return "";
            char c = orderBy.charAt(0);
            indexTag = Pinyin.toPinyin(c);
        }
        return indexTag;
    }

    public String getIndexTag() {
        return getIndexTag(orderBy());
    }

    public abstract String orderBy();
}
