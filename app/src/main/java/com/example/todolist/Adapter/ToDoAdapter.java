package com.example.todolist.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DatabaseHandler;

import java.util.List;
import java.util.TooManyListenersException;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{


   private List<ToDoModel> todoList;
      private MainActivity activity;
    private DatabaseHandler db;


      public ToDoAdapter(DatabaseHandler db, MainActivity activity){
          this.activity= activity;
          this.db = this.db;
      }

   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.task_layout, parent, false);
       return new ViewHolder(view);


   }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));



      holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked){
            db.updateStatus(item.getId(), 1);

        }
        else{
            db.updateStatus(item.getId(),0);
        }
    }
});

    }
      private boolean toBoolean(int n){
          return n!=0;
}
      public void setTasks(List<ToDoModel> todoList){
          this.todoList = todoList;
          notifyDataSetChanged();
       }
       public Context getContext(){
          return activity;
    }
        public  void edititem(int position){
          ToDoModel item = todoList.get(position);
          Bundle bundle = new Bundle();
          bundle.putInt("id",item.getId());
          bundle.putString("task",item.getTask());

    }

        @Override
    public int getItemCount() {
        return todoList.size();
    }

           public static class ViewHolder extends  RecyclerView.ViewHolder{
          CheckBox task ;

         public ViewHolder(View view){
              super(view);
              task = view.findViewById(R.id.todoCheckBox);

          }
}

}
