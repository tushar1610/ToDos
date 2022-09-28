package com.example.android.todos;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText newTask;
    private AppCompatButton reminderDate, reminderTime, cancel;
    private DbHandler dbHandler;
    private String dateText ;
    private String timeText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.newTaskButton){
            String newtask = newTask.getText().toString().trim();
            if(newtask.isEmpty()){
                Toast.makeText(NewTaskActivity.this, "Task Name cannot be empty.", Toast.LENGTH_SHORT).show();
            } else {
                todo td = new todo(newtask, dateText, timeText);
                dbHandler.addNewTask(td);
                Toast.makeText(NewTaskActivity.this, "ToDo has been added.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_new_task);
        newTask = findViewById(R.id.newTaskText);
        newTask.requestFocus();
        reminderDate = findViewById(R.id.reminderDateButton);
        reminderTime = findViewById(R.id.reminderTimeButton);
        cancel = findViewById(R.id.cancelButton);

        dbHandler = new DbHandler(NewTaskActivity.this);

        reminderDate.setOnClickListener(v -> {
            //Date Picker
            DatePicker datePicker = new DatePicker();
            datePicker.show(getSupportFragmentManager(), "DATE PICK");
        });

        reminderTime.setOnClickListener(v -> {
            //Time Picker
            TimePicker timePicker = new TimePicker();
            timePicker.show(getSupportFragmentManager(), "TIME PICK");
        });

        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month, dayOfMonth);
        dateText = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        if(minute < 10){
            timeText = hourOfDay + ":0" + minute;
        } else {
            timeText = hourOfDay + ":" + minute;
        }
    }
}