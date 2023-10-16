package ua.lviv.iot.algo.coursework.services;

import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.coursework.models.AdvertisingPanel;
import ua.lviv.iot.algo.coursework.filemanagers.AdvertisingPanelFileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AdvertisingPanelService {
    private final Map<Integer, AdvertisingPanel> panels;
    private final AtomicInteger nextAvailableId;
    private final AdvertisingPanelFileManager fileManager;

    public AdvertisingPanelService(final AdvertisingPanelFileManager panelCSVStorage) {
        this.fileManager = panelCSVStorage;
        this.panels = new HashMap<>();
        this.nextAvailableId = new AtomicInteger(0);
        loadPanels();
    }

    public List<AdvertisingPanel> getAll() {
        return new ArrayList<>(panels.values());
    }

    public AdvertisingPanel getById(final Integer id) {
        return panels.get(id);
    }

    public void createPanel(final AdvertisingPanel panel) {
        panel.setId(nextAvailableId.incrementAndGet());
        panels.put(panel.getId(), panel);
        fileManager.writeToCSV(getAll(), fileManager.filePath);
    }

    public AdvertisingPanel updatePanel(final Integer id,final AdvertisingPanel panel) {
        if (panels.containsKey(id)) {
            panel.setId(id);
            panels.put(id, panel);
            fileManager.writeToCSV(getAll(), fileManager.filePath);
            return panel;
        }
        return null;
    }

    public boolean deletePanel(final Integer id) {
        if (panels.containsKey(id)) {
            panels.remove(id);
            fileManager.writeToCSV(getAll(), fileManager.filePath);
            return true;
        }
        return false;
    }

    private void loadPanels() {
        List<AdvertisingPanel> loadedPanels = fileManager.load();
        panels.clear();
        for (AdvertisingPanel panel : loadedPanels) {
            panels.put(panel.getId(), panel);
        }
        int maxId = loadedPanels.stream().mapToInt(AdvertisingPanel::getId).max().orElse(0);
        nextAvailableId.set(maxId + 1);
    }
}
