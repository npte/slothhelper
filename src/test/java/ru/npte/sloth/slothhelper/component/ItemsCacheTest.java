package ru.npte.sloth.slothhelper.component;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class ItemsCacheTest extends TestCase {

    private static final ItemsCache itemCache = new ItemsCache();

    @Test
    public void testGetItemInfo() {
        List<String> a = itemCache.getItemInfo("a emerald");
        System.out.println(a);
    }

}