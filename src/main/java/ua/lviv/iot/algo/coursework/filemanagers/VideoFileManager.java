package ua.lviv.iot.algo.coursework.filemanagers;

import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.coursework.models.AdvertisingVideo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class VideoFileManager {
    private final String filePattern;
    private final SimpleDateFormat dateFormat;
    public final String filePath;
    public static final String PATH_TO_FILES = "src/main/resources/CSVFiles";


    public VideoFileManager() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM");
        String currentDate = getCurrentMonth();
        this.filePattern = "advertising_video_" + currentDate + "-\\d{2}\\.csv";
        this.filePath = getFilePath();
    }

    private String getCurrentMonth() {
        return dateFormat.format(new Date());
    }


    private String getFilePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        return "src/main/resources/CSVFiles/advertising_video_" + currentDate + ".csv";
    }


    public void writeToCSV(final List<AdvertisingVideo> videos, final String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write(AdvertisingVideo.getHeaders() + "\n");
            for (AdvertisingVideo video : videos) {
                writer.write(video.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AdvertisingVideo> load() {
        List<AdvertisingVideo> videos = new ArrayList<>();
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
                    if (values.length == 4) {
                        AdvertisingVideo video = createVideoFromCSV(values);
                        videos.add(video);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return videos;
    }

    private AdvertisingVideo createVideoFromCSV(final String[] values) {
        AdvertisingVideo video = new AdvertisingVideo();
        try {
            video.setId(Integer.parseInt(values[0].trim()));
            video.setManufacturer(values[1].trim());
            video.setBrand(values[2].trim());
            video.setVideoDuration(Integer.parseInt(values[3].trim()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return video;
    }

    public File[] getMatchingFiles(final String pathToFiles) {
        File directory = new File(pathToFiles);
        return directory.listFiles((dir, name) -> name.matches(filePattern));
    }
}
