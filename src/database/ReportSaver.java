package database;

import models.DownloadReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

public class ReportSaver {

    public static void save(String websiteName, LocalDateTime startTime, LocalDateTime endTime, List<DownloadReport> reports) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String websiteQuery = """
                INSERT INTO website 
                (website_name, download_start_date_time, download_end_date_time, total_elapsed_time, total_downloaded_kilobytes) 
                VALUES (?, ?, ?, ?, ?)
            """;
            try (PreparedStatement websiteStmt = connection.prepareStatement(websiteQuery, Statement.RETURN_GENERATED_KEYS)) {
                websiteStmt.setString(1, websiteName);
                websiteStmt.setString(2, startTime.toString());
                websiteStmt.setString(3, endTime.toString());
                websiteStmt.setLong(4, java.time.Duration.between(startTime, endTime).toMillis());
                websiteStmt.setLong(5, reports.stream().mapToLong(DownloadReport::getKilobytes).sum());
                websiteStmt.executeUpdate();

                ResultSet rs = websiteStmt.getGeneratedKeys();
                if (rs.next()) {
                    int websiteId = rs.getInt(1);

                    String linkQuery = """
                        INSERT INTO link 
                        (link_name, website_id, total_elapsed_time, total_downloaded_kilobytes) 
                        VALUES (?, ?, ?, ?)
                    """;
                    try (PreparedStatement linkStmt = connection.prepareStatement(linkQuery)) {
                        for (DownloadReport report : reports) {
                            linkStmt.setString(1, report.getLinkName());
                            linkStmt.setInt(2, websiteId);
                            linkStmt.setLong(3, report.getElapsedTime());
                            linkStmt.setLong(4, report.getKilobytes());
                            linkStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
