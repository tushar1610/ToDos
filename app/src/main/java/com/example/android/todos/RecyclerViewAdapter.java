package com.example.android.todos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<todo> arrayList;
    private Context context;
    public RecyclerViewAdapter(ArrayList<todo> todoArrayList, Context context) {
        this.arrayList = todoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        todo td = arrayList.get(position);
        if(td.newText == null){
            holder.newTask.setText("");
        } else {
            holder.newTask.setText(td.newText);
        }
        holder.taskDate.setText(td.reminderDate);
        holder.taskTime.setText(td.reminderTime);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView newTask, taskDate, taskTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newTask = (TextView) itemView.findViewById(R.id.taskDetail);
            taskDate = (TextView) itemView.findViewById(R.id.dateDetail);
            taskTime = (TextView) itemView.findViewById(R.id.timeDetail);
        }
    }
}
