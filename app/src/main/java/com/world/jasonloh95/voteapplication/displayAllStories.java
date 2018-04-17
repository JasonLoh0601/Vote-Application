package com.world.jasonloh95.voteapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.world.jasonloh95.voteapplication.Data.topic;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class displayAllStories extends AppCompatActivity{

    //declare the value
    private static final String TAG = "Display";
    TextView title, actor, date,stories,vote;
    ImageView good, bad;
    String t,a,d,s,t1;
    int v,p,total;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    List <topic> topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call the activity_display_all_stories and get the element from the .xml
        setContentView(R.layout.activity_display_all_stories);

        Intent i = this.getIntent();

        //receive all the value from listViewAdapter and set it to particular String and int
        t1 = i.getExtras().getString("T");
         t = i.getExtras().getString("TITLE");
        a = i.getExtras().getString("ACTOR");
         d = i.getExtras().getString("DATE");
         v = i.getExtras().getInt("VOTE");
         s = i.getExtras().getString("TOPIC");
         p = i.getExtras().getInt("POSITION");
        total= i.getExtras().getInt("TOTAL");

        //check the value of p and total in Run platform whether the result come out with my expected result
         Log.i(TAG, String.valueOf(p));
        Log.i(TAG, String.valueOf(total));

        //to call and get the text view which in activity_display_all_stories
        title = (TextView) findViewById(R.id.title);
        actor = (TextView) findViewById(R.id.actor);
        date = (TextView) findViewById(R.id.date);
        stories = (TextView) findViewById(R.id.stories);
        vote = (TextView) findViewById(R.id.vote);
        good = (ImageView) findViewById(R.id.imageView);
        bad = (ImageView) findViewById(R.id.imageView2);



        //set all the value in the TextView to show the value in my User interface
        String voteFormat = Integer.toString(v);
        title.setText(t);
        actor.setText(a);
        date.setText(d);
        stories.setText(s);
        vote.setText(voteFormat);

        //connect to the firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //catch the data from firebase
        myRef = mFirebaseDatabase.getReference();

        //create a function when the user click the good image the vote's value will increase
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v++;
                //store the vote's value into firebase. t1 is present a place the value store.
                myRef.child("Topic").child(t1).child("vote").setValue(v);
                String voteFormat = Integer.toString(v);
                vote.setText(voteFormat);
            }
        });


        //create a function when the user click the bad image the vote's value will decrease
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v--;

                //store the vote's value into firebase. t1 is present a place the value store.
                myRef.child("Topic").child(t1).child("vote").setValue(v);

                String voteFormat = Integer.toString(v);
                //update the vote's value and show it to user interface.
                vote.setText(voteFormat);
            }
        });
    }
}
