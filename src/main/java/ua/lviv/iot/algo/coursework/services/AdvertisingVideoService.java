package ua.lviv.iot.algo.coursework.services;

import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.coursework.filemanagers.VideoFileManager;
import ua.lviv.iot.algo.coursework.models.AdvertisingVideo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AdvertisingVideoService {
    private final Map<Integer, AdvertisingVideo> advertisingVideos;
    private final AtomicInteger nextAvailableId;
    private final VideoFileManager videoFileManager;

    public AdvertisingVideoService(final VideoFileManager videoFileManager) {
        this.advertisingVideos = new HashMap<>();
        this.nextAvailableId = new AtomicInteger(0);
        this.videoFileManager = videoFileManager;
        loadVideos();
    }

    public List<AdvertisingVideo> getAll() {
        return new ArrayList<>(advertisingVideos.values());
    }

    public AdvertisingVideo getById(final Integer id) {
        return advertisingVideos.get(id);
    }

    public void createAdvertisingVideo(final AdvertisingVideo advertisingVideo) {
        advertisingVideo.setId(nextAvailableId.incrementAndGet());
        advertisingVideos.put(advertisingVideo.getId(), advertisingVideo);
        videoFileManager.writeToCSV(getAll(), videoFileManager.filePath);
    }

    public AdvertisingVideo updateAdvertisingVideo(final Integer id,final AdvertisingVideo advertisingVideo) {
        if (advertisingVideos.containsKey(id)) {
            advertisingVideo.setId(id);
            advertisingVideos.put(id, advertisingVideo);
            videoFileManager.writeToCSV(getAll(), videoFileManager.filePath);
            return advertisingVideo;
        }
        return null;
    }

    public boolean deleteAdvertisingVideo(final Integer id) {
        if (id != null && advertisingVideos.containsKey(id)) {
            advertisingVideos.remove(id);
            videoFileManager.writeToCSV(getAll(), videoFileManager.filePath);
            return true;
        }
        return false;
    }

    private void loadVideos() {
        List<AdvertisingVideo> videos = videoFileManager.load();
        advertisingVideos.clear();
        for (AdvertisingVideo video : videos) {
            advertisingVideos.put(video.getId(), video);
        }
        int maxId = videos.stream().mapToInt(AdvertisingVideo::getId).max().orElse(0);
        nextAvailableId.set(maxId + 1);
    }
}
