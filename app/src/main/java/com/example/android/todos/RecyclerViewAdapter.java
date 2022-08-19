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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        todo td = arrayList.get(position);
        holder.box.setEnabled(td.check);
        if(td.newText == null){
            holder.newTask.setText("");
        } else {
            holder.newTask.setText(td.newText);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView newTask;
        public CheckBox box;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            box = (CheckBox) itemView.findViewById(R.id.todoCheckBox);
            newTask = (TextView) itemView.findViewById(R.id.taskDetail);
        }
    }
}
