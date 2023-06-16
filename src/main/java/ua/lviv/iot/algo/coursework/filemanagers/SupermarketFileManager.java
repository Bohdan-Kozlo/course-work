package ua.lviv.iot.algo.coursework.filemanagers;


import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.coursework.models.Supermarket;

import java.io.*;
import java.util.*;


import java.text.SimpleDateFormat;


@Component
public class SupermarketFileManager {
    private final String filePattern;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    public final String filePath = getFilePath();
    public static final String PATH_TO_FILES = "src/main/resources/CSVFiles";

    public SupermarketFileManager() {
        String currentDate = getCurrentMonth();
        this.filePattern = "supermarket_" + currentDate + "-\\d{1,2}\\.csv";
    }

    private String getCurrentMonth() {
        return dateFormat.format(new Date());
    }

    private String getFilePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        return "src/main/resources/CSVFiles/supermarket_" + currentDate + ".csv";
    }

    public void writeToCSV(final List<Supermarket> supermarkets, final String filePath) {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(Supermarket.getHeaders() + "\n");
            for (Supermarket supermarket : supermarkets) {
                writer.write(supermarket.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Supermarket> load() {
        List<Supermarket> supermarkets = new ArrayList<>();
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
                    if (values.length == 7) {
                        Supermarket supermarket = createSupermarketFromCSV(values);
                        supermarkets.add(supermarket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return supermarkets;
    }

    private Supermarket createSupermarketFromCSV(final String[] values) {
        Supermarket supermarket = new Supermarket();
        try {
            supermarket.setId(Integer.parseInt(values[0].trim()));
            supermarket.setAddress(values[1].trim());
            supermarket.setArea(Double.parseDouble(values[2].trim()));
            supermarket.setRetailChain(values[3].trim());
            supermarket.setOpeningHours(values[4].trim());
            supermarket.setAverageVisitors(Integer.parseInt(values[5].trim()));
            supermarket.setPanelId(Integer.parseInt(values[6].trim()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return supermarket;
    }

    public File[] getMatchingFiles(final String pathToFiles) {
        File directory = new File(pathToFiles);
        return directory.listFiles((dir, name) -> name.matches(filePattern));
    }
}
