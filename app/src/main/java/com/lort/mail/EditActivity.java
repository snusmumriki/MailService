package com.lort.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EditActivity extends AppCompatActivity {

    Task task;

    private EditText addressField;
    private EditText barcodesField;
    private EditText timeField;
    private EditText phoneField;
    private EditText contactField;
    private EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addressField = (EditText) findViewById(R.id.edit_address);
        timeField = (EditText) findViewById(R.id.edit_time);
        phoneField = (EditText) findViewById(R.id.edit_phone);
        contactField = (EditText) findViewById(R.id.edit_contact);
        nameField = (EditText) findViewById(R.id.edit_name);

        try {
            task = (Task) getIntent().getParcelableExtra("task");

            String name = task.getName();
            String address = task.getAddress();
            String time = task.getTime();
            String status = task.getStatus();
            String phone = task.getPhone();
            String contact = task.getContact();
            getSupportActionBar().setTitle(name);

            addressField.setText(address);
            timeField.setText(time);
            phoneField.setText(phone);
            contactField.setText(contact);

            nameField.setText(name);
        } catch (Exception ignored) {}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
