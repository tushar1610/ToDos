package com.example.android.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private FloatingActionButton addNewTask;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private DbHandler dbHandler;
    private ArrayList<todo> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewTask = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.tasksRecyclerView);
        arrayList = new ArrayList<>();
        dbHandler = new DbHandler(MainActivity.this);

        arrayList = dbHandler.readToDo();
        adapter = new RecyclerViewAdapter(arrayList, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addNewTask.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}