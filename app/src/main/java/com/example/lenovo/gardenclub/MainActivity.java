package com.example.lenovo.gardenclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = (Button) findViewById(R.id.button);

        UsernameEt = (EditText)findViewById(R.id.et_login);
        PasswordEt = (EditText)findViewById(R.id.et_pass);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UsernameEt.getText().toString();
                String password = PasswordEt.getText().toString();
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                backgroundWorker.execute(type, username, password);

//                Intent intent = new Intent(MainActivity.this, ContactList.class);
//                startActivity(intent);
     //           Login.setText("test");

            }
        });
    }

    public void OnLogin(View view) {

    }

}
