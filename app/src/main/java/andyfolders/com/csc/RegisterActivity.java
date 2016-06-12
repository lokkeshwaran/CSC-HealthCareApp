package andyfolders.com.csc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends Activity {

    protected EditText username;
    private EditText password;
    private EditText patient_id;
    public String enteredUsername;
    public String enteredPassword;
    public String enteredpatient_id;
    private final String serverUrl = "http://lokkeshwaran.cu.ma/csccoin/index.php";
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    public String regId = "";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    AsyncTask<Void, Void, String> createRegIdTask;
    public static final String REG_ID = "regId";
    public static final String patient_id_ID = "patient_idId";
    public String registrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView imgFp = (ImageView) findViewById(R.id.imageView);
        imgFp.setImageResource(R.drawable.head);
        applicationContext = getApplicationContext();

        username = (EditText)findViewById(R.id.username_field);
        password = (EditText)findViewById(R.id.password_field);
        patient_id = (EditText)findViewById(R.id.patient_id_field);
        Button signUpButton = (Button)findViewById(R.id.sign_up);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredUsername = username.getText().toString();
                enteredPassword = password.getText().toString();
                enteredpatient_id = patient_id.getText().toString();

                String patient_idID =enteredpatient_id;

                if(enteredUsername.equals("") || enteredPassword.equals("") || enteredpatient_id.equals("")){
                    Toast.makeText(RegisterActivity.this, "Username or password or patient_id must be filled", Toast.LENGTH_LONG).show();
                    return;
                }
                if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){
                    Toast.makeText(RegisterActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                    return;
                }

                if (checkPlayServices()) {

                    // Register Device in GCM Server
                    registerInBackground(patient_idID);
                }
                // request authentication with remote server4

            }
        });

        TextView login = (TextView)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }



    private void registerInBackground(final String patient_idID) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(applicationContext);
                    }
                    regId = gcmObj
                            .register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    storeRegIdinSharedPref(applicationContext, regId, patient_idID);

                    Toast.makeText(
                            applicationContext,
                            "Registering & Configuring Notifications ..."
                            , Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(
                            applicationContext,
                            "Not Connected to Internet"
                            , Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    // Store RegId and patient_id entered by User in SharedPref
    private void storeRegIdinSharedPref(Context context, String regId1,
                                        String patient_idID) {
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId1);
        editor.putString(patient_id_ID, patient_idID);
        editor.commit();

        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword, enteredpatient_id,regId1);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("username", params[1]));
                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                nameValuePairs.add(new BasicNameValuePair("patient_id", params[3]));
                nameValuePairs.add(new BasicNameValuePair("regid", params[4]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                System.out.println(params[4]);
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("kk");
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                Toast.makeText(RegisterActivity.this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult == 0){
                Toast.makeText(RegisterActivity.this, "Already Registered or Invalid Entries", Toast.LENGTH_LONG).show();
                return;
            }
            if(jsonResult == 1){

                SharedPreferences prefs = getSharedPreferences("UserDetails",
                        Context.MODE_PRIVATE);
                registrationId = prefs.getString(REG_ID, "");


                if (!TextUtils.isEmpty(registrationId)) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivitylogin.class);
                    Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    intent.putExtra("regId", registrationId);
                    startActivity(intent);
                    finish();
                }
            }
        }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }
    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }



    // Check if Google Playservices is installed in Device or not
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(
                        applicationContext,
                        "This device doesn't support Play services, App will not work normally",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {

        }
        return true;
    }

    // When Application is resumed, check for Play services support to make sure
    // app will be running normally
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }


}
