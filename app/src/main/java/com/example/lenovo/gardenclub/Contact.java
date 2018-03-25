package com.example.lenovo.gardenclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class Contact extends AppCompatActivity {
    Button sendEmail;
    TextView emailID;
    JSONObject jsonObject;
    String json, JSON_STRING, json_string, type, userID;
    private static final String TAG = "Contact";

    public Contact() throws JSONException {
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////////////////////////////////////////////////////////
        Intent intent = new Intent(this, Contact.class);
        intent.getExtras();
        setContentView(R.layout.activity_contact);
        json = getIntent().getExtras().getString("json_object");

        try {
            jsonObject = new JSONObject(json);

            Toast.makeText(getApplicationContext(), jsonObject.getString("userID"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendEmail = (Button) findViewById(R.id.button6);
//        emailID = (TextView)findViewById(R.id.textView15);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = String.valueOf(emailID.getText());

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"swanandinaction@gmail.com"});
//                emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{"swanandinaction@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject: ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body: ");

                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Choose email client... "));

            }
        });
        try {
            userID = jsonObject.getString("userID");
            new BackgroundTask1().execute("get_info", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getJSON(View view) {
        new BackgroundTask1().execute();
        parseJson(view);
    }

    class BackgroundTask1 extends AsyncTask<String,Void,String> {
        String json_post, json_get;
        private static final String TAG = "BackgroundTask";


        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: starts");
            type = params[0];
            StringBuilder stringBuilder = null;
            Log.d(TAG, "doInBackground: type: "+type);
            if (type.equals("post_info")) {
                try {
                    Log.d(TAG, "doInBackground: type equals post_info");
                    userID = params[1];
//                String password = params[2];
                    URL url = new URL(json_get);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    //CHANGE THIS: FirstName and LastName
                    userID = jsonObject.getString("UserID");
                    String post_data = URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(userID,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
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
                        stringBuilder.append(JSON_STRING+"\n");
                        Log.d(TAG, "doInBackground: JSON_STRING: " + JSON_STRING);
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
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                writeToFile(inputStream, getApplicationContext());
                    stringBuilder = new StringBuilder();
                    while ((JSON_STRING = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING+"\n");
//                        Log.d(TAG, "doInBackground: JSON_STRING: " + JSON_STRING["server_response"]);
                    }
                    JSONObject JO;
                    Log.d(TAG, "doInBackground: JG: " + stringBuilder);
                    JSONObject jsonObject = new JSONObject(String.valueOf(stringBuilder));
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                    String email, mbrStatus, photoID, yearTurnedActive, firstName, lastName, spouse, SreetAddress, City, State;
                    String ZipCode, PrimaryContactNumber, SecondaryContactNumber, TypeofPrimaryContactNo, TypeofSecondaryContactNo, Office, OfficerTitle, ExecutiveBdMbrship;
                    String CurrentCmteAssignment1, CmteAssign1Chair, CmteAssign1CoChair, CurrentCmteAssignment2, CmteAssign2Chair, CmteAssign2CoChair;
                    String CurrentCmteAssignment3, CmteAssign3Chair, CmteAssign3CoChair, BiographicalInfo;
                    Log.d(TAG, "doInBackground: userID::: " + userID);

                    final TextView nameTV = (TextView) findViewById(R.id.nameTV);
                    final TextView mbrStatusTV = (TextView) findViewById(R.id.mbrStatusTV);
                    final TextView spouseTV = (TextView) findViewById(R.id.spouseTV);
                    final TextView addressTV = (TextView) findViewById(R.id.addressTV);
                    final TextView primaryContactTV = (TextView) findViewById(R.id.primaryContactTV);
                    final TextView secondaryContactTV = (TextView) findViewById(R.id.secondaryContactTV);
                    final TextView emailTV = (TextView) findViewById(R.id.emailTV);

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        Log.d(TAG, "doInBackground: jsonArray ->" + jsonArray.getJSONObject(i));

                        if (jsonArray.getJSONObject(i).getString("userID").equals(userID)) {
                            //////////////////////////////////////////////////it works
                            Log.d(TAG, "doInBackground: does it work?" + jsonArray.getJSONObject(i));
                            email = jsonArray.getJSONObject(i).getString("email");
                            Log.d(TAG, "doInBackground: email: " + jsonArray.getJSONObject(i).getString("email"));
                            mbrStatus = jsonArray.getJSONObject(i).getString("mbrStatus");
                            userID = jsonArray.getJSONObject(i).getString("userID");
                            photoID = jsonArray.getJSONObject(i).getString("photoID");
                            yearTurnedActive = jsonArray.getJSONObject(i).getString("yearTurnedActive");
                            firstName = jsonArray.getJSONObject(i).getString("firstName");
                            lastName = jsonArray.getJSONObject(i).getString("lastName");
                            spouse = jsonArray.getJSONObject(i).getString("spouse");
                            SreetAddress = jsonArray.getJSONObject(i).getString("StreetAddress");
                            City = jsonArray.getJSONObject(i).getString("City");
                            State = jsonArray.getJSONObject(i).getString("State");
                            ZipCode = jsonArray.getJSONObject(i).getString("ZipCode");
                            PrimaryContactNumber = jsonArray.getJSONObject(i).getString("mobile");
                            SecondaryContactNumber = jsonArray.getJSONObject(i).getString("SecondaryContactNumber");
                            TypeofPrimaryContactNo = jsonArray.getJSONObject(i).getString("TypeofPrimaryContactNo");
                            TypeofSecondaryContactNo = jsonArray.getJSONObject(i).getString("TypeofSecondaryContactNo");
                            Office = jsonArray.getJSONObject(i).getString("Office");
                            OfficerTitle = jsonArray.getJSONObject(i).getString("OfficerTitle");
                            ExecutiveBdMbrship = jsonArray.getJSONObject(i).getString("ExecutiveBdMbrship");
                            CurrentCmteAssignment1 = jsonArray.getJSONObject(i).getString("CurrentCmteAssignment1");
                            CmteAssign1Chair = jsonArray.getJSONObject(i).getString("CmteAssign1Chair");
                            CmteAssign1CoChair = jsonArray.getJSONObject(i).getString("CmteAssign1CoChair");
                            CurrentCmteAssignment2 = jsonArray.getJSONObject(i).getString("CurrentCmteAssignment2");
                            CmteAssign2Chair = jsonArray.getJSONObject(i).getString("CmteAssign2Chair");
                            CmteAssign2CoChair = jsonArray.getJSONObject(i).getString("CmteAssign2CoChair");
                            CurrentCmteAssignment3 = jsonArray.getJSONObject(i).getString("CurrentCmteAssignment3");
                            CmteAssign3Chair = jsonArray.getJSONObject(i).getString("CmteAssign3Chair");
                            CmteAssign3CoChair = jsonArray.getJSONObject(i).getString("CmteAssign3CoChair");
                            BiographicalInfo = jsonArray.getJSONObject(i).getString("BiographicalInfo");

                            Log.d(TAG, "doInBackground: stufdf: " + firstName + lastName + mbrStatus);
                            final String finalFirstName = firstName;
                            final String finalLastName = lastName;
                            final String finalMbrStatus = mbrStatus;
                            final String finalSpouse = spouse;
                            final String finalSreetAddress = SreetAddress;
                            final String finalCity = City;
                            final String finalState = State;
                            final String finalZipCode = ZipCode;
                            final String finalPrimaryContactNumber = PrimaryContactNumber;
                            final String finalSecondaryContactNumber = SecondaryContactNumber;
                            final String finalEmail = email;
                            final String finalBiographicalInfo = BiographicalInfo;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    nameTV.setText(finalFirstName.concat(" " + finalLastName));
                                    mbrStatusTV.setText(finalMbrStatus);
                                    spouseTV.setText(finalSpouse);
                                    addressTV.setText(finalSreetAddress.concat(", " + finalCity + ", " + finalState + " " + finalZipCode));
                                    primaryContactTV.setText(finalPrimaryContactNumber);
                                    secondaryContactTV.setText(finalSecondaryContactNumber);
                                    emailTV.setText(finalEmail);
                                }
                            });

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
                    return stringBuilder.toString().trim();

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

        }
    }

    public void parseJson(View view) {
        new BackgroundTask1().execute();
        if (json_string == null) {
            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(this, ContactList.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }
    }

}


