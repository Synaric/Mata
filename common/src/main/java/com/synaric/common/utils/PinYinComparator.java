package com.synaric.common.utils;

import java.util.Comparator;

/**
 * <br/><br/>Created by Synaric on 2016/11/18 0018.
 */
public class PinYinComparator<T extends OrderedItem> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        String s1 = o1.getIndexTag(o1.orderBy());
        String s2 = o2.getIndexTag(o2.orderBy());
        char c1 = s1.charAt(0);
        char c2 = s2.charAt(0);
        boolean isLetter1 = ExtTextUtils.isLetter(c1);
        boolean isLetter2 = ExtTextUtils.isLetter(c2);

        if (isLetter1 ^ isLetter2) {
            return isLetter1 ? 1 : -1;
        }

        return s1.compareTo(s2);
    }
}
