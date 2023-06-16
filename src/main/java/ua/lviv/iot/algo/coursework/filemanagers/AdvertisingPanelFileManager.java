package ua.lviv.iot.algo.coursework.filemanagers;


import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.coursework.models.AdvertisingPanel;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class AdvertisingPanelFileManager {
    private final String filePattern;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    public final String filePath = getFilePath();
    public static final String PATH_TO_FILES = "src/main/resources/CSVFiles";

    public AdvertisingPanelFileManager() {
        String currentDate = getCurrentMonth();
        this.filePattern = "advertising_panel_" + currentDate + "-\\d{1,2}\\.csv";

    }

    public File[] getMatchingFiles(final String pathToFiles) {
        File directory = new File(pathToFiles);
        return directory.listFiles((dir, name) -> name.matches(filePattern));
    }

    public void writeToCSV(final List<AdvertisingPanel> panels, final String filePath) {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(AdvertisingPanel.getHeaders() + "\n");
            for (AdvertisingPanel panel : panels) {
                writer.write(panel.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AdvertisingPanel> load() {
        List<AdvertisingPanel> panels = new ArrayList<>();
        File[] matchingFiles = getMatchingFiles(PATH_TO_FILES);

        for (File file : matchingFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    String[] values = line.split(",");
                    if (values.length == 8) {
                        AdvertisingPanel panel = createAdvertisingPanelFromCSV(values);
                        panels.add(panel);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return panels;
    }

    private AdvertisingPanel createAdvertisingPanelFromCSV(final String[] values) {
        AdvertisingPanel panel = new AdvertisingPanel();
        try {
            panel.setId(Integer.parseInt(values[0].trim()));
            panel.setDepartment(values[1].trim());
            panel.setManufacturer(values[2].trim());
            panel.setScreenDiagonal(Integer.parseInt(values[3].trim()));
            panel.setResolution(values[4].trim());
            panel.setGraduateYear(Integer.parseInt(values[5].trim()));
            panel.setSupermarketId(Integer.parseInt(values[6].trim()));
            panel.setVideoId(Integer.parseInt(values[7].trim()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return panel;
    }

    private String getCurrentMonth() {
        return dateFormat.format(new Date());
    }

    private String getFilePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        return "src/main/resources/CSVFiles/advertising_panel_" + currentDate + ".csv";
    }
}
