package RLE;
import java.util.regex.Pattern;

class StringUtilities {
    static boolean isNumber(char stroke) {
        return (Pattern.compile("\\d").matcher(Character.toString(stroke)).matches());
    }
    static boolean isNumber(String stroke) {
        return (Pattern.compile("\\d").matcher(stroke).matches());
    }
}
