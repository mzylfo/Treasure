package com.example.treasure.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Date {
    @PrimaryKey
    public int uid;

    public String formatted;    //example: "2024-12-29 09:32:58"

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public Date(String formatted) {
        this.formatted = formatted;
    }
}
