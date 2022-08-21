package com.example.android.todos;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.text.DateFormat;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText newTask;
    private AppCompatButton reminder, save, cancel;
    private DbHandler dbHandler;
    private String dateText ;
    private String timeText;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        newTask = findViewById(R.id.newTaskText);
        reminder = findViewById(R.id.reminderButton);
        save = findViewById(R.id.newTaskButton);
        cancel = findViewById(R.id.cancelButton);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE) ;

        dbHandler = new DbHandler(NewTaskActivity.this);

        reminder.setOnClickListener(v -> {
            //Time Picker
            TimePicker timePicker = new TimePicker();
            timePicker.show(getSupportFragmentManager(), "TIME PICK");

            //Date Picker
            DatePicker datePicker = new DatePicker();
            datePicker.show(getSupportFragmentManager(), "DATE PICK");
        });

        save.setOnClickListener(v -> {
            String newtask = newTask.getText().toString().trim();
            if(newtask.isEmpty()){
                Toast.makeText(NewTaskActivity.this, "Task Name cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            todo td = new todo(newtask, dateText, timeText);
            dbHandler.addNewTask(td);
            Toast.makeText(NewTaskActivity.this, "ToDo has been added.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
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
        timeText = hourOfDay + ":" + minute;
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        long time = (mCalendar.getTimeInMillis() - (mCalendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() > time) {
            // setting time as AM and PM
            if (mCalendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
    }
}