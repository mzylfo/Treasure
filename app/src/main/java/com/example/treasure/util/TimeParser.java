package com.example.treasure.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeParser {
    public static String formatTime(String time) {
        // Definisci il formato della data in ingresso
        SimpleDateFormat inputFormat = new SimpleDateFormat("H:mm", Locale.getDefault()); // Modifica se necessario in base al formato che ricevi dall'API
        // Definisci il formato della data in uscita
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            Date parsedDate = inputFormat.parse(time); // Parsea la data in ingresso
            return outputFormat.format(parsedDate); // Ritorna la data nel formato desiderato
        } catch (Exception e) {
            return time; // Se c'è un errore, ritorna la data originale
        }
    }
}