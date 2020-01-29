package com.ajomondi.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //DatabaseTable db = new DatabaseTable(SearchResultsActivity.this);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            //Cursor c = db.getWordMatches(query, null);
            //process Cursor and display results
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
}
