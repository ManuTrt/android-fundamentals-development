package com.adg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        TextView tv = findViewById(R.id.actReceiveMsg_msgTextView);
        Intent creationIntent = getIntent();
        tv.setText(creationIntent.getStringExtra("message"));
    }
}