package com.example.lenovo.gardenclub;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    Button Login;
    String JSON_STRING, json_string;


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

                getJSON(v);
//                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
//                backgroundWorker.execute(type, username, password);

//                Intent intent = new Intent(MainActivity.this, ContactList.class);
//                startActivity(intent);
     //           Login.setText("test");

            }
        });
    }

    public void getJSON(View view) {
        new BackgroundTask().execute();
        parseJson(view);

    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {
        String json_url;
        private static final String TAG = "BackgroundTask";

        @Override
        protected String doInBackground(Void... voids) {
            try {
//                String username = params[1];
//                String password = params[2];
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://satoshi.cis.uncw.edu/~jbr5433/GardenClub/get_json_data.php";

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = findViewById(R.id.textView);
            textView.setText(result);
//            Log.d(TAG, "onPostExecute: s = " + s);
            json_string = result;

        }
    }

    public void parseJson(View view) {
        if (json_string == null) {
            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(this, ContactList.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }
    }



    public void OnLogin(View view) {

    }



}
