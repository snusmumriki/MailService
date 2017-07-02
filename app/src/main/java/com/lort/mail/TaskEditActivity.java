package com.lort.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class TaskEditActivity extends AppCompatActivity {
    private EditText addressField;
    private EditText barcodesField;
    private EditText timeField;
    private EditText phoneField;
    private EditText contactField;
    private EditText nameField;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addressField = (EditText) findViewById(R.id.edit_address);
        timeField = (EditText) findViewById(R.id.edit_time);
        phoneField = (EditText) findViewById(R.id.edit_phone);
        contactField = (EditText) findViewById(R.id.edit_contact);
        nameField = (EditText) findViewById(R.id.edit_name);

        task = getIntent().getParcelableExtra("task");
        getSupportActionBar().setTitle(task.getName());
        nameField.setText(task.getName());
        addressField.setText(task.getAddress());
        timeField.setText(task.getTime());
        phoneField.setText(task.getPhone());
        contactField.setText(task.getContact());
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
            task.setName(nameField.getText().toString());
            task.setAddress(addressField.getText().toString());
            task.setTime(timeField.getText().toString());
            task.setPhone(phoneField.getText().toString());
            task.setContact(contactField.getText().toString());
            setResult(RESULT_OK, new Intent().putExtra("task", task));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
