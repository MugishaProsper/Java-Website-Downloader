package downloader;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.*;
import java.util.*;

public class LinkExtractor {

    public static List<String> extractLinks(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        Document doc = Jsoup.parse(new File(filePath), "UTF-8");
        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            String link = element.attr("abs:href");
            if (!link.isEmpty()) {
                links.add(link);
            }
        }
        return links;
    }
}
