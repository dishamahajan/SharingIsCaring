package com.example.md66805.sharingiscaring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.Utility;
import com.example.md66805.sharingiscaring.adapter.ListItemAdapter;
import com.example.md66805.sharingiscaring.domain.ListItem;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String racfId;
    String domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        ListItem listItem = (ListItem) i.getSerializableExtra("detail");
        goToLoginPage(listItem);
        racfId = listItem.getRacf();
        domain = listItem.getDomain();
        Utility.ActionBarSetup(racfId + " from " + domain, getSupportActionBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewDetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListItemAdapter(listItem.getItemDetails(), racfId, this, findViewById(R.id.activityDetail));
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

}
