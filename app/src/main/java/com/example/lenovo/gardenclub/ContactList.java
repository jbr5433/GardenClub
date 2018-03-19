package com.example.lenovo.gardenclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

        json_string = getIntent().getExtras().getString("json_data");
        try {
            mJSONObject = new JSONObject(json_string);
            mJSONArray = mJSONObject.getJSONArray("server_response");

            int count = 0;
            String name, email, mobile;

            while(count <= mJSONArray.length()) {
                JSONObject JO = mJSONArray.getJSONObject(count);
                name = JO.getString("lastName");
                email = JO.getString("email");
                mobile = JO.getString("mobile");

                Contacts contact = new Contacts(name, email, mobile);
                Log.d(TAG, "onCreate: contact = " + contact);
                mContactAdapter.add(contact);

                count++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
