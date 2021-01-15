package ch.bbcah.app;

import java.util.Arrays;

public class StringExtensions {
    public static void main(String[] args) {
        System.out.println(StringExtensions.reverse("HALLO WELT"));
    }
    public static String reverse(String text) {
        var chars = text.split("");
        var result = new String[chars.length];
        var k = 0;
        for(var i = chars.length - 1; i >= 0; i--) {
            result[k] = chars[i];
            k++;
        }

        return String.join("", result);
    }
}
