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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
                    String SreetAddress, Committee, CommitteeTitle, AzaleaGardenTourCommitteesTitle, AzaleaGardenTourCommittees;
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
                            SreetAddress = jsonArray.getJSONObject(i).getString("StreetAddress");
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
                            final String finalSreetAddress = SreetAddress;
                            final String finalCityState = CityState;
                            final String finalZipCode = ZipCode;
                            final String finalPrimaryContactNumber = PrimaryContactNumber;
                            final String finalSecondaryContactNumber = SecondaryContactNumber;
                            final String finalEmail = email;
                            final String finalBiographicalInfo = BiographicalInfo;

                            final String finalYearTurnedActive = yearTurnedActive;
                            final String finalSpouse1 = spouse;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    nameTV.setText(finalFirstName.concat(" " + finalLastName));
                                    mbrStatusTV.setText(finalMbrStatus);
                                    spouseTV.setText(finalSpouse);
                                    addressTV.setText(finalSreetAddress.concat(",\n" + finalCityState + ", " + " " + finalZipCode));
                                    primaryContactTV.setText(finalPrimaryContactNumber);
                                    secondaryContactTV.setText(finalSecondaryContactNumber);
                                    emailTV.setText(finalEmail);

                                    btnEmail = findViewById(R.id.btn_email);
                                    btnCall = findViewById(R.id.btn_call);
                                    btnText = findViewById(R.id.btn_text);

                                    TextView tvMoreInfo = findViewById(R.id.tv_more_info);
                                    final TextView tvBack = findViewById(R.id.tv_back);
                                    final TextView tvEdit = findViewById(R.id.tv_edit);
                                    final EditText editText;
                                    AlertDialog alertDialog = null;
                                    final AlertDialog.Builder builder;
                                    builder = new AlertDialog.Builder(new ContextThemeWrapper(Contact.this, R.style.myDialog));
                                    editText = new EditText(getApplicationContext());

                                    if (finalEmail.equals(loginEmail)) {
                                        final int nameId = nameTV.getId();
                                        final int spouseId = spouseTV.getId();
                                        final int addressId = addressTV.getId();
                                        final int primId = primaryContactTV.getId();
                                        final int secId = secondaryContactTV.getId();
                                        final int emailId = emailTV.getId();

                                        //----!!!!!!---- Update Date begins ----!!!!!!----
                                        //no action is attached to this update, so add it within an onclicklistener
                                        String url = "http://satoshi.cis.uncw.edu/~jbr5433/GardenClub/update.php";

                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("ID", userID));
                                        params.add(new BasicNameValuePair("Spouse", finalSpouse));
//                                        params.add(new BasicNameValuePair("StreetAddress", finalSreetAddress));
                                        params.add(new BasicNameValuePair("PrimNum", finalPrimaryContactNumber));
                                        params.add(new BasicNameValuePair("SecNum", finalSecondaryContactNumber));

                                        //app will crash once you uncomment this
//                                        String resultServer = getHttpPost(url, params);
//                                        Log.d(TAG, "run: resultserver: " + resultServer);

                                        // !!!!!!!!!!!!! UPDATE ENDS !!!!!!!!!!!!!!!

                                        tvEdit.setText("Edit");
                                        builder.setTitle("Edit Fields");
                                        builder.setView(editText);
                                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if (viewId_placeholder == nameId) {
                                                            nameTV.setText(editText.getText());
                                                        }
                                                        if (viewId_placeholder == spouseId) {
                                                            spouseTV.setText(editText.getText());
                                                        }
                                                        if (viewId_placeholder == addressId) {
                                                            /** FIX THIS _____
                                                            builder.setTitle("Edit Street Address");
                                                            alertDialog.show();
                                                            builder.setTitle("Edit City and State");
                                                            alertDialog.show();
                                                            builder.setTitle("Edit Zipcode");
                                                            addressTV.setText(editText.getText());
                                                             */
                                                        }
                                                        if (viewId_placeholder == primId) {
                                                            primaryContactTV.setText(editText.getText());
                                                        }
                                                        if (viewId_placeholder == secId) {
                                                            secondaryContactTV.setText(editText.getText());
                                                        }
                                                        if (viewId_placeholder == emailId) {
                                                            //
                                                        }
                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });

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

                                        alertDialog = builder.create();

                                        //all other textviews.setOnCliCkListener(editFields);

                                        final AlertDialog finalAlertDialog = alertDialog;
                                        View.OnClickListener editFields = new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int viewId = view.getId();

                                                if (viewId == nameTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalAlertDialog.show();
                                                    editText.setText(nameTV.getText());
                                                }
                                                if (viewId == mbrStatusTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    Toast.makeText(getApplicationContext(), "You cannot edit your Member Status from this application", Toast.LENGTH_LONG).show();

                                                }
                                                if (viewId == spouseTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalAlertDialog.show();
                                                    editText.setText(spouseTV.getText());
                                                }
                                                if (viewId == addressTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalAlertDialog.show();
                                                    editText.setText(addressTV.getText());
                                                }
                                                if (viewId == primaryContactTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalAlertDialog.show();
                                                    editText.setText(primaryContactTV.getText());
                                                }
                                                if (viewId == secondaryContactTV.getId()) {
                                                    viewId_placeholder = viewId;
                                                    finalAlertDialog.show();
                                                    editText.setText(secondaryContactTV.getText());
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

//                    while(count <= jsonArray.length()) {
//                        //if (userID ==)
//                        JO = jsonArray.getJSONObject(count);
//                        name = JO.getString("firstName").concat(" " + JO.getString("lastName"));
//                        email = JO.getString("email");
//                        mobile = JO.getString("mobile");
//                        mbrStatus = JO.getString("mbrStatus");
//
////                        Contacts contact = new Contacts(name, email, mobile, mbrStatus);
////                        mContactAdapter.add(contact);
//
//                        count++;
//                    }

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

        public boolean SaveData(String firstName, String lastName, String spouse, String streetAddress, String primNum, String secNum) {
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
            params.add(new BasicNameValuePair("Spouse", spouse));
            params.add(new BasicNameValuePair("FirstName", firstName));
            params.add(new BasicNameValuePair("LastName", lastName));
            params.add(new BasicNameValuePair("StreetAddress", streetAddress));
            params.add(new BasicNameValuePair("PrimNum", primNum));
            params.add(new BasicNameValuePair("SecNum", secNum));

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
                ad.setMessage(strMessage);
                ad.show();
                return false;
            }
            else
            {
                Toast.makeText(Contact.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        public String getHttpPost(String url,List<NameValuePair> params) {
            StringBuilder str = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
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
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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


