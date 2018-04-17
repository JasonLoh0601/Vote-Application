package com.world.jasonloh95.voteapplication.list_View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.world.jasonloh95.voteapplication.Data.topic;
import com.world.jasonloh95.voteapplication.R;
import com.world.jasonloh95.voteapplication.displayAllStories;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class listViewAdapter extends ArrayAdapter<topic> {

    private static final String TAG = "All Stories ";
    LayoutInflater inflater;
    private Activity context;
    List<topic> stories;

    //receive the data from other class
    public listViewAdapter(Activity context, List<topic> stories){
        super(context, R.layout.all_stories, stories);
        this.context = context;
        this.stories = stories;
    }

    //this function will set the data into user interface and show to user.
    public View getView (final int position, View convertView, ViewGroup parent){

        //call the xml which is all_stories for giving element.
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView= inflater.inflate(R.layout.all_stories,parent,false);
        }

        //catch the element from the all_stories and set it into this class for getting value to show to user.
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView actor = (TextView) convertView.findViewById(R.id.actor);
        TextView date = (TextView) convertView.findViewById(R.id.time);
        final TextView vote = (TextView) convertView.findViewById(R.id.vote);

            //get the value from topic class and set it into String and int.
            Log.i(TAG, String.valueOf(position));
            final String t = stories.get(position).getT();
            final String title1 = stories.get(position).getTitle();
            final String actor1 = stories.get(position).getUserName();
            final String date1 = stories.get(position).getDate();
            final int vote1 = stories.get(position).getVote();
            final String topic = stories.get(position).getTopic();

            final int total = stories.size();
            final String voteFormat = Integer.toString(vote1);
            Log.i(TAG, voteFormat);
            //set the String or int into the xml element
            title.setText(title1);
            actor.setText(actor1);
            date.setText(date1);
            vote.setText(voteFormat);

            //the function will be call when the user click the list view.
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    openTheStories(t, title1, actor1, date1, vote1, topic, position, total);
                }
            });
        return convertView;
    }

    private void openTheStories(String t,String title, String actor, String date,int voteFormat,String topic,int p,int total){
        Intent i = new Intent(context, displayAllStories.class);

        //pack the value into a package and send it to displatAllStories class.
        i.putExtra("T",t);
        i.putExtra("TITLE",title);
        i.putExtra("ACTOR",actor);
        i.putExtra("DATE",date);
        i.putExtra("VOTE",voteFormat);
        i.putExtra("TOPIC",topic);
        i.putExtra("POSITION",p);
        i.putExtra("TOTAL",total);

        context.startActivity(i);
    }
}
