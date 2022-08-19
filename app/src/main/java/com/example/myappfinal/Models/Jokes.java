package com.example.myappfinal.Models;

public class Jokes {
    public String title;
    public String category;
    public String start;
    public String finish;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory(){ return category; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        return
                "Jokes{" +
                        "category = '" + category + '\'' +
                        ",title = '" + title + '\'' +
                        ",start = '" + start + '\'' +
                        ",finish = '" + finish + '\'' +
                        "}";
    }
}
