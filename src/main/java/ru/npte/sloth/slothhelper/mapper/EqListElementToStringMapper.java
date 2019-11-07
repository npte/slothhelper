package ru.npte.sloth.slothhelper.mapper;

import org.jsoup.nodes.Element;
import ru.npte.sloth.slothhelper.exceptions.AcDivNotFoundException;
import ru.npte.sloth.slothhelper.exceptions.NameDivNotFoundException;
import ru.npte.sloth.slothhelper.exceptions.TitleDivNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.npte.sloth.slothhelper.enums.EqListClassNames.*;

public class EqListElementToStringMapper {

    public static String map(Element element) {
        Item item = new Item();

        Element titleDiv = element
                .getElementsByClass(CELL_TITLE.getName())
                .stream()
                .findFirst()
                .orElseThrow(TitleDivNotFoundException::new);

        item.setTitle(titleDiv
                .getElementsByClass(NAME.getName())
                .stream()
                .findFirst()
                .orElseThrow(NameDivNotFoundException::new)
                .text());

        titleDiv.getElementsByClass(EXTRA.getName())
                .stream()
                .findFirst()
                .ifPresent(e -> item.setFlags(e.text()));

        item.setAc(element
                .getElementsByClass(CELL_AC.getName())
                .stream()
                .findFirst()
                .orElseThrow(TitleDivNotFoundException::new)
                .getElementsByTag("span")
                .stream()
                .findFirst()
                .orElseThrow(AcDivNotFoundException::new)
                .text());

        Optional<Element> statsDiv = element
                .getElementsByClass(CELL_STATS.getName())
                .stream()
                .findFirst();

        statsDiv.ifPresent(a -> a.childNodes()
                .stream()
                .filter(e -> ((Element)e).classNames().isEmpty())
                .findFirst()
                .ifPresent(e -> item.setStats(((Element) e).text())));

        if (statsDiv.isPresent()) {

            statsDiv.get().getElementsByClass(CPS_FOR.getName()).stream().findFirst().ifPresent(
                    e -> item.setCpFor(e.childNodes()
                            .stream()
                            .filter(el -> el instanceof Element)
                            .filter(el -> ((Element)el).hasClass(ATTACH_ITEM.getName()))
                            .map(el -> ((Element) el).text())
                            .collect(Collectors.toList()))
            );

            statsDiv.get().getElementsByClass(HAS_CLASP.getName()).stream().findFirst().ifPresent(
                    e -> item.setClasps(e.childNodes()
                            .stream()
                            .filter(el -> el instanceof Element)
                            .filter(el -> ((Element)el).hasClass(ATTACH_ITEM.getName()))
                            .map(el -> ((Element) el).text())
                            .collect(Collectors.toList()))
            );
        }

        return item.toString();
    }

    private static class Item {
        private String title;
        private String flags;
        private String ac;
        private String stats;
        private List<String> cpFor;
        private List<String> clasps;
        private String poppers;

        void setTitle(String title) {
            this.title = title;
        }

        void setFlags(String flags) {
            this.flags = flags;
        }

        void setAc(String ac) {
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

        public void setPoppers(String poppers) {
            this.poppers = poppers;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<b>").append(this.title).append("</b>%0A");
            if (this.flags != null) {
                sb.append(this.flags).append("%0A");
            }

            sb.append(this.ac.contains("d") ? "Damage: " : "AC: ").append(this.ac);

            if (this.stats != null && ! "".equalsIgnoreCase(this.stats)) {
                sb.append("%0AStats: ").append(this.stats);
            }

            if (this.cpFor != null && !this.cpFor.isEmpty()) {
                sb.append("%0ACp for:%0A").append(String.join("%0A", this.cpFor));
            }

            if (this.clasps != null) {
                sb.append("%0AHas clasp:%0A").append(String.join("%0A", this.clasps));
            }

            return sb.toString();
        }
    }
}


