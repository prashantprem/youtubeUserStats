package com.example.youtubeuserstats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.textInputEditText);
                str = text.getText().toString();
                if (str.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter channel name!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(infoIntent);
                }
            }
        });
    }


}