package com.example.lenovo.gardenclub;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//from github/gardenclub-android>git add .
//...github/gardenclub-android>git commit -m "add existing files
//...github/gardenclub-android>git push origin master

/**
 * User photo images <-----
 *
 * Primary contact number edit, then update that for the call and text button
 *
 * update database with edited information:
 * --> this may be done with a php file
 *
 */


public class MainActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    Button Login;
    static String JSON_STRING;
    static String json_string;
    private static final String TAG = "MainActivity";
    WebView mWebView;
    String password, username;
    int jsonParsed = 0;
     Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, ContactList.class);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        Login = findViewById(R.id.button);
        UsernameEt = findViewById(R.id.et_login);
        PasswordEt = findViewById(R.id.et_pass);

        Login.setVisibility(View.GONE);
        UsernameEt.setVisibility(View.GONE);
        PasswordEt.setVisibility(View.GONE);

        username = UsernameEt.getText().toString();
        password = PasswordEt.getText().toString();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "login";

//                StringBuilder sb = new StringBuilder();
////                sb.append("document.getElementsByTagName('form')[0].onsubmit = function () {");
//                sb.append("var login = function () {");
//                sb.append("var objPWD = " + password + ";objAccount  = " + username + ";var str = '';");
//                sb.append("var inputs = document.getElementsByTagName('input');");
//                sb.append("for (var i = 0; i < inputs.length; i++) {");
//                sb.append("if (inputs[i].name.toLowerCase() === 'pwd') {inputs[i].value = " + password + ";}");
//                sb.append("else if (inputs[i].name.toLowerCase() === 'log') {inputs[i].value = " + username + ";}");
//                sb.append("}");
//                sb.append("if (objAccount != null) {str += objAccount.value;}");
////                sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
//                sb.append("window.Android.processHTML(str);");
//                sb.append("return true;");
//                sb.append("};");
////                webview.loadUrl("javascript:" + sb.toString());

                parseJson(v);
//                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
//                backgroundWorker.execute(type, username, password);

//                Intent intent = new Intent(MainActivity.this, ContactList.class);
//                startActivity(intent);
     //           Login.setText("test");

            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //enable javascript
        mWebView = findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final WebAppInterface webAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(webAppInterface, "Android");

        //catch events
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: called");
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: called");
//                try {
//                    view.loadUrl("javascript:" + buildInjection());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "onPageFinished: sdasd ", e);
//                }
                StringBuilder sb = new StringBuilder();
                sb.append("document.getElementsByTagName('form')[0].onsubmit = function () {");
                sb.append("var objPWD, objAccount;var str = '';");
                sb.append("var inputs = document.getElementsByTagName('input');");
                sb.append("for (var i = 0; i < inputs.length; i++) {");
                sb.append("if (inputs[i].name.toLowerCase() === 'pwd') {objPWD = inputs[i];}");
                sb.append("else if (inputs[i].name.toLowerCase() === 'log') {objAccount = inputs[i];}");
                sb.append("}");
                sb.append("if (objAccount != null) {str += objAccount.value;}");
//                sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
                sb.append("window.Android.processHTML(str);");
                sb.append("return true;");
                sb.append("};");
                view.loadUrl("javascript:" + sb.toString());

                if (view.getUrl().equals("http://www.capefeargardenclub.org/")) {
                    Log.d(TAG, "onPageFinished: view.getUrl() = " + view.getUrl());
                    mWebView.setVisibility(View.GONE);
                    parseJson(view);
//                    Login.performClick();
//                    parseJson(view);
                    if (json_string != null) {
                        Log.d(TAG, "onPageFinished: json_string != null");
                        intent.putExtra("json_data", json_string);
                        Log.d(TAG, "onPageFinished: json_String =  " + json_string);
                        Log.d(TAG, "onPageFinished: loginInfo = " + webAppInterface.loginInfo);
                        intent.putExtra("login_email", webAppInterface.loginInfo);
                        startActivity(intent);

                    } else {
                        Log.d(TAG, "onPageFinished: json_string == null / jsonParsed = " + jsonParsed);
                        parseJson(view);
                        mWebView.reload();


                    }
//                    if (json_string == null) {
//                        Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();
//
//                    }
                } else  {
                    Log.d(TAG, "onPageFinished: view.getUrl() = " + view.getUrl());
                }
            }
        });

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("http://www.capefeargardenclub.org/wp-login.php?redirect_to=%2f");
            }
        });

    }

    private String buildInjection() throws IOException {
        Log.d(TAG, "buildInjection: called");
        StringBuilder buf = new StringBuilder();
        InputStream inject = getAssets().open("get_user_info.js");// file from assets
        BufferedReader in = new BufferedReader(new InputStreamReader(inject, "UTF-8"));
        String str;
        while ((str = in.readLine()) != null) {
            buf.append(str);
        }
        in.close();
        Log.d(TAG, "buildInjection: in: " + in);
        Log.d(TAG, "buildInjection: buf: " + buf);
        return buf.toString();
    }

    public void getJSON(View view) {
        new BackgroundTask().execute();
        parseJson(view);

    }

    public void parseJson(View view) {
        new BackgroundTask().execute();
        if (json_string == null) {
            Log.d(TAG, "parseJson: json_string == null");
            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();

        } else {
            Log.d(TAG, "parseJson: json_string != null");
            jsonParsed = 1;
            intent.putExtra("json_data", json_string);
            Log.d(TAG, "parseJson: json_data: " + json_string);
        }
    }



    class BackgroundTask extends AsyncTask<Void,Void,String> {
        String json_url;
        private static final String TAG = "BackgroundTask";

        @Override
        protected String doInBackground(Void... voids) {
            try {
//                String username = params[1];
//                String password = params[2];
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING+"\n");
                }
//                json_string = stringBuilder.toString();

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
            json_string = result;
            Log.d(TAG, "onPostExecute: json_string: " + json_string.toString());

        }
    }

}
