package com.example.treasure.util;


public class FeelingUtils {
    public static String getFeelingString(int feeling) {
        switch (feeling) {
            case 1:
                return "Happy";
            case 0:
                return "Neutral";
            case -1:
                return "Sad";
            default:
                return "Null";
        }
    }
}
