package com.world.jasonloh95.voteapplication.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.world.jasonloh95.voteapplication.Data.topic;
import com.world.jasonloh95.voteapplication.R;
import com.world.jasonloh95.voteapplication.list_View.listViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class allStoriesList extends AppCompatActivity {

    ListView listViewStories;
    DatabaseReference databaseStories;
    private Activity context;
    List<topic> stories;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //catch the data from firebase
        databaseStories = FirebaseDatabase.getInstance().getReference().child("Topic");

        //find the list view in list_view for showing the title
        listViewStories = (ListView) findViewById(R.id.listView);

        stories = new ArrayList<>();

    }

    public void onStart(){
        super.onStart();

        databaseStories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear the topic class
                stories.clear();

                //read the data from firebase and store it in topic class
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    topic a = postSnapshot.getValue(topic.class);

                    stories.add(a);
                }

                //i want to use this function to show all topic start from latest topic.
                Collections.reverse(stories);
                // send the list to listView Adapter for display the information
                listViewAdapter storiesAdapter = new listViewAdapter(allStoriesList.this,stories);

                //set the storiesAdapter into the listView.
                listViewStories.setAdapter(storiesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
