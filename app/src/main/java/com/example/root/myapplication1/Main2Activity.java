package com.example.root.myapplication1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Main2Activity extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    EditText name,email,phn,hr;
    Button bt;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    String getLati,getLongi;
    Usersession session;
   // int pm=0;
    /*private static final String prefername="Reg";
    private static final String isuserlogin="isuserlogin";
    private static final String keyname="name";
    private static final String keyemail="email";
    private static final String keyphonenumber="phonenumber";
    private static final String keyhr="homeradius";
    private static final String keylati="latitude";
    private static final String keylongi="longitude";*/
    /*SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
    editor =  sharedPreferences.edit();
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //final SQLDatabaseHandler db=new SQLDatabaseHandler(this);
        name=(EditText)findViewById(R.id.editText5);
        email=(EditText)findViewById(R.id.editText6);
        phn=(EditText)findViewById(R.id.editText7);
        hr=(EditText)findViewById(R.id.editText9);
        bt=(Button)findViewById(R.id.button);
        session=new Usersession(getApplicationContext());
        /*sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
// get editor to edit in file
        editor = sharedPreferences.edit();*/
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  uname=name.getText().toString();
                String uemail=email.getText().toString();
                String uphn=phn.getText().toString();
                String uhr=hr.getText().toString();

                /*editor.putString("name", uname);
                editor.putString("email",uemail);
                editor.putString("phonenumber",uphn);
                editor.putString("homeradius", uhr);
                editor.putString("latitude",getLati);
                editor.putString("longitude",getLongi);
                editor.putBoolean("isuserlogin",true);
                editor.commit();
*/
                session.createUserLoginSession(uname,uemail,uphn,uhr,getLati,getLongi);
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();




                /*db.addDetails(new SQLloca(name.getText().toString(),email.getText().toString(),phn.getText().toString(),hr.getText().toString(),getLati,getLongi));
                name.setText("");
                email.setText("");
                phn.setText("");
                hr.setText("");
                Log.v("Added ",name.getText().toString());
                Intent i=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);*/
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        getLati=String.valueOf(currentLatitude);
        getLongi=String.valueOf(currentLongitude);

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            getLati=String.valueOf(currentLatitude);
            getLongi=String.valueOf(currentLongitude);


            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }
}
