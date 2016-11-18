package com.synaric.common.utils;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Comparator;

/**
 * <br/><br/>Created by Synaric on 2016/11/18 0018.
 */
public abstract class PinYinComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        String s1 = orderBy(o1);
        String s2 = orderBy(o2);
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";
        if (Pinyin.isChinese(s1.charAt(0))) s1 = Pinyin.toPinyin(s1.charAt(0));
        if (Pinyin.isChinese(s2.charAt(0))) s2 = Pinyin.toPinyin(s2.charAt(0));

        return s1.compareTo(s2);
    }

    protected abstract String orderBy(T t);
}
