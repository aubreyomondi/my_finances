package com.ajomondi.myfinances;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {
    ArrayList<Sms> searchResults;

    public SearchResultsAdapter(ArrayList<Sms> searchResults){
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_results_rv_row, parent, false);
        return new SearchResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Sms result = searchResults.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView smsNumber;
        TextView smsBody;
        TextView smsDate;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            smsNumber = (TextView) itemView.findViewById(R.id.smsNumber);
            smsBody = (TextView) itemView.findViewById(R.id.smsBody);
            smsDate = (TextView) itemView.findViewById(R.id.smsDate);
            itemView.setOnClickListener(this);
        }

        public void bind (Sms result){
            smsNumber.setText(result.getNumber());
            smsBody.setText(result.getBody());


            Date dateFormat= new Date(Long.valueOf(result.getDateFormat()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            String date = simpleDateFormat.format(dateFormat);

            smsDate.setText(date);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            Sms selectedSms = searchResults.get(position);
            Intent intent = new Intent(view.getContext(), SmsDetailActivity.class);
            intent.putExtra("Sms", selectedSms);
            view.getContext().startActivity(intent);
        }
    }
}
