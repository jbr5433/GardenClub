package com.example.lenovo.gardenclub;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Contact extends AppCompatActivity {
    int viewId_placeholder = 0;
    Button btnEmail, btnCall, btnText;
    TextView emailID;
    JSONObject jsonObject;
    String json, JSON_STRING, json_string, type, userID;
    private static final String TAG = "Contact";
    String finalEmail = null;
    JSONObject JO;
    String SBString, loginEmail;
    StringBuilder str;
    String udFN, udLN, udSpouse, udAddress, udCAS, udZip, udPrim, udSec, udBio;


    public Contact() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Contact.class);
        intent.getExtras();
        setContentView(R.layout.activity_contact);
//        json = getIntent().getExtras().getString("json_object");
        userID = getIntent().getExtras().getString("user_id");
        loginEmail = getIntent().getExtras().getString("login_email");
        getSupportActionBar().hide();

//        try {
////            jsonObject = new JSONObject(json);
//
////            Toast.makeText(getApplicationContext(), jsonObject.getString("userID"), Toast.LENGTH_SHORT).show();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        //            userID = jsonObject.getString("userID");
        new BackgroundTask1().execute("get_info", userID);
    }

    public void getJSON(View view) {
        new BackgroundTask1().execute();
        parseJson(view);

    }

    class BackgroundTask1 extends AsyncTask<String, Void, String> {
        String json_post, json_get;
        private static final String TAG = "BackgroundTask";


        @Override
        protected String doInBackground(String... params) {
            type = params[0];
            StringBuilder stringBuilder = null;
            if (type.equals("post_info")) {
                try {
                    userID = params[1];
//                String password = params[2];
                    URL url = new URL(json_get);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    //CHANGE THIS: FirstName and LastName
                    userID = jsonObject.getString("ID");
                    String post_data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                    String result="";
//                    String line="";
//                    while((line = bufferedReader.readLine())!= null) {
//                        result += line;
//                    }

//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                writeToFile(inputStream, getApplicationContext());
                    stringBuilder = new StringBuilder();
                    while ((JSON_STRING = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();

//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (type.equals("get_info")) {
                try {
//                String password = params[2];
                    URL url = new URL(json_get);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                writeToFile(inputStream, getApplicationContext());
                    stringBuilder = new StringBuilder();
                    while ((JSON_STRING = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING + "\n");
//                        Log.d(TAG, "doInBackground: JSON_STRING: " + JSON_STRING["server_response"]);
                    }

                    JSONObject jsonObject = new JSONObject(String.valueOf(stringBuilder));
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                    String email;
                    String mbrStatus;
                    String photoID;
                    String yearTurnedActive;
                    String firstName;
                    String lastName;
                    String spouse;
                    String StreetAddress, Committee, CommitteeTitle, AzaleaGardenTourCommitteesTitle, AzaleaGardenTourCommittees;
                    String CityState, WorkNum, Officers;
                    String ZipCode, PrimaryContactNumber, SecondaryContactNumber, TypeofPrimaryContactNo, TypeofSecondaryContactNo, Office, OfficerTitle, ExecutiveBdMbrship;
                    String CurrentCmteAssignment1, CmteAssign1Chair, CmteAssign1CoChair, CurrentCmteAssignment2, CmteAssign2Chair, CmteAssign2CoChair;
                    String CurrentCmteAssignment3;
                    String CmteAssign3Chair;
                    String CmteAssign3CoChair;
                    String BiographicalInfo;

                    final TextView nameTV = (TextView) findViewById(R.id.nameTV);
                    final TextView mbrStatusTV = (TextView) findViewById(R.id.mbrStatusTV);
                    final TextView spouseTV = (TextView) findViewById(R.id.spouseTV);
                    final TextView addressTV = (TextView) findViewById(R.id.addressTV);
                    final TextView primaryContactTV = (TextView) findViewById(R.id.primaryContactTV);
                    final TextView secondaryContactTV = (TextView) findViewById(R.id.secondaryContactTV);
                    final TextView emailTV = (TextView) findViewById(R.id.emailTV);
                    int isUser = 0;

                    for (int i = 0; i <= jsonArray.length(); i++) {
//                        Log.d(TAG, "doInBackground: jsonArray ->" + jsonArray.getJSONObject(i));

                        if (jsonArray.getJSONObject(i).getString("ID").equals(userID)) {
                            email = jsonArray.getJSONObject(i).getString("EmailAddress");
                            mbrStatus = jsonArray.getJSONObject(i).getString("MbrStatus");
                            userID = jsonArray.getJSONObject(i).getString("ID");
                            photoID = jsonArray.getJSONObject(i).getString("PhotoID");
                            yearTurnedActive = jsonArray.getJSONObject(i).getString("YearTA");
                            firstName = jsonArray.getJSONObject(i).getString("FirstName");
                            lastName = jsonArray.getJSONObject(i).getString("LastName");
                            spouse = jsonArray.getJSONObject(i).getString("Spouse");
                            StreetAddress = jsonArray.getJSONObject(i).getString("StreetAddress");
                            CityState = jsonArray.getJSONObject(i).getString("CityState");
                            ZipCode = jsonArray.getJSONObject(i).getString("ZipCode");
                            PrimaryContactNumber = jsonArray.getJSONObject(i).getString("PrimNum");
                            SecondaryContactNumber = jsonArray.getJSONObject(i).getString("SecNum");
                            WorkNum = jsonArray.getJSONObject(i).getString("WorkNum");
                            Officers = jsonArray.getJSONObject(i).getString("Officers");
                            Committee = jsonArray.getJSONObject(i).getString("Committee");
                            CommitteeTitle = jsonArray.getJSONObject(i).getString("CommitteeTitle");
                            AzaleaGardenTourCommitteesTitle = jsonArray.getJSONObject(i).getString("AzaleaGardenTourCommitteesTitles");
                            AzaleaGardenTourCommittees = jsonArray.getJSONObject(i).getString("AzaleaGardenTourCommittees");
                            BiographicalInfo = jsonArray.getJSONObject(i).getString("BiographicalInfo");

                            final String finalFirstName = firstName;
                            final String finalLastName = lastName;
                            final String finalMbrStatus = mbrStatus;
                            final String finalSpouse = spouse;
                            final String finalStreetAddress = StreetAddress;
                            final String finalCityState = CityState;
                            final String finalZipCode = ZipCode;
                            final String finalPrimaryContactNumber = PrimaryContactNumber;
                            final String finalSecondaryContactNumber = SecondaryContactNumber;
                            final String finalEmail = email;
                            final String finalBiographicalInfo = BiographicalInfo;

                            final String finalYearTurnedActive = yearTurnedActive;
                            final String finalSpouse1 = spouse;

                            final TextView tvMoreInfo = findViewById(R.id.tv_more_info);
                            final TextView tvBack = findViewById(R.id.tv_back);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalEmail.equals(loginEmail)) {
                                        Log.d(TAG, "run: finalFirstName: " + finalFirstName);
                                        nameTV.setText(finalFirstName.concat(" " + finalLastName));
                                        mbrStatusTV.setText(finalMbrStatus);
                                        spouseTV.setText(finalSpouse);
                                        addressTV.setText(finalStreetAddress.concat(",\n" + finalCityState + ", " + " " + finalZipCode));
                                        primaryContactTV.setText(finalPrimaryContactNumber);
                                        secondaryContactTV.setText(finalSecondaryContactNumber);
                                        emailTV.setText(finalEmail);

                                        btnEmail = findViewById(R.id.btn_email);
                                        btnCall = findViewById(R.id.btn_call);
                                        btnText = findViewById(R.id.btn_text);

                                        TextView tvMoreInfo = findViewById(R.id.tv_more_info);
                                        final TextView tvBack = findViewById(R.id.tv_back);
                                        final TextView tvEdit = findViewById(R.id.tv_edit);
                                        tvEdit.setText("Edit");

                                        final int nameId = nameTV.getId();
                                        final int spouseId = spouseTV.getId();
                                        final int addressId = addressTV.getId();
                                        final int primId = primaryContactTV.getId();
                                        final int secId = secondaryContactTV.getId();
                                        final int emailId = emailTV.getId();



                                        AlertDialog alertDialog = null;
                                        final AlertDialog.Builder builder;
                                        builder = new AlertDialog.Builder(Contact.this);
                                        View viewAD = getLayoutInflater().inflate(R.layout.dialog_general, null);

                                        final TextView adTitle = viewAD.findViewById(R.id.genTitle);
                                        final EditText etGen = viewAD.findViewById(R.id.etGen);
                                        builder.setView(viewAD)
                                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //this will update the data
                                                        //gets the id of the textview
                                                        //viewId_placeholder is set to the id of the textview that is clicked
                                                        //so each if statement says "if this was the textview that had been clicked, do this: "
                                                        Log.d(TAG, "onClick: i: " + i);
                                                        if (viewId_placeholder == spouseId) {
                                                            spouseTV.setText(etGen.getText());
                                                            udSpouse = etGen.getText().toString();
                                                            if (udFN == null) {
                                                                udFN = finalFirstName;
                                                            }
                                                            if (udLN == null) {
                                                                udLN = finalLastName;
                                                            }
                                                            if (udSpouse == null) {
                                                                udSpouse = finalSpouse;
                                                            }
                                                            if (udCAS == null) {
                                                                udCAS = finalCityState;
                                                            }
                                                            if (udPrim == null) {
                                                                udPrim = finalPrimaryContactNumber;
                                                            }
                                                            if (udSec == null) {
                                                                udSec = finalSecondaryContactNumber;
                                                            }
                                                            if (udZip == null) {
                                                                udZip = finalZipCode;
                                                            }
                                                            try {
                                                                SaveData(userID, loginEmail, udFN, udLN, udSpouse, udAddress, udCAS, udZip, udPrim, udSec);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Toast.makeText(getApplicationContext(), "udFN: " + udFN + "; udLN: " + udLN + "; udSpouse: " +udSpouse + "; udCAS: " +udCAS + "; udPrim: " +
                                                                    udPrim + "; udSec: " +udSec+ "; udZip: " + udZip, Toast.LENGTH_LONG).show();
                                                        }
                                                        if (viewId_placeholder == primId) {
                                                            primaryContactTV.setText(etGen.getText());
                                                            udPrim = etGen.getText().toString();
                                                            if (udFN == null) {
                                                                udFN = finalFirstName;
                                                            }
                                                            if (udLN == null) {
                                                                udLN = finalLastName;
                                                            }
                                                            if (udSpouse == null) {
                                                                udSpouse = finalSpouse;
                                                            }
                                                            if (udCAS == null) {
                                                                udCAS = finalCityState;
                                                            }
                                                            if (udPrim == null) {
                                                                udPrim = finalPrimaryContactNumber;
                                                            }
                                                            if (udSec == null) {
                                                                udSec = finalSecondaryContactNumber;
                                                            }
                                                            if (udZip == null) {
                                                                udZip = finalZipCode;
                                                            }
                                                            try {
                                                                SaveData(userID, loginEmail, udFN, udLN, udSpouse, udAddress, udCAS, udZip, udPrim, udSec);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Toast.makeText(getApplicationContext(), "udFN: " + udFN + "; udLN: " + udLN + "; udSpouse: " +udSpouse + "; udCAS: " +udCAS + "; udPrim: " +
                                                                    udPrim + "; udSec: " +udSec+ "; udZip: " + udZip, Toast.LENGTH_LONG).show();
                                                        }
                                                        if (viewId_placeholder == secId) {
                                                            secondaryContactTV.setText(etGen.getText());
                                                            udSec = etGen.getText().toString();
                                                            if (udFN == null) {
                                                                udFN = finalFirstName;
                                                            }
                                                            if (udLN == null) {
                                                                udLN = finalLastName;
                                                            }
                                                            if (udSpouse == null) {
                                                                udSpouse = finalSpouse;
                                                            }
                                                            if (udCAS == null) {
                                                                udCAS = finalCityState;
                                                            }
                                                            if (udPrim == null) {
                                                                udPrim = finalPrimaryContactNumber;
                                                            }
                                                            if (udSec == null) {
                                                                udSec = finalSecondaryContactNumber;
                                                            }
                                                            if (udZip == null) {
                                                                udZip = finalZipCode;
                                                            }
                                                            try {
                                                                SaveData(userID, loginEmail, udFN, udLN, udSpouse, udAddress, udCAS, udZip, udPrim, udSec);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Toast.makeText(getApplicationContext(), "udFN: " + udFN + "; udLN: " + udLN + "; udSpouse: " +udSpouse + "; udCAS: " +udCAS + "; udPrim: " +
                                                                            udPrim + "; udSec: " +udSec+ "; udZip: " + udZip, Toast.LENGTH_LONG).show();


                                                        }
//                                                        String postFN = firstName;
//                                                        String postLN = nameTV.getText().toString();
//                                                        String postFN = nameTV.getText().toString();
//                                                        String postFN = nameTV.getText().toString();
//                                                        String postFN = nameTV.getText().toString();
//                                                        String postFN = nameTV.getText().toString();
//                                                        String postFN = nameTV.getText().toString();
//                                                        SaveData();

                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        return;
                                                    }
                                                });

                                        alertDialog = builder.create();

                                        //set all the edit texts and buttons

                                        //----!!!!!!---- Update Date begins ----!!!!!!----
                                        //no action is attached to this update, so add it within an onclicklistener
                                        String url = "http://satoshi.cis.uncw.edu/~jbr5433/GardenClub/update.php";

                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("userID", userID));
                                        params.add(new BasicNameValuePair("userID", finalEmail));
                                        params.add(new BasicNameValuePair("spouse", finalSpouse));
                                        params.add(new BasicNameValuePair("streetAddress", finalStreetAddress));
                                        params.add(new BasicNameValuePair("primNum", finalPrimaryContactNumber));
                                        params.add(new BasicNameValuePair("secNum", finalSecondaryContactNumber));

//                                        app will crash once you uncomment this: PROBLEM WITH THE URL or PARAMS?????
                                        String resultServer = null;
                                        try {
                                            resultServer = getHttpPost(url, params);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d(TAG, "run: resultserver: " + resultServer);

                                        // !!!!!!!!!!!!! UPDATE ENDS !!!!!!!!!!!!!!!

//                                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
////                                                textView.setText(editText.getText());
//                                            }
//                                        });

                                        tvEdit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getApplicationContext(), "Tap the fields you wish to change", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        //all other textviews.setOnCliCkListener(editFields);

                                        //updated variables


                                        final AlertDialog finalGenAD = alertDialog;
                                        View.OnClickListener editFields = new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //need to indicate which alert dialog needs to be shown: firstName or LastName? City or State or Zip?
                                                int viewId = view.getId();
                                                int z = 0;

                                                if (viewId == nameTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    AlertDialog adName = null;
                                                    AlertDialog.Builder builderName;
                                                    builderName = new AlertDialog.Builder(Contact.this);
                                                    View viewName = getLayoutInflater().inflate(R.layout.dialog_name, null);
                                                    final EditText etFN = viewName.findViewById(R.id.etFirstName);
                                                    final EditText etLN = viewName.findViewById(R.id.etLastName);
                                                    Log.d(TAG, "onClick: etFN: " + etFN);
                                                    builderName.setView(viewName)
                                                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                udFN = etFN.getText().toString();
                                                                udLN = etLN.getText().toString();
                                                                if (udAddress == null) {
                                                                    udAddress = finalStreetAddress;
                                                                }
                                                                if (udSpouse == null) {
                                                                    udSpouse = finalSpouse;
                                                                }
                                                                if (udCAS == null) {
                                                                    udCAS = finalCityState;
                                                                }
                                                                if (udPrim == null) {
                                                                    udPrim = finalPrimaryContactNumber;
                                                                }
                                                                if (udSec == null) {
                                                                    udSec = finalSecondaryContactNumber;
                                                                }
                                                                if (udZip == null) {
                                                                    udZip = finalZipCode;
                                                                }
                                                                try {
                                                                    SaveData(userID, loginEmail, udFN, udLN, udSpouse, udAddress, udCAS, udZip, udPrim, udSec);
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                nameTV.setText(udFN.concat(" " + udLN));
                                                                Toast.makeText(getApplicationContext(), "udFN: " + udFN + "; udLN: " + udLN + "; udSpouse: " +udSpouse + "; udCAS: " +udCAS + "; udPrim: " +
                                                                        udPrim + "; udSec: " +udSec+ "; udZip: " + udZip, Toast.LENGTH_LONG).show();
                                                            }})
                                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                            }
                                                    });
                                                    adName = builderName.create();
                                                    adName.show();


                                                    etFN.setText(finalFirstName);
                                                    etLN.setText(finalLastName);
                                                }
                                                if (viewId == mbrStatusTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    Toast.makeText(getApplicationContext(), "You cannot edit your Member Status from this application", Toast.LENGTH_LONG).show();

                                                }
                                                if (viewId == spouseTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalGenAD.show();
                                                    etGen.setText(finalSpouse);
                                                    adTitle.setText("Edit Spouse Name");
//                                                    etAlertDialog.setText(spouseTV.getText());
                                                }
                                                if (viewId == addressTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    AlertDialog adAddress = null;
                                                    AlertDialog.Builder builderAddress;
                                                    builderAddress = new AlertDialog.Builder(Contact.this);
                                                    View viewAd = getLayoutInflater().inflate(R.layout.dialog_address, null);
                                                    final EditText etStreet = viewAd.findViewById(R.id.etStreet);
                                                    EditText etCAS = viewAd.findViewById(R.id.etCityAndState);
                                                    EditText etZip = viewAd.findViewById(R.id.etZip);
                                                    etStreet.setText(finalStreetAddress);
                                                    etCAS.setText(finalCityState);
                                                    etZip.setText(finalZipCode);

                                                    builderAddress.setView(viewAd);
                                                    builderAddress.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    udAddress = etStreet.getText().toString();
                                                                    if (udFN == null) {
                                                                        udFN = finalFirstName;
                                                                    }
                                                                    if (udLN == null) {
                                                                        udLN = finalLastName;
                                                                    }
                                                                    if (udSpouse == null) {
                                                                        udSpouse = finalSpouse;
                                                                    }
                                                                    if (udCAS == null) {
                                                                        udCAS = finalCityState;
                                                                    }
                                                                    if (udPrim == null) {
                                                                        udPrim = finalPrimaryContactNumber;
                                                                    }
                                                                    if (udSec == null) {
                                                                        udSec = finalSecondaryContactNumber;
                                                                    }
                                                                    if (udZip == null) {
                                                                        udZip = finalZipCode;
                                                                    }
                                                                    try {
                                                                        SaveData(userID, loginEmail, udFN, udLN, udSpouse, udAddress, udCAS, udZip, udPrim, udSec);
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    addressTV.setText(udAddress.concat("\n" + udCAS + ", " + " " + udZip));
                                                                    Toast.makeText(getApplicationContext(), "udFN: " + udFN + "; udLN: " + udLN + "; udSpouse: " +udSpouse + "; udCAS: " +udCAS + "; udPrim: " +
                                                                            udPrim + "; udSec: " +udSec+ "; udZip: " + udZip, Toast.LENGTH_LONG).show();
                                                                }})
                                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    return;
                                                                }
                                                            });
                                                    adAddress = builderAddress.create();
                                                    adAddress.show();
                                                }
                                                if (viewId == primaryContactTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalGenAD.show();
                                                    etGen.setText(finalPrimaryContactNumber);
                                                    adTitle.setText("Edit Primary Contact Number");
                                                }
                                                if (viewId == secondaryContactTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalGenAD.show();
                                                    etGen.setText(finalSecondaryContactNumber);
                                                    adTitle.setText("Edit Secondary Contact Number");
                                                }
                                                if (viewId == emailTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    Toast.makeText(getApplicationContext(), "You cannot edit your email address from this application", Toast.LENGTH_LONG).show();
                                                }
