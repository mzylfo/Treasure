package com.example.treasure.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    public static String formatDate(String date) {
        // Definisci il formato della data in ingresso
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); // Modifica se necessario in base al formato che ricevi dall'API
        // Definisci il formato della data in uscita
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        try {
            Date parsedDate = inputFormat.parse(date); // Parsea la data in ingresso
            return outputFormat.format(parsedDate); // Ritorna la data nel formato desiderato
        } catch (Exception e) {
            return date; // Se c'è un errore, ritorna la data originale
        }
    }
}