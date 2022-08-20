package com.example.android.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText newTask;
    private AppCompatButton reminder, save, cancel;
    private DbHandler dbHandler;
    private String reminderText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        newTask = findViewById(R.id.newTaskText);
        reminder = findViewById(R.id.reminderButton);
        save = findViewById(R.id.newTaskButton);
        cancel = findViewById(R.id.cancelButton);

        dbHandler = new DbHandler(NewTaskActivity.this);

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Time Picker
                TimePicker timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "TIME PICK");

                //Date Picker
                DatePicker datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newtask = newTask.getText().toString().trim();
                if(newtask.isEmpty()){
                    Toast.makeText(NewTaskActivity.this, "Task Name cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(reminderText == ""){
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
//                    reminderText = sdf.format(new Date());
//                }
                todo td = new todo(newtask, reminderText);
                dbHandler.addNewTask(td);
                Toast.makeText(NewTaskActivity.this, "ToDo has been added.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        reminderText = selectedDate + " ";
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        String selectedTime = hourOfDay + ":" + minute;
        reminderText += selectedTime + " ";
    }
}