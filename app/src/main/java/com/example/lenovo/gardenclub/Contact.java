package com.example.lenovo.gardenclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Contact extends AppCompatActivity {
    Button sendEmail;
    TextView emailID;


    /////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        sendEmail = (Button) findViewById(R.id.button6);
        emailID = (TextView)findViewById(R.id.textView15);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = String.valueOf(emailID.getText());

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"swanandinaction@gmail.com"});
//                emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{"swanandinaction@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject: ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body: ");

                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Choose email client... "));


    }
}
        );}}