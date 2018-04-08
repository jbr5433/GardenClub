package com.example.lenovo.gardenclub;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;



import org.w3c.dom.Text;

/**
 * Created by Joe on 4/8/2018.
 */

public class ContactAdapterTEst extends CursorAdapter{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SearchView searchView;

    public ContactAdapterTEst(Context context, Cursor cursor, SearchView sv) {
        super(context, cursor, false);
        mContext = context;
        searchView = sv;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.row_layout_1, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex(
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
        String phone = cursor.getString(cursor.getColumnIndex(
                ContactsContract.Contacts.HAS_PHONE_NUMBER));

        String hasPhone = "Has Phone Number";
        if(phone.equals(0)){
            hasPhone = "Without Phone Number";
        }

        TextView nameTv =  view.findViewById(R.id.nameTV);
        nameTv.setText(name);

        TextView mbrStatus = view.findViewById(R.id.tv_mbrstatus);
        mbrStatus.setText(hasPhone);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView pName = (TextView) view.findViewById(R.id.nameTV);
                searchView.setIconified(true);
                //get contact details and display
                Toast.makeText(context, "Selected Contact "+pName.getText(),
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
