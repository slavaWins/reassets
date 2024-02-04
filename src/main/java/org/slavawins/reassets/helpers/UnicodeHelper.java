package org.slavawins.reassets.helpers;

public class UnicodeHelper {

    public static int convertToInteger(String unicodeString) {
        String hexString = unicodeString.substring(2); // Удаляем символы "\ us "
        if (hexString.startsWith("\\")) hexString = hexString.substring(1);
        int intValue = Integer.parseInt(hexString, 16); // Преобразуем шестнадцатеричное значение в int

        return intValue;
    }

    public static String convertToUnicode(int intValue) {
        String hexString = Integer.toHexString(intValue); // Преобразуем значение в шестнадцатеричную строку
        String unicodeString = "\\u" + hexString; // Добавляем символы "\ u" к шестнадцатеричной строке



        return unicodeString;
    }

    public static char ToSymbol(String unicodeString) {

        String convertedString = unicodeString.replace("\\u", "");

        if (convertedString.startsWith("\\")) convertedString = convertedString.substring(1);

        int unicodeValue = Integer.parseInt(convertedString, 16);

        char unicodeChar = (char) unicodeValue;
        return unicodeChar;

    }

    public static String GenIteration(int i) {
        String code = "\\uc24e";
        int val = 49742;  // \uc26c  \\uc26c \\uc26c
        val += i;
        return convertToUnicode(val);

    }
}
