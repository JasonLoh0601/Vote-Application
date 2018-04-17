package com.world.jasonloh95.voteapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.world.jasonloh95.voteapplication.Data.topic;
import com.world.jasonloh95.voteapplication.List.allStoriesList;
import com.world.jasonloh95.voteapplication.list_View.listViewAdapter;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

// the first page or class the application run and show.
public class MainActivity extends AppCompatActivity {

    ListView listViewStories;
    DatabaseReference databaseStories;
    private Activity context;
    List<topic> stories;
    List<topic> subStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //receive data from firebase "Topic"
        databaseStories = FirebaseDatabase.getInstance().getReference().child("Topic");

        //get the listView element from activity_main
        listViewStories = (ListView) findViewById(R.id.listView);

        //create a new Arraylist
        stories = new ArrayList<>();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // user can click this fab to go to the create Topic page for creating a new topic
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create = new Intent(MainActivity.this, createTopic.class);
                MainActivity.this.startActivity(create);
            }
        });

        // fab1 is link to the allStoriesList page to show all Stories in firebase when the user click it.
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.allStories);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create = new Intent(MainActivity.this, allStoriesList.class);
                MainActivity.this.startActivity(create);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onStart(){
        super.onStart();

        //get the all data from the firebase.
        databaseStories.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "Check" ;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //make sure all data in topic class have been remove before the new data store in.
                stories.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    topic a = postSnapshot.getValue(topic.class);

                    stories.add(a);
                }

                //sort the list and reverse the list for getting the popular topic in application
                Collections.sort(stories);
                Collections.reverse(stories);

                //only top 20 topic can be show in this page
                subStories = stories.subList(0,min(stories.size(),20));

                Log.i(TAG, String.valueOf(stories));
                    //call listViewAdapter and get set the user interface in that class and combine together.
                    listViewAdapter storiesAdapter = new listViewAdapter(MainActivity.this,subStories);
                    listViewStories.setAdapter(storiesAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
