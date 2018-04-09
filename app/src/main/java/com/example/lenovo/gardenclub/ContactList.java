package com.example.lenovo.gardenclub;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.lenovo.gardenclub.MainActivity.JSON_STRING;




public class ContactList extends AppCompatActivity {
    private static final String TAG = "ContactList";
    String json_string;
    JSONObject mJSONObject;
    JSONArray mJSONArray;
    ContactAdapter mContactAdapter;
//    SearchView mSearchView = (SearchView) findViewById(R.id.searchView);

    ListView lst;
//    String[] names = {"A", "B","C","D","E","F"};
//    Integer[] imgid= {R.drawable.image,R.drawable.img,R.drawable.image,R.drawable.image,R.drawable.img,R.drawable.image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        JSONObject JO;
        mContactAdapter = new ContactAdapter(this, R.layout.row_layout);
        lst = findViewById(R.id.ListView);
//        CustomListView customListView = new CustomListView(this, names, imgid);

        lst.setAdapter(mContactAdapter);
        lst.setTextFilterEnabled(true);

        json_string = getIntent().getExtras().getString("json_data");
        Log.d(TAG, "onCreate: json_string from intent: " + json_string);
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
                mContactAdapter.add(contact);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        // Get the intent, verify the action and get the query
//        Intent searchIntent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
//            String query = searchIntent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
//        }

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.search_badge_ID);
        Log.d(TAG, "onCreateOptionsMenu: starts");

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mContactAdapter.getFilter().filter(s);
                Log.d(TAG, "onQueryTextChange: count: " + mContactAdapter.getCount());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
