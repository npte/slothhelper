package ru.npte.sloth.slothhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.npte.sloth.slothhelper.dto.AucItem;
import ru.npte.sloth.slothhelper.repo.AucRepository;

@Service
public class StorageService {

    @Autowired
    private AucRepository aucRepository;

    public boolean isNewAutItem(AucItem aucItem) {
        AucItem itemInRepo = aucRepository.get(aucItem);

        if (aucItem.equals(itemInRepo)) {
            return false;
        }

        aucRepository.put(aucItem);
        return true;
    }
}