//                                            editText.setText(view.getId().get tvEdit.getText());/////<---------
                                            }
                                        };


                                        nameTV.setOnClickListener(editFields);
                                        mbrStatusTV.setOnClickListener(editFields);
                                        spouseTV.setOnClickListener(editFields);
                                        addressTV.setOnClickListener(editFields);
                                        primaryContactTV.setOnClickListener(editFields);
                                        secondaryContactTV.setOnClickListener(editFields);
                                        emailTV.setOnClickListener(editFields);

                                    }

                                    tvMoreInfo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent moreInfoIntent = new Intent(getApplicationContext(), MoreInfo.class);
                                            moreInfoIntent.putExtra("bio", finalBiographicalInfo);
                                            moreInfoIntent.putExtra("YTA", finalYearTurnedActive);
                                            moreInfoIntent.putExtra("login_email", loginEmail);
                                            moreInfoIntent.putExtra("user_email", finalEmail);
                                            startActivity(moreInfoIntent);

                                        }
                                    });

                                    tvBack.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            onBackPressed();
                                        }
                                    });

                                    btnCall.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            callIntent.setData(Uri.parse("tel: " + finalPrimaryContactNumber));
                                            if (ActivityCompat.checkSelfPermission(Contact.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                // TODO: Consider calling
                                                //    ActivityCompat#requestPermissions
                                                // here to request the missing permissions, and then overriding
                                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                //                                          int[] grantResults)
                                                // to handle the case where the user grants the permission. See the documentation
                                                // for ActivityCompat#requestPermissions for more details.
                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                intent.setData(Uri.parse("tel: " + finalPrimaryContactNumber));
                                                startActivity(intent);
                                                return;
                                            }
                                            startActivity(callIntent);
                                        }
                                    });

                                    btnEmail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

