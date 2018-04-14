package com.world.jasonloh95.voteapplication.list_View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.world.jasonloh95.voteapplication.Data.topic;
import com.world.jasonloh95.voteapplication.R;
import com.world.jasonloh95.voteapplication.displayAllStories;

import java.util.List;

public class listViewAdapter extends ArrayAdapter<topic> {

    LayoutInflater inflater;
    private Activity context;
    List<topic> stories;

    public listViewAdapter(Activity context, List<topic> stories){
        super(context, R.layout.all_stories, stories);
        this.context = context;
        this.stories = stories;
    }

    public View getView (int position, View convertView, ViewGroup parent){

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView= inflater.inflate(R.layout.all_stories,parent,false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView actor = (TextView) convertView.findViewById(R.id.actor);
        TextView date = (TextView) convertView.findViewById(R.id.time);
        TextView vote = (TextView) convertView.findViewById(R.id.vote);

        final String title1 = stories.get(position).getTitle();
        final String actor1 = stories.get(position).getUserName();
        final String date1 = stories.get(position).getDate();
        final int vote1 = stories.get(position).getVote();
        final String topic = stories.get(position).getTopic();

        final String voteFormat = Integer.toString(vote1);
        title.setText(title1);
        actor.setText(actor1);
        date.setText(date1);
        vote.setText(voteFormat);

        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openTheStories(title1,actor1,date1,voteFormat,topic);
            }
        });
        return convertView;
    }

    private void openTheStories(String title, String actor, String date,String voteFormat,String topic){
        Intent i = new Intent(context, displayAllStories.class);

        i.putExtra("TITLE",title);
        i.putExtra("ACTOR",actor);
        i.putExtra("DATE",date);
        i.putExtra("VOTE",voteFormat);
        i.putExtra("TOPIC",topic);

        context.startActivity(i);
    }
}
