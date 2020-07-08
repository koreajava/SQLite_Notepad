package com.tf.myapplication.vo;

/**
 * Created by 82108 on 2020-06-15.
 */
public class NoteVo {

    String title;
    String content;
    String date;
    int idx;


    public NoteVo(String title, String content, String date, int idx) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.idx = idx;
    }

    public NoteVo() {
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
