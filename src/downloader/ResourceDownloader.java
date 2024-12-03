package downloader;

import models.DownloadReport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceDownloader {

    public List<DownloadReport> downloadResources(List<String> links, File directory) {
        List<DownloadReport> reports = new ArrayList<>();
        for (String link : links) {
            try {
                System.out.println("Downloading: " + link);
                long startTime = System.currentTimeMillis();
                String fileName = link.substring(link.lastIndexOf('/') + 1);
                File outputFile = new File(directory, fileName);
                FileDownloader.download(link, outputFile);
                long endTime = System.currentTimeMillis();
                reports.add(new DownloadReport(link, endTime - startTime, outputFile.length()));
            } catch (IOException e) {
                System.err.println("Failed to download: " + link + " - " + e.getMessage());
            }
        }
        return reports;
    }
}
