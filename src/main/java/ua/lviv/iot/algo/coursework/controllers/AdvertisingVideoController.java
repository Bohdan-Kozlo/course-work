package ua.lviv.iot.algo.coursework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.coursework.models.AdvertisingVideo;
import ua.lviv.iot.algo.coursework.services.AdvertisingVideoService;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class AdvertisingVideoController {
    private final AdvertisingVideoService videoService;

    public AdvertisingVideoController( final AdvertisingVideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity<List<AdvertisingVideo>> getAllVideos() {
        List<AdvertisingVideo> videos = videoService.getAll();

        if (!videos.isEmpty()) {
            return ResponseEntity.ok(videos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisingVideo> getVideoById(@PathVariable final Integer id) {
        AdvertisingVideo video = videoService.getById(id);
        if (video != null) {
            return ResponseEntity.ok(video);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AdvertisingVideo> createVideo(@RequestBody final AdvertisingVideo video) {
        videoService.createAdvertisingVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisingVideo> updateVideo(@PathVariable final Integer id, @RequestBody final AdvertisingVideo video) {
        AdvertisingVideo updatedVideo = videoService.updateAdvertisingVideo(id, video);
        if (updatedVideo != null) {
            return ResponseEntity.ok(updatedVideo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable final Integer id) {
        boolean deleted = videoService.deleteAdvertisingVideo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
