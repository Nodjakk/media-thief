package works.akus.mediathief.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class TextUtils {


    public static String transliterate(String input){

        String[][] mapping = {
                {"а", "a"}, {"б", "b"}, {"в", "v"}, {"г", "g"}, {"д", "d"}, {"е", "e"}, {"ё", "e"}, {"ж", "zh"},
                {"з", "z"}, {"и", "i"}, {"й", "y"}, {"к", "k"}, {"л", "l"}, {"м", "m"}, {"н", "n"}, {"о", "o"},
                {"п", "p"}, {"р", "r"}, {"с", "s"}, {"т", "t"}, {"у", "u"}, {"ф", "f"}, {"х", "kh"}, {"ц", "ts"},
                {"ч", "ch"}, {"ш", "sh"}, {"щ", "shch"}, {"ъ", ""}, {"ы", "y"}, {"ь", ""}, {"э", "e"}, {"ю", "yu"},
                {"я", "ya"}, {"А", "A"}, {"Б", "B"}, {"В", "V"}, {"Г", "G"}, {"Д", "D"}, {"Е", "E"}, {"Ё", "E"},
                {"Ж", "Zh"}, {"З", "Z"}, {"И", "I"}, {"Й", "Y"}, {"К", "K"}, {"Л", "L"}, {"М", "M"}, {"Н", "N"},
                {"О", "O"}, {"П", "P"}, {"Р", "R"}, {"С", "S"}, {"Т", "T"}, {"У", "U"}, {"Ф", "F"}, {"Х", "Kh"},
                {"Ц", "Ts"}, {"Ч", "Ch"}, {"Ш", "Sh"}, {"Щ", "Shch"}, {"Ъ", ""}, {"Ы", "Y"}, {"Ь", ""}, {"Э", "E"},
                {"Ю", "Yu"}, {"Я", "Ya"}, {" ", " "}, {"-", "-"}
        };

        for (String[] pair : mapping) {
            input = input.replace(pair[0], pair[1]);
        }

        String normalizedText = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("[^\\p{ASCII}a-zA-Z0-9._-]+");

        String cleanText = pattern.matcher(normalizedText)
                .replaceAll("")
                .replaceAll("\\s+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_+", "")
                .replaceAll("_+$", "");

        return cleanText;
    }

}
