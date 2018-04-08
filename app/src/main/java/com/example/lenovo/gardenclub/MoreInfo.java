package com.example.lenovo.gardenclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class MoreInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        getSupportActionBar().hide();

        JSONObject JO;

        String YTA = getIntent().getExtras().getString("YTA");
        String bio = getIntent().getExtras().getString("bio");

        TextView tvYTA = (TextView) findViewById(R.id.tv_yta);
        TextView tvBio = (TextView) findViewById(R.id.tv_bio);
        TextView tvBack = (TextView) findViewById(R.id.tvBack);

        if (tvYTA != null) {
            tvYTA.setText(YTA);
        }

        if (tvBio != null) {
            tvBio.setText(bio);
        }

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        String json_string = getIntent().getExtras().getString("json_data");

    }
}
