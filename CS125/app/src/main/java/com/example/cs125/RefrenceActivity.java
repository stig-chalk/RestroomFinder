package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RefrenceActivity extends AppCompatActivity {
    private RatingBar rating_accessTlt;
    private RatingBar rating_busy;
    private RatingBar rating_clean;
    private RatingBar rating_genInclus;
    private RatingBar rating_paper;
    private RatingBar rating_soap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrence);
        rating_accessTlt = findViewById(R.id.rating_accessTlt);
        rating_busy = findViewById(R.id.rating_busy);
        rating_clean = findViewById(R.id.rating_clean);
        rating_genInclus = findViewById(R.id.rating_genInclus);
        rating_paper = findViewById(R.id.rating_paper);
        rating_soap = findViewById(R.id.rating_soap);

        Button submit = findViewById(R.id.Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });
    }

    public void submit() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "http://ec2-100-24-72-207.compute-1.amazonaws.com:8080/user/setPrefer";

        String accessTlt = String.valueOf((int)rating_accessTlt.getRating());
        String busy = String.valueOf((int)rating_busy.getRating());
        String clean = String.valueOf((int)rating_clean.getRating());
        String genInclus = String.valueOf((int)rating_genInclus.getRating());
        String paper = String.valueOf((int)rating_paper.getRating());
        String soap = String.valueOf((int)rating_soap.getRating());

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("accessTlt", accessTlt);
        builder.appendQueryParameter("busy", busy);
        builder.appendQueryParameter("clean", clean);
        builder.appendQueryParameter("genInclus", genInclus);
        builder.appendQueryParameter("paper", paper);
        builder.appendQueryParameter("soap", soap);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Intent intent = new Intent(RefrenceActivity.this, GalleryActivity.class);
                            String success = response.getString("success");
                            if (success.equals("true")){
                                String msg = response.getString("msg");
                                intent.putExtra("weighted",true);
                                Toast.makeText(RefrenceActivity.this,msg, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else{
                                String msg = response.getString("msg");
                                Log.d("login.error", msg);
                                Toast.makeText(RefrenceActivity.this,msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Sign up.error", error.toString());
            }
        });

        queue.add(request);
    }
}
