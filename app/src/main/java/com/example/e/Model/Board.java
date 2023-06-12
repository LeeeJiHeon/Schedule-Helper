package com.example.e.Model;

import java.io.Serializable;

public class Board implements Serializable {
    private String no;
    private String writer;
    private String title;
    private String content;
    private String date;
    private String mayjor;
    private String like_count = "0";


    public Board(String no, String writer, String title, String content, String date){
        this.no = no;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public void setMayjor(String mayjor) {
        this.mayjor = mayjor;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getNo() {
        return no;
    }

    public String getWriter() {
        return writer;
    }

    public String getMayjor() {
        return mayjor;
    }

    public String getLike_count() {
        return like_count;
    }
}
