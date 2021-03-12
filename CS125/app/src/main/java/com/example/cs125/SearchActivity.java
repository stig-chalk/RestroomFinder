package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<String> mRanks = new ArrayList<String>();
    private ArrayList<String> mNames = new ArrayList<String>();
    private ArrayList<String> mAddresses= new ArrayList<String>();
    private ArrayList<String> mRatings= new ArrayList<String>();

    static class Restroom{
         ArrayList<String> mRanks;
         ArrayList<String> mNames;
         ArrayList<String> mAddresses;
         ArrayList<String> mRatings;
         ArrayList<Boolean> accessTlt;
         ArrayList<Boolean> genInclus;
         ArrayList<Boolean> soap;
         ArrayList<Boolean> paper;
         ArrayList<Float> busy;
         ArrayList<Float> clean;

        public Restroom(){
            mRanks = new ArrayList<String>();
            mNames = new ArrayList<String>();
            mAddresses= new ArrayList<String>();
            mRatings= new ArrayList<String>();
            accessTlt = new ArrayList<Boolean>();
            genInclus = new ArrayList<Boolean>();
            soap= new ArrayList<Boolean>();
            paper= new ArrayList<Boolean>();
            busy = new ArrayList<Float>();
            clean = new ArrayList<Float>();
        }
        public Restroom(Restroom restroom) {
            mRanks = restroom.mRanks;
            mNames = restroom.mNames;
            mAddresses = restroom.mAddresses;
            mRatings = restroom.mRatings;
            accessTlt = restroom.accessTlt;
            genInclus = restroom.genInclus;
            soap = restroom.soap;
            paper = restroom.paper;
            clean = restroom.clean;
            busy = restroom.busy;
        }
    }
    Restroom restroom = new Restroom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initContext();
    }

    private void initContext(){
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "http://ec2-100-24-72-207.compute-1.amazonaws.com:8080/search/unweighted;";

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("lat", String.valueOf(122));
        builder.appendQueryParameter("lon", String.valueOf(133));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("detail");
                            System.out.println(response);
                            for(int i=0; i<jsonArray.length(); i++) {
                                restroom.mRanks.add(jsonArray.getJSONObject(i).getString("name").substring(0,1));
                                restroom.mNames.add(jsonArray.getJSONObject(i).getString("name"));
                                restroom.mAddresses.add(jsonArray.getJSONObject(i).getString("addr"));
                                restroom.mRatings.add(jsonArray.getJSONObject(i).getString("rating").substring(0,3));
                                restroom.accessTlt.add(jsonArray.getJSONObject(i).getBoolean("accessTlt"));
                                restroom.genInclus.add(jsonArray.getJSONObject(i).getBoolean("genInclus"));
                                restroom.soap.add(jsonArray.getJSONObject(i).getBoolean("soap"));
                                restroom.paper.add(jsonArray.getJSONObject(i).getBoolean("paper"));
                                restroom.clean.add((float)jsonArray.getJSONObject(i).getDouble("clean"));
                                restroom.busy.add((float)jsonArray.getJSONObject(i).getDouble("busy"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(restroom.clean);
                        System.out.println(restroom.paper);
                        initRecyclerView();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Sign up.error", error.toString());
            }
        });

        queue.add(request);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycleView);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, restroom);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }
}