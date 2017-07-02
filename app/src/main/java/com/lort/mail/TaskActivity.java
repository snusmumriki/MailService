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

import com.google.android.gms.vision.barcode.Barcode;

import java.util.Arrays;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    public static final int BARCODE_CAPTURE = 1;
    public static final int FORM_ADD = 2;
    public static final int FORM_EDIT = 3;
    public static final int TASK_EDIT = 4;
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
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        formsField.setItemAnimator(itemAnimator);
        if (forms.size() != 0) {
            formsField.setAdapter(new FormAdapter(forms));
        }

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
                    startActivityForResult(new Intent(this, FormEditActivity.class)
                            .putExtra("title", task.getName()), FORM_ADD);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Выберите новый статус заявки")
                    .setSingleChoiceItems(items, -1, (dialog, item) -> {
                        task.setStatus(Arrays.asList("wait", "progress", "done").get(item));
                        levelDialog.dismiss();
                    });
            levelDialog = builder.create();
            levelDialog.show();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BARCODE_CAPTURE:
                Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BARCODE_OBJECT);
                Log.i("tag", "Barcode read: " + barcode.displayValue);
                startActivityForResult(new Intent(this, FormEditActivity.class)
                        .putExtra("title", task.getName())
                        .putExtra("barcode", barcode.displayValue), FORM_ADD);
                break;
            case FORM_ADD:
                task.getForms().add(data.getParcelableExtra("form"));
                formsField.setAdapter(new FormAdapter(task.getForms()));
                break;
            case FORM_EDIT:
                FormAdapter adapter = (FormAdapter) formsField.getAdapter();
                adapter.getForms().set(adapter.getLastIndex(), data.getParcelableExtra("form"));
                break;
            case TASK_EDIT:
                task = data.getParcelableExtra("task");
                getSupportActionBar().setTitle(task.getName());
                addressField.setText(task.getAddress());
                timeField.setText(task.getTime());
                phoneField.setText(task.getPhone());
                contactField.setText(task.getContact());
                break;
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
            startActivityForResult(
                    new Intent(this, TaskEditActivity.class).putExtra("task", task), TASK_EDIT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}