//                String email = String.valueOf(emailID.getText());

                                            Log.d(TAG, "onClick: finalEmail: " + finalEmail);

                                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{finalEmail});
//                                            emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"swanandinaction@gmail.com"});
//                                            emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{"swanandinaction@gmail.com"});
                                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject: ");
                                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body: ");

                                            emailIntent.setType("message/rfc822");
                                            startActivity(Intent.createChooser(emailIntent, "Choose email client... "));

                                        }
                                    });

                                    btnText.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Log.d(TAG, "onClick: clicked ae");
                                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                            sendIntent.setData(Uri.parse("sms: " + finalPrimaryContactNumber));
                                            startActivity(sendIntent);
                                        }
                                    });
                                }
                            });
                            Intent intent = new Intent(String.valueOf(this));
                            intent.putExtra("json_data", json_string);
                        }
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    SBString = stringBuilder.toString().trim();
                    return SBString;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        public boolean SaveData(String uID, String email, String fName, String lName, String spouse, String streetAddress, String CAS, String zipCode, String primNum, String secNum) throws InterruptedException {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            final TextView txtUserID = (TextView)findViewById(R.id.txtUserID);
//            final TextView txtAppointmentID = (TextView)findViewById(R.id.txtAppointmentID);
//            final EditText txtType = (EditText)findViewById(R.id.txtType);
//            final EditText txtDate = (EditText)findViewById(R.id.txtDate);
//            final EditText txtTime = (EditText)findViewById(R.id.txtTime);

            //Dialog
            final AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());

            ad.setTitle("Error! ");
            ad.setIcon(android.R.drawable.btn_star_big_on);
            ad.setPositiveButton("Close", null);

            String url = "http://satoshi.cis.uncw.edu/~jbr5433/GardenClub/update.php";
            params.add(new BasicNameValuePair("userID", uID));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("firstName", fName));
            params.add(new BasicNameValuePair("lastName", lName));
            params.add(new BasicNameValuePair("spouse", spouse));
            params.add(new BasicNameValuePair("streetAddress", streetAddress));
            params.add(new BasicNameValuePair("CAS", CAS));
            params.add(new BasicNameValuePair("zipCode", zipCode));
            params.add(new BasicNameValuePair("primNum", primNum));
            params.add(new BasicNameValuePair("secNum", secNum));

            String resultServer  = getHttpPost(url,params);
            Log.d(TAG, "resultServer - updateData: " + resultServer);

            /*** Default Value ***/
            String strStatusID = "0";
            String strMessage = "Unknown Status!";

            JSONObject c;
            try {
                c = new JSONObject(resultServer);
                strStatusID = c.getString("StatusID");
                strMessage = c.getString("Message");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Prepare Save Data
            if(strStatusID.equals("0"))
            {
//                ad.setMessage(strMessage);
//                ad.show();
                Toast.makeText(Contact.this, "Update not successful", Toast.LENGTH_SHORT).show();

                return false;
            }
            else
            {
                Toast.makeText(Contact.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        public String getHttpPost(String strUrl, final List<NameValuePair> params) throws InterruptedException {
            Log.d(TAG, "getHttpPost: starts");
            final String url = strUrl;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    Log.d(TAG, "run: works");
                    str = new StringBuilder();

                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(params));
                        //problem starts here
                        HttpResponse response = client.execute(httpPost);
                        StatusLine statusLine = response.getStatusLine();
                        int statusCode = statusLine.getStatusCode();
                        if (statusCode == 200) { // Status OK
                            HttpEntity entity = response.getEntity();
                            InputStream content = entity.getContent();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                str.append(line);
                            }
                        } else {
                            Log.e("Log", "Failed to download result..");
                        }
                        Log.d(TAG, "run: str: " + str);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            Log.d(TAG, "getHttpPost: str: " + str);
            return str.toString();
        }

        @Override
        protected void onPreExecute() {
            json_post = "http://satoshi.cis.uncw.edu/~jbr5433/GardenClub/get_json_info.php";
            json_get = "http://satoshi.cis.uncw.edu/~jbr5433/GardenClub/more_json.php";

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

//            TextView textView = findViewById(R.id.textView);
//            textView.setText(result);
//            Log.d(TAG, "onPostExecute: s = " + s);
            json_string = result;
//            Log.d(TAG, "onPostExecute: json_string: " + json_string.toString());
//            writeToFile(json_string);
            JSONObject jsonObject = null;
            Log.d(TAG, "onPostExecute: result:: " + result);
            try {
                jsonObject = new JSONObject(String.valueOf(SBString));
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                String sssss = jsonObject.getString("userID");
                Log.d(TAG, "onPostExecute: :::::" + sssss);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseJson(View view) {
        new BackgroundTask1().execute();
        if (json_string == null) {
//            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(this, ContactList.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }
    }

}


