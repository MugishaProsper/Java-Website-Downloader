import downloader.*;
import database.DatabaseSetup;
import database.ReportSaver;
import models.DownloadReport;
import utils.URLValidator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Initialize database and create tables
            System.out.println("Setting up the database...");
            DatabaseSetup.setupDatabase();

            // Step 2: Prompt user for website URL
            System.out.print("Enter the URL of the website: ");
            String url = scanner.nextLine();

            // Step 3: Validate the URL
            if (!URLValidator.isValidURL(url)) {
                throw new IllegalArgumentException("Invalid URL format.");
            }

            // Step 4: Set up directories and start downloading
            LocalDateTime startTime = LocalDateTime.now();
            String domainName = new File(new java.net.URL(url).getHost()).getName();
            File downloadDirectory = new File(domainName);
            if (!downloadDirectory.exists() && !downloadDirectory.mkdir()) {
                throw new IOException("Could not create directory for the website.");
            }

            System.out.println("Downloading homepage...");
            String homepagePath = FileDownloader.download(url, new File(downloadDirectory, "index.html"));

            // Step 5: Extract external links
            System.out.println("Extracting external links...");
            List<String> links = LinkExtractor.extractLinks(homepagePath);

            // Step 6: Download external resources
            System.out.println("Downloading external resources...");
            ResourceDownloader resourceDownloader = new ResourceDownloader();
            List<DownloadReport> reports = resourceDownloader.downloadResources(links, downloadDirectory);

            // Step 7: Save the download report to the database
            LocalDateTime endTime = LocalDateTime.now();
            ReportSaver.save(domainName, startTime, endTime, reports);

            System.out.println("Download completed. Reports saved to database.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
