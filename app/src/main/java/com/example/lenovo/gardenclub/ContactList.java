package com.example.lenovo.gardenclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


public class ContactList extends AppCompatActivity{


    ListView lst;
    String[] names = {"A", "B","C","D","E","F"};
    Integer[] imgid= {R.drawable.image,R.drawable.img,R.drawable.image,R.drawable.image,R.drawable.img,R.drawable.image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);


        lst = findViewById(R.id.ListView);
        CustomListView customListView = new CustomListView(this, names, imgid);
        lst.setAdapter(customListView);




    }
}
