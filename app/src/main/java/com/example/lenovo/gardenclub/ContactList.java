package com.example.lenovo.gardenclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.lenovo.gardenclub.MainActivity.JSON_STRING;


public class ContactList extends AppCompatActivity{
    private static final String TAG = "ContactList";
    String json_string;
    JSONObject mJSONObject;
    JSONArray mJSONArray;
    ContactAdapter mContactAdapter;

    ListView lst;
//    String[] names = {"A", "B","C","D","E","F"};
//    Integer[] imgid= {R.drawable.image,R.drawable.img,R.drawable.image,R.drawable.image,R.drawable.img,R.drawable.image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        mContactAdapter = new ContactAdapter(this, R.layout.row_layout);
        lst = findViewById(R.id.ListView);
//        CustomListView customListView = new CustomListView(this, names, imgid);
        lst.setAdapter(mContactAdapter);
        JSONObject JO;

        json_string = getIntent().getExtras().getString("json_data");
        try {


            mJSONObject = new JSONObject(json_string);
            mJSONArray = mJSONObject.getJSONArray("server_response");

            int count = 0;
            String name, email, mobile, mbrStatus;

            while(count <= mJSONArray.length()) {
                JO = mJSONArray.getJSONObject(count);
                name = JO.getString("firstName").concat(" " + JO.getString("lastName"));
                email = JO.getString("email");
                mobile = JO.getString("mobile");
                mbrStatus = JO.getString("mbrStatus");

                Contacts contact = new Contacts(name, email, mobile, mbrStatus);
                Log.d(TAG, "onCreate: contact = " + contact);
                mContactAdapter.add(contact);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Intent intent = new Intent(this, Contact.class);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Toast.makeText(getApplicationContext(), String.valueOf(mJSONArray.getJSONObject(i)), Toast.LENGTH_SHORT).show();
                    String json = mJSONArray.getJSONObject(i).toString();
                    intent.putExtra("json_object", json);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });

    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {
        String json_url;
        private static final String TAG = "BackgroundTask";

        @Override
        protected String doInBackground(Void... voids) {
            try {
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

}
