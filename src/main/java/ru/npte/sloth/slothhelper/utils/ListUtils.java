package ru.npte.sloth.slothhelper.utils;

import java.util.List;

public final class ListUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    private ListUtils() {
    }
}
