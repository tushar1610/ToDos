package com.example.android.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    private EditText newTask;
    private AppCompatButton save, cancel;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        newTask = findViewById(R.id.newTaskText);
        save = findViewById(R.id.newTaskButton);
        cancel = findViewById(R.id.cancelButton);

        dbHandler = new DbHandler(NewTaskActivity.this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newtask = newTask.getText().toString().trim();
                if(newtask.isEmpty()){
                    Toast.makeText(NewTaskActivity.this, "Task Name cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                todo td = new todo(newtask, false);
                dbHandler.addNewTask(td);
                Toast.makeText(NewTaskActivity.this, "ToDo hs been added.", Toast.LENGTH_SHORT).show();
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
}