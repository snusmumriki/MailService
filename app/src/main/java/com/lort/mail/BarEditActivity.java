package com.lort.mail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class BarEditActivity extends AppCompatActivity {

    Task task;
    String bar;
    Barcode barcode;

    EditText barNum;
    EditText barName;
    EditText barAddress;
    EditText barContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barNum = (EditText) findViewById(R.id.bar_num_bar);
        barName = (EditText) findViewById(R.id.bar_name);
        barAddress = (EditText) findViewById(R.id.bar_address);
        barContact = (EditText) findViewById(R.id.bar_contact);

        try {
            task = (Task) getIntent().getParcelableExtra("task");
            getSupportActionBar().setTitle(task.getName());
        } catch (Exception e) {
            getSupportActionBar().setTitle("Редактировать накладную");
        }


        if (getIntent().getParcelableExtra("barcode") != null) {
            try {
                barcode = (Barcode) getIntent().getParcelableExtra("barcode");
                barNum.setText(barcode.getBar());
                barName.setText(barcode.getName());
                barContact.setText(barcode.getContact());
                barAddress.setText(barcode.getAddress());
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        } else if (getIntent().getParcelableExtra("bar") != null) {
            try {
                bar = getIntent().getStringExtra("bar");
                barNum.setText(bar);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
