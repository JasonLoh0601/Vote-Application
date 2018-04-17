package com.world.jasonloh95.voteapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.world.jasonloh95.voteapplication.Data.topic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//this class use for creating new topic by the user.
public class createTopic extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private EditText userName, title;
    private EditText  topic1;
    private TextView word;
    private int count;
    private Button submit;
    private Date date, currentTime;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,databaseStories;
    private ProgressDialog progressDialog;

    List<topic> stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        //catch the element from activity_create_topic.
        userName = (EditText) findViewById(R.id.editText);
        title = (EditText) findViewById(R.id.editText2);
        topic1 = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.submit);
        word =(TextView) findViewById(R.id.wordCount);

        //catch the data from firebase and which is in Topic area.
        databaseStories = FirebaseDatabase.getInstance().getReference().child("Topic");
        stories = new ArrayList<>();

        /*this function is use for changing the value of the word count and show it to user for remaining.
           the require set the limit of word is 255 so the user is not able to write the character more than 255
        */
        topic1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                count = topic1.length();
                String convert = String.valueOf(count);
                word.setText(convert + "/255");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //when the user click the submit button then the system will run submitToDataBase.
        progressDialog= new ProgressDialog(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitToDataBase(view);
            }
        });


    }

    public void onStart(){
        super.onStart();

        //receive the data from the firebase and store it into topic class.
        databaseStories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear all data in topic class before receive and store the data into topic class
                stories.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    topic a = postSnapshot.getValue(topic.class);

                    stories.add(a);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void submitToDataBase(View v){
        //connect to the firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //get the data from the firebase
        myRef = mFirebaseDatabase.getReference();
         String userName1= userName.getText().toString();
         String topic2= topic1.getText().toString();
         String title1 = title.getText().toString();
         String t="Topic" + (stories.size()+1);
         //get the current date
        currentTime = Calendar.getInstance().getTime();
        //set the format what i want
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(currentTime);

        Log.i(TAG, userName1+topic2+currentTime);
        //consider the condition when the userName1 and topic2 not equal empty
        if(!userName1.equals("") &&!topic2.equals("")){
            //store the data into topic class
            topic sentTopic = new topic(t,title1,userName1,topic2,formattedDate);
            //store the data into firebase
            myRef.child("Topic").child("Topic" + (stories.size()+1)).setValue(sentTopic);
            toastMessage("Submit successfully");
            //reset the edit Text.
            userName.setText("");
            topic1.setText("");

            //go back to Main Activity(Home page) after store successfull
            Intent i = new Intent(createTopic.this, MainActivity.class);
            startActivity(i);
            //other condition when the user never insert data in userName1
        }else if (!userName1.equals("")){
            toastMessage("Please insert User Name.");

            //other condition when the user never insert data in topic2
        }else if (!topic2.equals("")){
            toastMessage("Your post cannot be empty, please write something");

            //other condition when the user never insert data in create topic page1
        }else{
            toastMessage("Please Fill out all the fields");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
