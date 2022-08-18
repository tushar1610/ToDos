package com.example.android.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity{

    FloatingActionButton addNewTask;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewTask = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todo[] dataSet = new todo[20];
        addNewTask.setOnClickListener(v -> {
            boolean prev = false;
            for(int i = 0; i < 20; i++){
                todo td = new todo("Task " + (i+1) + " set", prev);
                prev = !prev;
                dataSet[i] = td;
            }
            adapter = new RecyclerViewAdapter(dataSet);
            recyclerView.setAdapter(adapter);
//                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
//                startActivity(intent);
        });
    }
}