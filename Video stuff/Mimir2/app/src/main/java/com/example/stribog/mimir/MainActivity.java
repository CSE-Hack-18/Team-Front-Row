package com.example.stribog.mimir;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.vidyo.VidyoClient.Connector.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements Connector.IConnect, GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private Connector vc;
    private FrameLayout videoFrame;

    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView debug;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeTextView = (TextView) findViewById((R.id.latitude_textview));
        mLongitudeTextView = (TextView) findViewById((R.id.longitude_textview));
        debug = (TextView) findViewById(R.id.debug);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        geocoder = new Geocoder(getBaseContext());

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);

    }

    public void Start(View v){

        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 0, "", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
        vc.cycleCamera();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ourToken = "cHJvdmlzaW9uAENhbGxlckAyNzc2YzEudmlkeW8uaW8ANjM2OTI4NDQyODEAADZiNGQzYzc5ZjE4ZTVjOWI3ZDNmMmU5Y2QwMWRkYzFiYTU1ZTAyYWY3YjYxMjZjNDRjY2I4OGRhZmQ1YTk5OTRjNWVmZDI5OTYxMjJiM2E3M2YwMWE3N2ZiOTMyNDBmYQ==";
        vc.connect("prod.vidyo.io", ourToken, "Caller", "DemoRoom", this);
    }

    public void Connect(View v) {
        //String ourToken = "cHJvdmlzaW9uAENhbGxlckAyNzc2YzEudmlkeW8uaW8ANjM2OTI4NDQyODEAADZiNGQzYzc5ZjE4ZTVjOWI3ZDNmMmU5Y2QwMWRkYzFiYTU1ZTAyYWY3YjYxMjZjNDRjY2I4OGRhZmQ1YTk5OTRjNWVmZDI5OTYxMjJiM2E3M2YwMWE3N2ZiOTMyNDBmYQ==";
        //vc.connect("prod.vidyo.io", ourToken, "Caller", "DemoRoom", this);
    }

    public void Disconnect(View v){
        vc.disconnect();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
        System.out.println("TIMED OUT TIMED OUT TIMED OUT ---------------------------------------------------------");
        if(connectorFailReason == Connector.ConnectorFailReason.VIDYO_CONNECTORFAILREASON_ConnectionTimeout){

            System.out.println("RECONNECTINGGGGGG    ---------------------------------------------------------");
            this.Connect(videoFrame);
        }
    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {

    }


    private LocationListener locationListener = null;

    private static final String TAG = "Debug";
    private Boolean flag = false;
    private TextView locationText = null;
    LocationManager locationManager = null;

    public void getLocation(View v){

    }

    private static Boolean displayGpsStatus(Context ctx) {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        return lm != null &&
                (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            mLatitudeTextView.setText("Failed. Lacks permission.");
            return;
        }
            startLocationUpdates();
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(mLocation == null){
                startLocationUpdates();
            }
            if (mLocation != null) {
                 mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
                mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
            } else {
                Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(3000);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "Did not receive location";
        try {
            String address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getAddressLine(0);
            debug.setText(address);
            msg = "Address" + address;


        }catch (Exception e){debug.setText("Failed to get address");
            msg = "Updated Location: " +
                    Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude());
            mLatitudeTextView.setText("Lat:"+ String.valueOf(location.getLatitude()));
            mLongitudeTextView.setText("Lon:"+String.valueOf(location.getLongitude() ));
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        /*final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();*/
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
