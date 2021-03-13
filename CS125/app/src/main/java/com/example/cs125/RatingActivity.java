package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RatingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        String name = getIntent().getStringExtra("name");
        String addr = getIntent().getStringExtra("addr");
        String id = getIntent().getStringExtra("id");
        Integer red = getIntent().getIntExtra("red",155);
        Integer green = getIntent().getIntExtra("green",155);
        Integer blue = getIntent().getIntExtra("blue",155);

        setUpView(name, addr,red ,green, blue,id);


    }
    private void setUpView(final String name,
                           final String addr,
                           Integer red, Integer green, Integer blue,
                           final String id) {

        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.rgb(255, 255, 255), Color.rgb(red, green, blue)
        });

        drawable.setAlpha(75);
        LinearLayout layout = findViewById(R.id.layout_rating);
        layout.setBackground(drawable);

        TextView n = findViewById(R.id.name_rating);
        n.setText(name);

        TextView a = findViewById(R.id.addr_rating);
        a.setText(addr);

        Button button = findViewById(R.id.submit_rating);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(id);
            }
        });
    }
    public void submit(String id) {
        Spinner rating_clean =findViewById(R.id.rating_clean_rating);
        Spinner rating_busy = findViewById(R.id.rating_busy_rating);
        CheckBox check_gi = findViewById(R.id.rating_check_gi);
        CheckBox check_at = findViewById(R.id.rating_check_at);
        CheckBox check_paper = findViewById(R.id.rating_check_paper);
        CheckBox check_soap = findViewById(R.id.rating_check_soap);
        RatingBar ratingBar = findViewById(R.id.rating_rating);

        String busy = "";
        String busyString = rating_busy.getSelectedItem().toString();
        switch (busyString){
            case "Very Busy":
                busy = "5.0";
                break;
            case "Busy":
                busy = "4.0";
                break;
            case "Slightly Busy":
                busy = "3.0";
                break;
            case "Normal":
                busy = "2.0";
                break;
            case "Free":
                busy = "1.0";
                break;
            case "Very Free":
                busy = "0.0";
                break;
            default:
                busy = "6.0";
        }

        String clean = "";
        String cleanString = rating_clean.getSelectedItem().toString();
        switch (cleanString){
            case "Very Clean":
                clean = "5.0";
                break;
            case "Clean":
                clean = "4.0";
                break;
            case "Normal":
                clean = "3.0";
                break;
            case "Slightly Dirty":
                clean = "2.0";
                break;
            case "Dirty":
                clean = "1.0";
                break;
            case "Very Dirty":
                clean = "0.0";
                break;
            default:
                clean = "6.0";
        }


        String sbusy = busy;
        String sclean = clean;
        String genInclus = String.valueOf(check_gi.isChecked());
        String accessTlt = String.valueOf(check_at.isChecked());
        String paper = String.valueOf(check_paper.isChecked());
        String soap = String.valueOf(check_soap.isChecked());
        String rating = String.valueOf(ratingBar.getRating());


        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        String url = "http://ec2-100-24-72-207.compute-1.amazonaws.com:8080/feedback/add";
        Uri.Builder builder = Uri.parse(url).buildUpon();
//        set parameter for url
        builder.appendQueryParameter("accessTlt", accessTlt);
        builder.appendQueryParameter("busy", sbusy);
        builder.appendQueryParameter("clean", sclean);
        builder.appendQueryParameter("genInclus", genInclus);
        builder.appendQueryParameter("id", id);
        builder.appendQueryParameter("paper", paper);
        builder.appendQueryParameter("rating", rating);
        builder.appendQueryParameter("soap", soap);


        System.out.println(builder.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String success = response.getString("success");
//                            if will get the success massage if "success" is true
                            if (success.equals("true")){
                                String msg = response.getString("msg");
                                Toast.makeText(RatingActivity.this,msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RatingActivity.this, SearchActivity.class);
                                intent.putExtra("weighted",true);
                                startActivity(intent);
                            }
//                            otherwise it will show the error massage
                            else{
                                String msg = response.getString("msg");
                                Log.d("SUBMIT.error", msg);
                                Toast.makeText(RatingActivity.this,msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Feedback.error", error.toString());
            }
        });

        queue.add(request);
    }

}