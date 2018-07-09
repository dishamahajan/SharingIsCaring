package com.example.md66805.sharingiscaring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.md66805.sharingiscaring.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText racfId;
    Spinner spinner;
    Button loginButton;
    Toast toast;

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
                    Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                    intent.putExtra("racfId", racfId.getText().toString());
                    intent.putExtra("domain",String.valueOf(spinner.getSelectedItem()));
                    startActivity(intent);
                } else if(racfId.getText().toString().isEmpty()) {
                    showToast("Please enter your Racf Id!!!");
                    //Snackbar.make(view,"Please enter your Racf Id!!!!!",Snackbar.LENGTH_SHORT).show();
                } else {
                    showToast("Please select your Domain!!!");
                    //Snackbar.make(view,"Please select your Domain!!!!!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
