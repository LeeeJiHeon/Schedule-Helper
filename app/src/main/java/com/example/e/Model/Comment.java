package com.example.e.Model;

public class Comment {
    private String comment_no;
    private String bbs_no;
    private String user_id;
    private String content;
    private String date;
    private String mayjor;

    public Comment(String comment_no, String bbs_no, String user_id, String content, String date){
        this.comment_no     = comment_no;
        this.bbs_no         = bbs_no;
        this.user_id        = user_id;
        this.content        = content;
        this.date           = date;
    }

    public void setMayjor(String mayjor) {
        this.mayjor = mayjor;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBbs_no(String bbs_no) {
        this.bbs_no = bbs_no;
    }

    public void setComment_no(String comment_no) {
        this.comment_no = comment_no;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getBbs_no() {
        return bbs_no;
    }

    public String getComment_no() {
        return comment_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMayjor() {
        return mayjor;
    }
}
