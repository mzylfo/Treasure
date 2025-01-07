package com.example.treasure.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Feeling {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int face; //faccina scelta
    private String text; //testo inserito --> -1 is sad, 0 is neutral, 1 is happy
    private String date; //data dell'inserimento
    private String time; //ora dell'inserimento

    public Feeling(String text, int face, String date, String time) {
        this.text = text;
        this.face = face;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
