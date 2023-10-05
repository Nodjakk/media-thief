package works.akus.mediathief.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    public static String getDomain(String url){
        Pattern pattern = Pattern.compile("^(https?://)?(?:www\\.)?([^/]+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(2);
        } else {
            return null;
        }
    }

    public static boolean isURL(String input) {
        String urlPattern = "^(https?|ftp)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static Map<String, String> getParams(String url){
        Map<String, String> params = new HashMap<>();
        Pattern pattern = Pattern.compile("\\?([^#]*)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String paramString = matcher.group(1);
            String[] paramPairs = paramString.split("&");

            for (String paramPair : paramPairs) {
                String[] keyValue = paramPair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    params.put(key, value);
                }
            }
        }

        return params;
    }

    public static String getLastPath(String urlString) {
        Pattern pattern = Pattern.compile("/([^/?#]+)(?:\\?|#|$)");
        Matcher matcher = pattern.matcher(urlString);

        String lastPath = null;

        while (matcher.find()) {
            lastPath = matcher.group(1);
        }

        return lastPath;
    }



}
