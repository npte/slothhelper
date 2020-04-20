package ru.npte.sloth.slothhelper.enums;

public enum EqListClassNames {
    ITEM("item"),
    NAME("name"),
    EXTRA("extra extra1"),
    CELL_TITLE("cell title"),
    CELL_STATS("cell stats"),
    CELL_POPPERS("cell poppers"),
    CELL_AC("cell ac"),
    KEYWORDS("keywords"),
    CPS_FOR("cpsFor attaches"),
    HAS_CLASP("cpsFor attaches hasClasp"),
    ATTACH_ITEM("attachitem"),
    ATTACHES("attaches"),
    ;

    private String name;

    EqListClassNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
