package andyfolders.com.csc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class gcmnotify extends Activity {

    TextView emailET;
    private AQuery aq;
    // Progress Dialog bar object
    ProgressDialog prgDialog;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcmnotification);
        aq = new AQuery(this);
        String json = getIntent().getStringExtra("greetjson");
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);


        emailET = (TextView) findViewById(R.id.greetingmsg);
        emailET.setMovementMethod(new ScrollingMovementMethod());
        // Check if Google Play Service is installed in Device
        // Play services is needed to handle GCM stuffs
        if (!checkPlayServices()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Rate and Share our APP",
                    Toast.LENGTH_LONG).show();
        }

        // When json is not null
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("greetimgurl", jsonObj.getString("greetImgURL"));
                editor.putString("greetmsg", jsonObj.getString("greetMsg"));
                editor.commit();

                emailET.setText(prefs.getString("greetmsg", ""));
                // Render Image read from Image URL using aquery 'image' method
				/*aq.id(R.id.greetimg)
						.progress(R.id.progress)
						.image(prefs.getString("greetimgurl", ""), true, true,
								0, 0, null, AQuery.FADE_IN);*/
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        // When Json is null
        else if (!"".equals(prefs.getString("greetimgurl", ""))	&& !"".equals(prefs.getString("greetmsg", "") != null)) {
            emailET.setText(prefs.getString("greetmsg", ""));
			/*aq.id(R.id.greetimg)
					.progress(R.id.progress)
					.image(prefs.getString("greetimgurl", ""), true, true, 0,
							0, null, AQuery.FADE_IN);*/
        }

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String url = "market://search?q=andyfolders";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


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
                        getApplicationContext(),
                        "This device doesn't support Play services, App will not work normally",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Rate and Share our APP",
                    Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed()
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }


}
