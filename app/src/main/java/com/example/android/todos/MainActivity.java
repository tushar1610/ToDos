package com.example.android.todos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private FloatingActionButton addNewTask;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private DbHandler dbHandler;
    private ArrayList<todo> arrayList;
    private Snackbar sb;
    private View noTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);

        addNewTask = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.tasksRecyclerView);
        arrayList = new ArrayList<>();
        dbHandler = new DbHandler(MainActivity.this);
        noTask = findViewById(R.id.noToDo);
        arrayList = dbHandler.readTask();

        if(arrayList.isEmpty()){
            noTask.setVisibility(View.VISIBLE);
        }
        adapter = new RecyclerViewAdapter(arrayList, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todo td = arrayList.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                arrayList.remove(position);
                adapter.notifyItemRemoved(position);
                if(arrayList.isEmpty()){
                    noTask.setVisibility(View.VISIBLE);
                }
                sb = Snackbar.make(recyclerView, td.getNewText(), Snackbar.LENGTH_SHORT);
                sb.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.add(position, td);
                        noTask.setVisibility(View.INVISIBLE);
                        //dbHandler.addNewTask(td);
                        adapter.notifyItemInserted(position);
                    }
                }).addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if(event != DISMISS_EVENT_ACTION){
                            dbHandler.deleteTask(td.getNewText());
                        }
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);
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

    @Override
    protected void onStart() {
        super.onStart();
        if(arrayList.isEmpty()){
            noTask.setVisibility(View.VISIBLE);
        } else {
            noTask.setVisibility(View.INVISIBLE);
        }
        adapter.notifyDataSetChanged();
    }
}