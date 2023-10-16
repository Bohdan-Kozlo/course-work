package ua.lviv.iot.algo.coursework.services;


import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.coursework.models.Supermarket;
import ua.lviv.iot.algo.coursework.filemanagers.SupermarketFileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SupermarketService {
    private final Map<Integer, Supermarket> supermarkets;
    private final AtomicInteger nextAvailableId;
    private final SupermarketFileManager fileManager;

    public SupermarketService(final SupermarketFileManager supermarketFileManager) {
        this.supermarkets = new HashMap<>();
        this.nextAvailableId = new AtomicInteger(0);
        this.fileManager = supermarketFileManager;
        loadSupermarkets();
    }

    public List<Supermarket> getAll() {
        return new ArrayList<>(supermarkets.values());
    }

    public Supermarket getById(final Integer id) {
        return supermarkets.get(id);
    }

    public void createSupermarket(final Supermarket supermarket) {
        supermarket.setId(nextAvailableId.incrementAndGet());
        supermarkets.put(supermarket.getId(), supermarket);
        fileManager.writeToCSV(getAll(), fileManager.filePath);
    }

    public Supermarket updateSupermarket(final Integer id, final Supermarket supermarket) {
        if (supermarkets.containsKey(id)) {
            supermarket.setId(id);
            supermarkets.put(id, supermarket);
            fileManager.writeToCSV(getAll(), fileManager.filePath);
            return supermarket;
        }
        return null;
    }

    public boolean deleteSupermarket(final Integer id) {
        if (id != null && supermarkets.containsKey(id)) {
            supermarkets.remove(id);
            fileManager.writeToCSV(getAll(), fileManager.filePath);
            return true;
        }
        return false;
    }

    private void loadSupermarkets() {
        List<Supermarket> loadedSupermarkets = fileManager.load();
        supermarkets.clear();
        for (Supermarket supermarket : loadedSupermarkets) {
            supermarkets.put(supermarket.getId(), supermarket);
        }
        int maxId = loadedSupermarkets.stream().mapToInt(Supermarket::getId).max().orElse(0);
        nextAvailableId.set(maxId + 1);
    }
}
