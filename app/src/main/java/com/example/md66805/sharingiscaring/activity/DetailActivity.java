package com.example.md66805.sharingiscaring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.Utility;
import com.example.md66805.sharingiscaring.adapter.ListItemAdapter;
import com.example.md66805.sharingiscaring.domain.ItemDetails;
import com.example.md66805.sharingiscaring.domain.ListItem;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String racfId;
    String domain;
    ListItem listItem;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        listItem = (ListItem) i.getSerializableExtra("detail");
        goToLoginPage(listItem);
        racfId = listItem.getRacf();
        domain = listItem.getDomain();
        Utility.ActionBarSetup(racfId + " from " + domain, getSupportActionBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recyclerViewDetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListItemAdapter(listItem.getItemDetails(), racfId, this, findViewById(R.id.activityDetail), progressBar);
        recyclerView.setAdapter(adapter);
    }

    private void goToLoginPage(ListItem listItem) {
        if (listItem == null || listItem.getRacf() == null || listItem.getRacf().isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
        intent.putExtra("racfId", racfId);
        intent.putExtra("domain", domain);
        startActivity(intent);
        return true;
    }

    public void onBackPressed() {
        finish();
        Intent launchNextActivity;
        launchNextActivity = new Intent(this, ItemActivity.class);
        launchNextActivity.putExtra("racfId", racfId);
        launchNextActivity.putExtra("domain", domain);
        startActivity(launchNextActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private List<ItemDetails> getDevices(List<ItemDetails> itemDetails, String userID) {
        List<ItemDetails> myDevices = new ArrayList<>();
        for (ItemDetails itemDetail :
                itemDetails) {
            if (itemDetail.getRacfId().equalsIgnoreCase(userID)) {
                myDevices.add(itemDetail);
            }
        }
        return myDevices;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.all_devices:
                adapter = new ListItemAdapter(listItem.getItemDetails(), racfId, this, findViewById(R.id.activityDetail),progressBar);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.my_devices:
                adapter = new ListItemAdapter(getDevices(listItem.getItemDetails(), racfId), racfId, this, findViewById(R.id.activityDetail),progressBar);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.available_devices:
                adapter = new ListItemAdapter(getDevices(listItem.getItemDetails(), "Admin"), racfId, this, findViewById(R.id.activityDetail),progressBar);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.logout:
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
