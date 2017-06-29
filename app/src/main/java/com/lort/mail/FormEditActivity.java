package com.lort.mail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FormEditActivity extends AppCompatActivity {

    Task task;
    String bar;
    Form form;

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

        toolbar.setNavigationOnClickListener(v -> finish());

        barNum = (EditText) findViewById(R.id.bar_num_bar);
        barName = (EditText) findViewById(R.id.bar_name);
        barAddress = (EditText) findViewById(R.id.bar_address);
        barContact = (EditText) findViewById(R.id.bar_contact);

        task = getIntent().getParcelableExtra("task");
        getSupportActionBar().setTitle(task.getName());
        getSupportActionBar().setTitle("Редактировать накладную");

        if (getIntent().getParcelableExtra("form") != null) {
            form = getIntent().getParcelableExtra("form");
            barNum.setText(form.getBar());
            barName.setText(form.getName());
            barContact.setText(form.getContact());
            barAddress.setText(form.getAddress());
        } else if (getIntent().getParcelableExtra("form") != null) {
            bar = getIntent().getStringExtra("form");
            barNum.setText(bar);
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
