package ru.npte.sloth.slothhelper.dto;

import java.util.Arrays;
import java.util.Objects;

public class AucItem {

    private final String id;
    private final String seller;
    private final String name;
    private final String current;
    private final String buyout;
    private final String age;

    public AucItem(String aucString) {
        String[] tokens = aucString.split(" ");
        this.id = tokens[0];
        this.seller = tokens[1];
        this.current = tokens[4];
        this.buyout = tokens[5];
        this.age = tokens[6];
        this.name = String.join(" ", Arrays.asList(tokens).subList(7, tokens.length));
    }

    public String getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }

    public String getCurrent() {
        return current;
    }

    public String getBuyout() {
        return buyout;
    }

    public String getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AucItem aucItem = (AucItem) o;
        return getId().equals(aucItem.getId()) &&
                getSeller().equals(aucItem.getSeller()) &&
                getName().equals(aucItem.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSeller(), getName());
    }
}
