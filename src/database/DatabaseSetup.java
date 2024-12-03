package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void setupDatabase() {
        String websiteTable = """
            CREATE TABLE IF NOT EXISTS website (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                website_name TEXT NOT NULL,
                download_start_date_time TEXT NOT NULL,
                download_end_date_time TEXT NOT NULL,
                total_elapsed_time INTEGER NOT NULL,
                total_downloaded_kilobytes INTEGER NOT NULL
            );
        """;

        String linkTable = """
            CREATE TABLE IF NOT EXISTS link (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                link_name TEXT NOT NULL,
                website_id INTEGER NOT NULL,
                total_elapsed_time INTEGER NOT NULL,
                total_downloaded_kilobytes INTEGER NOT NULL,
                FOREIGN KEY (website_id) REFERENCES website (id)
            );
        """;

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(websiteTable);
            statement.execute(linkTable);

            System.out.println("Database setup completed.");
        } catch (Exception e) {
            System.err.println("Database setup failed: " + e.getMessage());
        }
    }
}
