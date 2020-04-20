package ru.npte.sloth.slothhelper.dto;

import java.util.List;

public class Item {
    private String title;
    private String name;
    private String flags;
    private String ac;
    private String stats;
    private List<String> cpFor;
    private List<String> clasps;
    private List<String> attachTo;
    private String poppers;
    private String keywords;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }


    public void setStats(String stats) {
        this.stats = stats;
    }

    public void setCpFor(List<String> cpFor) {
        this.cpFor = cpFor;
    }

    public void setClasps(List<String> clasps) {
        this.clasps = clasps;
    }

    public void setAttachTo(List<String> attachTo) {
        this.attachTo = attachTo;
    }

    public void setPoppers(String poppers) {
        this.poppers = poppers;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public String getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>").append(this.title).append("</b>%0A");
        if (this.flags != null) {
            sb.append(this.flags).append("%0A");
        }

        if (this.ac != null) {
            sb.append(this.ac.contains("d") ? "Damage: " : "AC: ").append(this.ac);
        }

        if (this.stats != null && ! "".equalsIgnoreCase(this.stats)) {
            sb.append("%0AStats: ").append(this.stats);
        }

        if (this.cpFor != null && !this.cpFor.isEmpty()) {
            sb.append("%0ACp for:%0A").append(String.join("%0A", this.cpFor));
        }

        if (this.clasps != null) {
            sb.append("%0AHas clasp:%0A").append(String.join("%0A", this.clasps));
        }

        if (this.attachTo != null) {
            sb.append("%0AAttach to:%0A").append(String.join("%0A", this.attachTo));
        }

        return sb.toString();
    }
}
