package downloader;

import java.io.*;
import java.net.*;

public class FileDownloader {

    public static String download(String urlString, File outputFile) throws IOException {
        try (InputStream in = new URL(urlString).openStream();
             FileOutputStream out = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return outputFile.getAbsolutePath();
    }
}
