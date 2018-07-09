package com.example.md66805.sharingiscaring.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.md66805.sharingiscaring.Utility;
import com.example.md66805.sharingiscaring.adapter.MyAdapter;
import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.domain.ItemDetails;
import com.example.md66805.sharingiscaring.domain.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    boolean doubleBackToExitPressedOnce = false;
    private List<ListItem> listItems;
    String racfId;
    String domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        racfId = getIntent().getStringExtra("racfId");
        domain = getIntent().getStringExtra("domain");
        goToLoginPage(racfId);

        Utility.ActionBarSetup(racfId+" from "+domain, getSupportActionBar());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        getResponseText("https://api.jsonbin.io/b/5b3f7459a5a2f34ea6b20884/12");
    }

    private void goToLoginPage(String racfId) {
        if (racfId == null || racfId.isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    private void getResponseText(String stringUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                stringUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("devices");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                List<ItemDetails> itemDetails = new ArrayList<>();
                                JSONArray itemArray = object.getJSONArray("details");
                                for (int j = 0; j < itemArray.length(); j++) {
                                    JSONObject itemObject = itemArray.getJSONObject(j);
                                    ItemDetails itemDetail = new ItemDetails(
                                            itemObject.getString("Extn"),
                                            itemObject.getString("name"),
                                            itemObject.getString("domain"),
                                            itemObject.getString("type")
                                    );
                                    itemDetails.add(itemDetail);
                                }
                                ListItem listItem = new ListItem(object.getString("name"), String.valueOf(object.getJSONArray("details").length()), racfId, domain, itemDetails);
                                listItems.add(listItem);
                            }
                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent launchNextActivity;
            launchNextActivity = new Intent(this, MainActivity.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            launchNextActivity.putExtra("Exit","True");
            startActivity(launchNextActivity);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to EXIT!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
