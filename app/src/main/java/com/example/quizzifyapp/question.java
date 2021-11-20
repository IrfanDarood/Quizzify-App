package com.example.quizzifyapp;


public class question {
    public String question_no, question, option_crt, option_incrt1, option_incrt2, option_incrt3, subject,Topic,quizid;
    public question(){

    }
    public question(String question_no,String question, String option_crt, String option_incrt1, String option_incrt2, String option_incrt3,
                    String subject, String Topic, String quizid){
        this.question=question;
        this.question_no=question_no;
        this.option_crt=option_crt;
        this.option_incrt1=option_incrt1;
        this.option_incrt2=option_incrt2;
        this.option_incrt3=option_incrt3;
        this.subject=subject;
        this.quizid=quizid;
        this.Topic=Topic;


    }


}
