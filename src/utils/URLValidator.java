package utils;

import java.util.regex.*;

public class URLValidator {

    public static boolean isValidURL(String url) {
        String regex = "^(http|https)://.*$";
        return Pattern.matches(regex, url);
    }
}
