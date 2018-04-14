package com.world.jasonloh95.voteapplication.Data;

import java.util.Date;

public class topic {
    String title;
    String topic;
    String userName;
    String date;
    int vote;

    public topic(){

    }

    public topic (String title, String userName,String topic, String date){
        this.title = title;
        this.topic = topic;
        this.userName = userName;
        this.date = date;
        this.vote= 0;
    }

    public topic (String title, String userName, String topic, String date, int vote){
        this.title = title;
        this.topic = topic;
        this.userName = userName;
        this.date = date;
        this.vote = vote;
    }

    public void setTitle (String title){
        this.title = title;
    }
    public String getTitle (){
        return title;
    }

    public void setTopic (String topic){
        this.topic = topic;
    }
    public String getTopic (){
        return topic;
    }

    public void setUserName (String userName){
        this.userName = userName;
    }

    public String getUserName (){
        return userName;
    }

    public void setDate (String date){
        this.date = date;
    }

    public String getDate (){
        return date;
    }

    public void setVote (int vote){
        this.vote = vote;
    }

    public int getVote (){
        return vote;
    }
}
