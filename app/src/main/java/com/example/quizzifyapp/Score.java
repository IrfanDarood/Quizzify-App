package com.example.quizzifyapp;

public class Score {
    public String userid,quizid,roomid,subject,topic,totalmarks,marksobtained;
    public Score(){

    }

    public Score(String userid, String quizid, String subject, String topic, String totalmarks, String marksobtained, String roomid) {
        this.userid = userid;
        this.quizid = quizid;
        this.subject = subject;
        this.topic = topic;
        this.totalmarks = totalmarks;
        this.marksobtained = marksobtained;
        this.roomid=roomid;
    }
}
