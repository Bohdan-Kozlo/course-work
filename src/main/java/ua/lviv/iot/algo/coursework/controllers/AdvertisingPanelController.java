package ua.lviv.iot.algo.coursework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.coursework.models.AdvertisingPanel;
import ua.lviv.iot.algo.coursework.services.AdvertisingPanelService;

import java.util.List;

@RestController
@RequestMapping("/panels")
public class AdvertisingPanelController {
    private final AdvertisingPanelService panelService;

    public AdvertisingPanelController(final AdvertisingPanelService panelService) {
        this.panelService = panelService;
    }

    @GetMapping
    public ResponseEntity<List<AdvertisingPanel>> getAllPanels() {
        List<AdvertisingPanel> panels = panelService.getAll();
        if (!panels.isEmpty()) {
            return ResponseEntity.ok(panels);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisingPanel> getPanelById(@PathVariable final Integer id) {
        AdvertisingPanel panel = panelService.getById(id);
        if (panel != null) {
            return ResponseEntity.ok(panel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AdvertisingPanel> createPanel(@RequestBody final AdvertisingPanel panel) {
        panelService.createPanel(panel);
        return ResponseEntity.status(HttpStatus.CREATED).body(panel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisingPanel> updatePanel(@PathVariable final Integer id, @RequestBody final AdvertisingPanel panel) {
        AdvertisingPanel updatedPanel = panelService.updatePanel(id, panel);
        if (updatedPanel != null) {
            return ResponseEntity.ok(updatedPanel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdvertisingPanel> deletePanel(@PathVariable final Integer id) {
        boolean deleted = panelService.deletePanel(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
