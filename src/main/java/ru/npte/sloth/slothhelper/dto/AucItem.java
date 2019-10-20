package ru.npte.sloth.slothhelper.dto;

import org.jsoup.select.Elements;

public class AucItem {

    private String num;
    private String name;
    private String current;
    private String buyout;
    private String ends;
    private String searchLink;

    AucItem(Elements elements) {
        this.num = elements.get(0).text();
        this.name = elements.get(1).text();
        this.current = elements.get(4).text();
        this.buyout = elements.get(5).text();
        this.ends = elements.get(6).text();
        //this.searchLink = "http://slothmudeq.tk/?search=" + this.name.replaceAll("(^a )|(^A )|(^an )|(^An )|(^the )|(^The )", "").replace("'", "%27").replace(" ", "+");
    }

    public String toString() {
        return new StringBuilder().append(this.num).append(". ")
                .append("<a href=\"").append(this.searchLink).append("\">").append(this.name).append("</a>")
                .append("\r\nCurrent: ").append(this.current).append("\r\nBuyout: ").append(this.buyout).append("\r\nEnds: ").append(this.ends).toString();
    }

}
