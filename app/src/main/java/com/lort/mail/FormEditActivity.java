package com.lort.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.common.api.CommonStatusCodes;

public class FormEditActivity extends AppCompatActivity {
    EditText barNum;
    EditText barName;
    EditText barAddress;
    EditText barContact;

    Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        barNum = (EditText) findViewById(R.id.bar_num_bar);
        barName = (EditText) findViewById(R.id.bar_name);
        barAddress = (EditText) findViewById(R.id.bar_address);
        barContact = (EditText) findViewById(R.id.bar_contact);

        String title = getIntent().getStringExtra("title");
        if (title == null) title = "Редактировать накладную";
        getSupportActionBar().setTitle(title);

        toolbar.setNavigationOnClickListener(v -> {
            form.setBar(barNum.getText().toString());
            form.setName(barNum.getText().toString());
            form.setContact(barNum.getText().toString());
            form.setAddress(barNum.getText().toString());
            setResult(CommonStatusCodes.SUCCESS, new Intent().putExtra("form", form));
            finish();
        });

        if ((form = getIntent().getParcelableExtra("form")) != null) {
            barNum.setText(form.getBar());
            barName.setText(form.getName());
            barContact.setText(form.getContact());
            barAddress.setText(form.getAddress());
        } else {
            form = new Form();
            form.setBar(getIntent().getStringExtra("barcode"));
            barNum.setText(form.getBar());
        }
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
