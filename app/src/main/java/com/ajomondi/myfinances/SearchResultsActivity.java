package com.ajomondi.myfinances;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class SearchResultsActivity extends AppCompatActivity {
    ArrayList<Sms> searchResults;
    Sms searchResult;
    SearchResultsAdapter searchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResults = (ArrayList<Sms>) getIntent().getSerializableExtra("Results");

        searchResultsAdapter = new SearchResultsAdapter(searchResults);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rvSmsResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchResultsAdapter);
    }

}
