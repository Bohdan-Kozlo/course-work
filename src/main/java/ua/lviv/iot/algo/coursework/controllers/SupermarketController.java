package ua.lviv.iot.algo.coursework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.coursework.models.Supermarket;
import ua.lviv.iot.algo.coursework.services.SupermarketService;

import java.util.List;

@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {
    private final SupermarketService supermarketService;

    public SupermarketController(final SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @GetMapping
    public ResponseEntity<List<Supermarket>> getAllSupermarkets() {
        List<Supermarket> supermarkets = supermarketService.getAll();

        if (!supermarkets.isEmpty()) {
            return ResponseEntity.ok(supermarkets);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supermarket> getSupermarketById(@PathVariable final Integer id) {
        Supermarket supermarket = supermarketService.getById(id);
        if (supermarket != null) {
            return ResponseEntity.ok(supermarket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Supermarket> createSupermarket(@RequestBody final Supermarket supermarket) {
        supermarketService.createSupermarket(supermarket);
        return ResponseEntity.status(HttpStatus.CREATED).body(supermarket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supermarket> updateSupermarket(@PathVariable final Integer id, @RequestBody final Supermarket supermarket) {
        Supermarket updatedSupermarket = supermarketService.updateSupermarket(id, supermarket);
        if (updatedSupermarket != null) {
            return ResponseEntity.ok(updatedSupermarket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupermarket(@PathVariable final Integer id) {
        boolean deleted = supermarketService.deleteSupermarket(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
