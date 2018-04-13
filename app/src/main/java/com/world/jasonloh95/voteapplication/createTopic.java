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
import java.util.Calendar;
import java.util.Date;

public class createTopic extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private EditText userName;
    private EditText  topic1;
    private TextView word;
    private int count;
    private Button submit;
    private Date date, currentTime;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        userName = (EditText) findViewById(R.id.editText);
        topic1 = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.submit);
        word =(TextView) findViewById(R.id.wordCount);

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

        progressDialog= new ProgressDialog(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitToDataBase(view);
            }
        });


    }


    public void submitToDataBase(View v){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
         String userName1= userName.getText().toString();
         String topic2= topic1.getText().toString();
        currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(currentTime);

        Log.i(TAG, userName1+topic2+currentTime);
        if(!userName1.equals("") &&!topic2.equals("")){
            topic sentTopic = new topic(userName1,topic2,formattedDate);
            myRef.child("Topic").push().setValue(sentTopic);
            toastMessage("Submit successfully");
            userName.setText("");
            topic1.setText("");
            Intent i = new Intent(createTopic.this, MainActivity.class);
            startActivity(i);
        }else if (!userName1.equals("")){
            toastMessage("Please insert User Name.");
        }else if (!topic2.equals("")){
            toastMessage("Your post cannot be empty, please write something");
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
