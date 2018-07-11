package com.example.md66805.sharingiscaring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.md66805.sharingiscaring.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText racfId;
    Spinner spinner;
    Button loginButton;
    boolean doubleBackToExitPressedOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        getSupportActionBar().hide();

        if("True".equalsIgnoreCase(getIntent().getStringExtra("Exit"))) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }

        spinner = findViewById(R.id.loginSpinner);
        List<String> domains = new ArrayList<>();
        domains.add("Please select your domain");
        domains.add("CAP");
        domains.add("PDP");
        domains.add("MPS");
        domains.add("OFP");
        domains.add("JDF");
        domains.add("DCE");
        domains.add("FiHRE");
        domains.add("GHNS");
        domains.add("DT");
        domains.add("CTS");
        domains.add("CPS");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, domains);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                racfId = findViewById(R.id.textViewRacfId);
                spinner = (Spinner) findViewById(R.id.loginSpinner);
                if (!racfId.getText().toString().isEmpty() && spinner.getSelectedItem()!="Please select your domain") {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                    intent.putExtra("racfId", racfId.getText().toString().toUpperCase().trim());
                    intent.putExtra("domain",String.valueOf(spinner.getSelectedItem()).trim());
                    startActivity(intent);
                } else if(racfId.getText().toString().isEmpty()) {
                    Snackbar.make(view,"Please enter your Racf Id!!!",Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view,"Please select your Domain!!!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
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
        Snackbar.make(findViewById(R.id.loginActivity), "Please click Back again to Exit!", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
