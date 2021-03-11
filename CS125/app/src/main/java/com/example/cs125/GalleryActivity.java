package com.example.cs125;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIncomingIntent();
    }

    private void getIncomingIntent(){

        String name = getIntent().getStringExtra("name");
        String addr = getIntent().getStringExtra("addr");
        float busy = getIntent().getFloatExtra("busy",0);
        float clean = getIntent().getFloatExtra("clean",0);
        boolean accessTlt = getIntent().getBooleanExtra("accessTlt",true);
        boolean genInclus = getIntent().getBooleanExtra("genInclus",true);
        boolean soap = getIntent().getBooleanExtra("soap",true);
        boolean paper = getIntent().getBooleanExtra("paper",true);
        Integer red = getIntent().getIntExtra("red",155);
        Integer green = getIntent().getIntExtra("green",155);
        Integer blue = getIntent().getIntExtra("blue",155);

        setUpView(name, addr, busy, clean, accessTlt, genInclus, soap, paper,red ,green, blue);
    }

    private void setUpView(String name,
                           String addr,
                           Float busy,
                           Float clean,
                           Boolean accessTlt,
                           Boolean genInclus,
                           Boolean soap,
                           Boolean paper,
                           Integer red, Integer green, Integer blue){

        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP, new int[] {Color.rgb(255,255,255), Color.rgb(red,green,blue)
        });

        drawable.setAlpha(75);
        LinearLayout layout = findViewById(R.id.layout_gallery);
        layout.setBackground(drawable);

        TextView n = findViewById(R.id.name_gallery);
        n.setText(name);

        TextView a = findViewById(R.id.addr_gallery);
        a.setText(addr);

        RatingBar star_busy = findViewById(R.id.rating_busy_gallery);
        star_busy.setRating(busy);
        star_busy.setIsIndicator(true);

        RatingBar star_clean = findViewById(R.id.rating_clean_gallery);
        star_clean.setRating(clean);
        star_clean.setIsIndicator(true);

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

        System.out.println("1"+busy);
        System.out.println("2"+clean);
        System.out.println("3"+accessTlt);
        System.out.println("4"+genInclus);
        System.out.println("5"+paper);
        System.out.println("6"+soap);
    }


}

