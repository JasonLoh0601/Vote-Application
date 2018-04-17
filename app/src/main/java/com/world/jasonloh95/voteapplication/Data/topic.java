package com.world.jasonloh95.voteapplication.Data;


import android.support.annotation.NonNull;

//this class use to store all topic data.
public class topic implements Comparable {
    String title;
    String topic;
    String userName;
    String date;
    String t;
    int vote;

    public topic(){

    }

    public topic (String t,String title, String userName,String topic, String date){
        this.t=t;
        this.title = title;
        this.topic = topic;
        this.userName = userName;
        this.date = date;
        this.vote= 0;
    }

    public topic (String t,String title, String userName, String topic, String date, int vote){
        this.t=t;
        this.title = title;
        this.topic = topic;
        this.userName = userName;
        this.date = date;
        this.vote = vote;
    }

    public void setT (String t){
        this.t = t;
    }
    public String getT (){
        return t;
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



    @Override
    public int compareTo(@NonNull Object o) {
        int compareVote = ((topic)o).getVote();

        return this.vote-compareVote;
    }
}
