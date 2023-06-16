package ua.lviv.iot.algo.coursework.filemanagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.coursework.models.AdvertisingPanel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class AdvertisingPanelFileManagerTest {
    public static final String PATH_TO_TEST_FILES = "src/main/resources/testFiles";
    public static final String PATH_TO_EXPECTED_FILE = "src/main/resources/testFiles/advertising_panel_expected.csv";
    public static final String PATH_TO_RESULT_FILE = "src/main/resources/testFiles/advertising_panel_actual.csv";
    public final AdvertisingPanelFileManager fileManager = new AdvertisingPanelFileManager();
    public final List<AdvertisingPanel> panels = new ArrayList<>();

    @BeforeEach
    void setUp() {
        AdvertisingPanel panel1 = new AdvertisingPanel(1, "Department 1", "Manufacturer 1", 32, "1920x1080", 2022, 101, 201);
        AdvertisingPanel panel2 = new AdvertisingPanel(2, "Department 2", "Manufacturer 2", 40, "3840x2160", 2021, 102, 202);
        AdvertisingPanel panel3 = new AdvertisingPanel(3, "Department 3", "Manufacturer 3", 24, "1280x720", 2023, 103, 203);
        panels.add(panel1);
        panels.add(panel2);
        panels.add(panel3);
    }

    @Test
    void writeToCSV() throws IOException {
        Path expectedFile = new File(PATH_TO_EXPECTED_FILE).toPath();
        Path actualFile = new File(PATH_TO_RESULT_FILE).toPath();
        fileManager.writeToCSV(panels, PATH_TO_RESULT_FILE);
        Assertions.assertEquals(-1L, Files.mismatch(expectedFile, actualFile));
    }

    @Test
    void getMatchingFiles() {
        File[] files = fileManager.getMatchingFiles(PATH_TO_TEST_FILES);
        Assertions.assertEquals(2, files.length);

    }
}