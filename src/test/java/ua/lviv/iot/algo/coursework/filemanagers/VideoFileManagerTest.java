package ua.lviv.iot.algo.coursework.filemanagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.coursework.models.AdvertisingVideo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


class VideoFileManagerTest {
    public static final String PATH_TO_TEST_FILES = "src/main/resources/testFiles";
    public static final String PATH_TO_EXPECTED_FILE = "src/main/resources/testFiles";
    public static final String PATH_TO_RESULT_FILE = "src/main/resources/testFiles";
    public final VideoFileManager fileManager = new VideoFileManager();
    List<AdvertisingVideo> videos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        AdvertisingVideo video1 = new AdvertisingVideo(1, "Manufacturer 1", "Brand 1", 30);
        AdvertisingVideo video2 = new AdvertisingVideo(2, "Manufacturer 2", "Brand 2", 45);
        AdvertisingVideo video3 = new AdvertisingVideo(3, "Manufacturer 3", "Brand 3", 60);
        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
    }

    @Test
    void writeToCSV() throws IOException {
        Path expectedFile = new File(PATH_TO_EXPECTED_FILE).toPath();
        Path actualFile = new File(PATH_TO_RESULT_FILE).toPath();
        fileManager.writeToCSV(videos, PATH_TO_RESULT_FILE);
        Assertions.assertEquals(-1L, Files.mismatch(expectedFile, actualFile));
    }

    @Test
    void getMatchingFiles() {
        File[] files = fileManager.getMatchingFiles(PATH_TO_TEST_FILES);
        Assertions.assertEquals(2, files.length);
    }
}