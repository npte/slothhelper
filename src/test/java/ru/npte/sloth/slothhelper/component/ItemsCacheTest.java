package ru.npte.sloth.slothhelper.component;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ItemsCacheTest extends TestCase {

    private static final ItemsCache itemCache = new ItemsCache();

    @Test
    public void testGetItemInfo() {
        List<String> a = itemCache.getItemInfo("a soul imprisoned within a phial");

        System.out.println(a.stream().map(e -> e.replaceAll("%0A", System.lineSeparator())).collect(Collectors.toList()));
    }

}