package ua.lviv.iot.algo.coursework.filemanagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.coursework.models.Supermarket;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


class SupermarketFileManagerTest {
    public static final String PATH_TO_TEST_FILES = "src/main/resources/testFiles";
    public static final String PATH_TO_EXPECTED_FILE = "src/main/resources/testFiles/supermarket_expected.csv";
    public static final String PATH_TO_RESULT_FILE = "src/main/resources/testFiles/supermarket_actual.csv";
    public final SupermarketFileManager fileManager = new SupermarketFileManager();
    public final List<Supermarket> supermarkets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Supermarket supermarket1 = new Supermarket(1, "Address 1", 1000.0, "Retail Chain 1", "9 AM - 6 PM", 500, 101);
        Supermarket supermarket2 = new Supermarket(2, "Address 2", 1500.0, "Retail Chain 2", "8 AM - 9 PM", 700, 102);
        Supermarket supermarket3 = new Supermarket(3, "Address 3", 1200.0, "Retail Chain 3", "10 AM - 7 PM", 600, 103);
        supermarkets.add(supermarket1);
        supermarkets.add(supermarket2);
        supermarkets.add(supermarket3);
    }

    @Test
    void writeToCSV() throws IOException {
        Path expectedFile = new File(PATH_TO_EXPECTED_FILE).toPath();
        Path actualFile = new File(PATH_TO_RESULT_FILE).toPath();
        fileManager.writeToCSV(supermarkets, PATH_TO_RESULT_FILE);
        Assertions.assertEquals(-1L, Files.mismatch(expectedFile, actualFile));
    }

    @Test
    void getMatchingFiles() {
        File[] files = fileManager.getMatchingFiles(PATH_TO_TEST_FILES);
        Assertions.assertEquals(2, files.length);
    }

}