package com.example.lenovo.gardenclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/19/2018.
 */

public class ContactAdapter extends ArrayAdapter{
    List list = new ArrayList();
    public ContactAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(Contacts object) {
        super.add(object);
        list.add(object);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout_1, parent, false);
            contactHolder = new ContactHolder();
            contactHolder.tx_name = row.findViewById(R.id.nameTV);
            contactHolder.tx_email = row.findViewById(R.id.tx_email);
            contactHolder.tx_mobile = row.findViewById(R.id.tx_mobile);
            contactHolder.tx_mbrstatus = row.findViewById(R.id.tv_mbrstatus);
            row.setTag(contactHolder);

        } else {
            contactHolder = (ContactHolder) row.getTag();
        }
        Contacts contact = (Contacts) this.getItem(position);
        contactHolder.tx_name.setText(contact.getName());
//        contactHolder.tx_email.setText(contact.getEmail());
//        contactHolder.tx_mobile.setText(contact.getMobile());
        contactHolder.tx_mbrstatus.setText(contact.getMbrStatus());
        return row;
    }

    static class ContactHolder {
        TextView tx_name, tx_email, tx_mobile, tx_mbrstatus;

    }
}
