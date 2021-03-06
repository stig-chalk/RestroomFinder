package com.example.cs125;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class GalleryActivity extends AppCompatActivity implements LocationListener{
    private LocationManager locationManager;
    String user_location;
    String destination;
    String[] busy_level ={"Very Free", "Free", "Normal","Slightly Busy", "Busy", "Very Busy", };
    String[] clean_level ={"Very Dirty", "Dirty", "Slightly Dirty", "Normal", "Clean", "Very Clean"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);
        getIncomingIntent();

        ImageButton button_direction = findViewById(R.id.button_direction);


        button_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayTrack();
            }
        });
    }

    private void getIncomingIntent(){
        String name = getIntent().getStringExtra("name");
        String addr = getIntent().getStringExtra("addr");
        float busy = getIntent().getFloatExtra("busy",0);
        float clean = getIntent().getFloatExtra("clean",0);
        String rating = getIntent().getStringExtra("rating");
        System.out.println("abd: "+rating);
        boolean accessTlt = getIntent().getBooleanExtra("accessTlt",true);
        boolean genInclus = getIntent().getBooleanExtra("genInclus",true);
        boolean soap = getIntent().getBooleanExtra("soap",true);
        boolean paper = getIntent().getBooleanExtra("paper",true);
        Integer red = getIntent().getIntExtra("red",155);
        Integer green = getIntent().getIntExtra("green",155);
        Integer blue = getIntent().getIntExtra("blue",155);
        String id = getIntent().getStringExtra("id");
        double distance = getIntent().getDoubleExtra("distance",0);
        setUpView(name, addr, busy, clean, rating, accessTlt, genInclus, soap, paper,red ,green, blue,distance, id);
    }

    private void setUpView(final String name,
                           final String addr,
                           Float busy,
                           Float clean,
                           String rating,
                           Boolean accessTlt,
                           Boolean genInclus,
                           Boolean soap,
                           Boolean paper,
                           final Integer red, final Integer green, final Integer blue,
                           Double distance,
                           final String id){

        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP, new int[] {Color.rgb(255,255,255), Color.rgb(red,green,blue)
        });
//        get the value of each factor
//        we already sort them by some factors in the backend so we only need to display them with the same order
        drawable.setAlpha(75);
        LinearLayout layout = findViewById(R.id.layout_gallery);
        layout.setBackground(drawable);

        TextView n = findViewById(R.id.name_gallery);
        n.setText(name);

        TextView a = findViewById(R.id.addr_gallery);
        a.setText(addr);

        TextView d = findViewById(R.id.distance_gallery);
        d.setText(distance+ " miles");

        TextView star_busy = findViewById(R.id.rating_busy_gallery);
        star_busy.setText(busy_level[Math.round(busy)]);

        TextView star_clean = findViewById(R.id.rating_clean_gallery);
        star_clean.setText(clean_level[Math.round(clean)]);

        RatingBar star_rating = findViewById(R.id.rating_gallery);
        star_rating.setRating(Float.parseFloat(rating));
        star_rating.setClickable(false);

        CheckBox check_at = findViewById(R.id.check_at);
        check_at.setChecked(accessTlt);
        check_at.setClickable(false);

        CheckBox check_gi = findViewById(R.id.check_gi);
        check_gi.setChecked(genInclus);
        check_gi.setClickable(false);

        CheckBox check_soap = findViewById(R.id.check_soap);
        check_soap.setChecked(soap);
        check_soap.setClickable(false);

        CheckBox check_paper = findViewById(R.id.check_paper);
        check_paper.setChecked(paper);
        check_paper.setClickable(false);

        ImageButton button_rate = findViewById(R.id.button_rate);
        button_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GalleryActivity.this, RatingActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("addr", addr);
                intent.putExtra("id", id);
                intent.putExtra("red", red);
                intent.putExtra("green", green);
                intent.putExtra("blue", blue);

                startActivity(intent);
            }
        });

        destination = addr;

    }

    @Override
    public void onLocationChanged(Location location){
//        get current user Latitude and Longitude and combine as a String, use it later in the url
        user_location = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());

//        if will jump to the google map if user have one and show the navigation
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + user_location + "/" + destination);
            System.out.println(uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException e){
//            if user don't have google map it will redirect to the google map store
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void DisplayTrack(){
//        ask for permission to get the location
        if (ContextCompat.checkSelfPermission(GalleryActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(GalleryActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        getLocation();
    }


    @SuppressLint("MissingPermission")
//    get current location
    private void getLocation(){
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, GalleryActivity.this);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

