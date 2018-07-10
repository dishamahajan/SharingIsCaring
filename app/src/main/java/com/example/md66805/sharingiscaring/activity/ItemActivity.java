package com.example.md66805.sharingiscaring.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.Utility;
import com.example.md66805.sharingiscaring.adapter.MyAdapter;
import com.example.md66805.sharingiscaring.domain.ItemDetails;
import com.example.md66805.sharingiscaring.domain.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    boolean doubleBackToExitPressedOnce = false;
    private List<ListItem> listItems;
    String racfId;
    String domain;
    Button refresh;
    Toast toast;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        racfId = getIntent().getStringExtra("racfId");
        domain = getIntent().getStringExtra("domain");
        goToLoginPage(racfId);
        progressBar = findViewById(R.id.progressBar);
        Utility.ActionBarSetup(racfId + " from " + domain, getSupportActionBar());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        if(isMobileDataEnabled()) {
            getResponseText("http://204.54.27.233:7564/devices");
        }
        else{
            progressBar.setVisibility(View.GONE);
            refresh=findViewById(R.id.refreshButton);
            refresh.setVisibility(View.VISIBLE);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                    intent.putExtra("racfId", racfId);
                    intent.putExtra("domain",domain);
                    startActivity(intent);
                }
            });
            showToast("Please check your Internet Connection!");
        }
    }

    private void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goToLoginPage(String racfId) {
        if (racfId == null || racfId.isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    private void getResponseText(String stringUrl) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                stringUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("catalogue");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                List<ItemDetails> itemDetails = new ArrayList<>();
                                JSONArray itemArray = object.getJSONArray("deviceList");
                                for (int j = 0; j < itemArray.length(); j++) {
                                    JSONObject itemObject = itemArray.getJSONObject(j);
                                    ItemDetails itemDetail = new ItemDetails(
                                            itemObject.getString("serialNumber"),
                                            itemObject.getString("racfId"),
                                            itemObject.getString("owner"),
                                            itemObject.getString("domain"),
                                            itemObject.getString("model"),
                                            ""
                                    );
                                    itemDetails.add(itemDetail);
                                }
                                ListItem listItem = new ListItem(object.getString("type"), String.valueOf(object.getJSONArray("deviceList").length()), racfId, domain, itemDetails);
                                listItems.add(listItem);
                            }
                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        showToast("Error : Cannot Fetch Data!");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private boolean isMobileDataEnabled(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent launchNextActivity;
            launchNextActivity = new Intent(this, MainActivity.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            launchNextActivity.putExtra("Exit", "True");
            startActivity(launchNextActivity);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to EXIT!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
