package com.example.moneysensev1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.MyViewHolder> {

    Context context;
    ArrayList<Income> list;

    public FirebaseAdapter(Context context, ArrayList<Income> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_income,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Income income =  list.get(position);
        holder.amount.setText(income.getTmAmount());
        holder.type.setText(income.getTmType());
        holder.note.setText(income.getTmNote());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView amount, type, note;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.amount_txt_income);
            type=itemView.findViewById(R.id.type_txt_income);
            note=itemView.findViewById(R.id.note_txt_income);

        }
    }

}
