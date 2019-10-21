package ru.npte.sloth.slothhelper.repo;

import ru.npte.sloth.slothhelper.dto.AucItem;

public interface AucRepository {
    AucItem get(AucItem aucItem);

    void put(AucItem aucItem);
}
