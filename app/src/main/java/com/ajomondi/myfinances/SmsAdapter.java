package com.ajomondi.myfinances;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder>{
    ArrayList<Sms> smses;

    public SmsAdapter(ArrayList<Sms> mSmses){
        this.smses = mSmses;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.sms_rv_row, parent, false);
        return new SmsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        Sms sms = smses.get(position);
        holder.bind(sms);
    }

    @Override
    public int getItemCount() {
        return smses.size();
    }

    public class SmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView smsBody;

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            smsBody = (TextView) itemView.findViewById(R.id.smsBody);
            itemView.setOnClickListener(this);
        }

        public void bind (Sms sms){
            smsBody.setText(sms.getBody());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
