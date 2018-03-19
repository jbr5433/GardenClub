package com.example.lenovo.gardenclub;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.gardenclub.R;

/**
 * Created by lenovo on 18-02-2018.
 */

public class CustomListView extends ArrayAdapter{

    private String[] names;
    private Integer[] imgid;
    private Activity context;


    public CustomListView(Activity context, String[] names, Integer[] imgid) {
        super(context, R.layout.list_view_contacts_template, names);

        this.context = context;
        this.names = names;
        this.imgid = imgid;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.list_view_contacts_template, null, true);
            viewHolder = new ViewHolder(r);

            r.setTag(viewHolder);


        }
        else
        {
            viewHolder = (ViewHolder)r.getTag();

        }

        viewHolder.iv1.setImageResource(imgid[0]);
        viewHolder.tv1.setText(names[position]);


            return r;


    }
    class ViewHolder{

        TextView tv1;
        ImageView iv1;

        ViewHolder(View v)
        {
            tv1 = v.findViewById(R.id.textView17);
            iv1 = v.findViewById(R.id.imageView3);

        }




    }

}
