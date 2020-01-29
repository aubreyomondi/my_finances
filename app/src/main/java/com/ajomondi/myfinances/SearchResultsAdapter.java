package com.ajomondi.myfinances;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        TextView searchResult;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            searchResult = (TextView) itemView.findViewById(R.id.search_result);
            itemView.setOnClickListener(this);
        }

        public void bind (Sms result){
            searchResult.setText(result.getBody());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
