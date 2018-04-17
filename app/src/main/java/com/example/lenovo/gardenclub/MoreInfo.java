package com.example.lenovo.gardenclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MoreInfo extends AppCompatActivity {
    private static final String TAG = "MoreInfo";
    String userID, loginEmail, userEmail;
    int viewId_placeholder = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MoreInfo.class);
        intent.getExtras();
        userID = getIntent().getExtras().getString("user_id");
        loginEmail = getIntent().getExtras().getString("login_email").trim();
        userEmail = getIntent().getExtras().getString("user_email").trim();
        setContentView(R.layout.activity_more_info);
        getSupportActionBar().hide();

        JSONObject JO;

        String YTA = getIntent().getExtras().getString("YTA");
        String bio = getIntent().getExtras().getString("bio");

        final TextView tvYTA = findViewById(R.id.tv_yta);
        final TextView tvBio = findViewById(R.id.tv_bio);
        TextView tvBack = findViewById(R.id.tvBack);

        if (tvYTA != null) {
            tvYTA.setText(YTA);
        }

        if (tvBio != null) {
            tvBio.setText(bio);
        }

        final EditText editText;
        final AlertDialog alertDialog;
//                                    final AlertDialog alertDialog;
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(new ContextThemeWrapper(MoreInfo.this, R.style.myDialog));
        editText = new EditText(getApplicationContext());
        Log.d(TAG, "onCreate: loginEmail: " + loginEmail);
        Log.d(TAG, "onCreate: userEmail: " + userEmail);


        if (loginEmail.equals(userEmail)) {
            TextView tvEdit = findViewById(R.id.tvEdit);
            tvEdit.setText("Edit");
            final int tvYTAId = tvYTA.getId();
            final int tvBioId = tvBio.getId();
            tvEdit.setText("Edit");
            builder.setTitle("Edit Fields");
            builder.setView(editText);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (viewId_placeholder == tvBioId) {
                                tvBio.setText(editText.getText());
                            }
                            if (viewId_placeholder == tvYTAId) {
                                //<------------------
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });

            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Tap the fields you wish to change", Toast.LENGTH_LONG).show();
                }
            });

            alertDialog = builder.create();

            View.OnClickListener editFields = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int viewId = view.getId();
                    Log.d(TAG, "onClick: viewid: " + viewId);
                    Log.d(TAG, "onClick: tvBioId: " + tvBioId);

                    if (viewId == tvBioId) {
                        viewId_placeholder = viewId;
                        alertDialog.show();
                        editText.setText(tvBio.getText());
                    }
                    if (viewId == tvYTAId) {
                        viewId_placeholder = viewId;
                        Toast.makeText(getApplicationContext(), "You cannot edit your Member Status from this application", Toast.LENGTH_LONG).show();

                    }
                }
            };

            tvBio.setOnClickListener(editFields);
            tvYTA.setOnClickListener(editFields);
        }



        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
