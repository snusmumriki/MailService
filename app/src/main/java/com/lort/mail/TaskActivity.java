package com.lort.mail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    Task task;

    AlertDialog levelDialog;

    private EditText addressField;
    private RecyclerView barcodesField;
    private EditText timeField;
    private EditText phoneField;
    private EditText contactField;

    ArrayList<Barcode> barcodes;

    private String m_Text_bar = "";
    private String m_Text_name = "";
    private String m_Text_address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        task = (Task) getIntent().getParcelableExtra("task");

        String name = task.getName();
        final String address = task.getAddress();
        String time = task.getTime();
        String status = task.getStatus();
        barcodes = task.getBarcodes();
        String phone = task.getPhone();
        String contact = task.getContact();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcodesField = (RecyclerView) findViewById(R.id.rv_bar);
        addressField = (EditText) findViewById(R.id.task_address);
        timeField = (EditText) findViewById(R.id.task_time);
        phoneField = (EditText) findViewById(R.id.task_phone);
        contactField = (EditText) findViewById(R.id.task_contact);

        barcodesField.setLayoutManager(new LinearLayoutManager(this));
        barcodes.add(new Barcode());
        BarcodeAdapter adapter = new BarcodeAdapter(barcodes);
        barcodesField.setAdapter(adapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        barcodesField.setItemAnimator(itemAnimator);

        getSupportActionBar().setTitle(name);
        addressField.setText(address);
        timeField.setText(time);
        phoneField.setText(phone);
        contactField.setText(contact);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_scan:
                                onClick(bottomNavigationView);
                                return true;
                            case R.id.action_input:
                                Intent intent = new Intent(TaskActivity.this, BarEditActivity.class);
                                intent.putExtra("task", task);
                                startActivity(intent);
                                return true;
                            default:
                                return true;
                        }
                    }
                });
        switch (status) {
            case "wait":
                //toolbar.setBackgroundColor(Color.RED);
                toolbar.setLogo(R.drawable.red);
                break;
            case "progress":
                //toolbar.setBackgroundColor(Color.YELLOW);
                toolbar.setLogo(R.drawable.yellow);
                break;
            case "done":
                //toolbar.setBackgroundColor(Color.GREEN);
                toolbar.setLogo(R.drawable.green);
                break;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Strings to Show In Dialog with Radio Buttons
                final CharSequence[] items = {"Ожидает выполнения","В процессе выполнения","Выполнен"};

                // Creating and Building the Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                builder.setTitle("Выберите новый статус заявки");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item)
                        {
                            case 0:
                                task.setStatus("wait");
                                break;
                            case 1:
                                task.setStatus("progress");
                                break;
                            case 2:
                                task.setStatus("done");
                                break;
                        }
                        levelDialog.dismiss();
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            try {
                if (!scanContent.equals(null)) {
                    Intent intentEdit = new Intent(TaskActivity.this, BarEditActivity.class);
                    intent.putExtra("task", task);
                    intent.putExtra("bar", scanContent);
                    startActivity(intentEdit);

                    }
            } catch (Exception e) {}
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Intent intent = new Intent(TaskActivity.this, EditActivity.class);
            intent.putExtra("task", task);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}