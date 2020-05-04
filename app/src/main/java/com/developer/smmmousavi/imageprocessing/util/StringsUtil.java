package com.developer.smmmousavi.imageprocessing.util;

public class StringsUtil {

    public static String getShortString(String longString) {
        String shortSting = longString.substring(0, 10);
        return shortSting.concat(" ...");
    }

}
