package com.example.note.RecyclerView;

import java.util.Date;

import io.realm.RealmObject;

public class ListNote extends RealmObject {
    private String topic;
    private String content;
    private int id;
    private String date;
    private String time;

    public ListNote(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public ListNote(String topic, String content, int id) {
        this.topic = topic;
        this.content = content;
        this.id = id;
    }

    public ListNote(String topic, String content, int id, String date, String time) {
        this.topic = topic;
        this.content = content;
        this.id = id;
        this.date = date;
        this.time = time;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListNote() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void coppyFromNoteToRealm(ListNote note){
        this.setId(note.getId());
        this.setTopic(note.getTopic());
        this.setContent(note.getContent());
        this.setDate(note.getDate());
        this.setTime(note.getTime());
    }
}
