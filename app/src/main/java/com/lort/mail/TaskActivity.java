package com.lort.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private static final int BARCODE_CAPTURE = 0;
    private static final int FORM_EDIT = 1;
    Task task;

    AlertDialog levelDialog;

    private EditText addressField;
    private RecyclerView formsField;
    private EditText timeField;
    private EditText phoneField;
    private EditText contactField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        formsField = (RecyclerView) findViewById(R.id.rv_bar);
        addressField = (EditText) findViewById(R.id.task_address);
        timeField = (EditText) findViewById(R.id.task_time);
        phoneField = (EditText) findViewById(R.id.task_phone);
        contactField = (EditText) findViewById(R.id.task_contact);

        task = getIntent().getParcelableExtra("task");
        List<Form> forms = task.getForms();
        formsField.setLayoutManager(new LinearLayoutManager(this));
        forms.add(new Form());
        FormAdapter adapter = new FormAdapter(forms);
        formsField.setAdapter(adapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        formsField.setItemAnimator(itemAnimator);

        getSupportActionBar().setTitle(task.getName());
        addressField.setText(task.getAddress());
        timeField.setText(task.getTime());
        phoneField.setText(task.getPhone());
        contactField.setText(task.getContact());

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_scan:
                    startActivityForResult(
                            new Intent(this, BarcodeCaptureActivity.class), BARCODE_CAPTURE);
                    return true;
                case R.id.action_input:
                    startActivityForResult(new Intent(TaskActivity.this, FormEditActivity.class)
                            .putExtra("title", task.getName()), FORM_EDIT);
                    return true;
                default:
                    return true;
            }
        });
        switch (task.getStatus()) {
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
        fab.setOnClickListener(view -> {
            String[] items = new String[]{"Ожидает выполнения", "В процессе выполнения", "Выполнен"};

            AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
            builder.setTitle("Выберите новый статус заявки");
            builder.setSingleChoiceItems(items, -1, (dialog, item) -> {
                switch (item) {
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
            });
            levelDialog = builder.create();
            levelDialog.show();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BARCODE_OBJECT);
                    startActivity(new Intent(TaskActivity.this, FormEditActivity.class)
                            .putExtra("title", task.getName()).putExtra("barcode", barcode.displayValue));
                    Log.d("TAG", "Barcode read: " + barcode.displayValue);
                } else {
                    Log.d("TAG", "No form captured, intent data is null");
                }
            } else {

            }
        } else {

        }
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
            Intent intent = new Intent(TaskActivity.this, TaskEditActivity.class);
            intent.putExtra("task", task);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}