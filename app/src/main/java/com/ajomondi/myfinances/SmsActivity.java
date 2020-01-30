package com.ajomondi.myfinances;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class SmsActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_READ_SMS = 123;
    ArrayList<Sms> smses = new ArrayList<Sms>();
    Sms sms;
    SmsAdapter smsAdapter;
    SmsDatabaseTable smsDatabaseTable;
    ArrayList<Sms> searchResults = new ArrayList<Sms>();
    Sms searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        if (ContextCompat.checkSelfPermission(SmsActivity.this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // request permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(SmsActivity.this,
                    Manifest.permission.READ_SMS)) {
                // permission justification
                Log.d("Explanation Status:", "Explanation needed!");
                Toast.makeText(this, "Explanation needed!", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(SmsActivity.this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
        } else {
            // permission has already been granted
            Log.d("Permission Status:", "Permission has already been granted!");
            getAllSms(SmsActivity.this);
        }

        smsAdapter = new SmsAdapter(smses);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rvSmses);
        recyclerView.setLayoutManager(new LinearLayoutManager(SmsActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration();
        recyclerView.setAdapter(smsAdapter);

        smsDatabaseTable = new SmsDatabaseTable(SmsActivity.this);
        SmsDatabaseTable.smses = smses;
    }

    public void getAllSms(Context context) {

        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    Log.d("smsDate", smsDate);
                    Date dateFormat= new Date(Long.valueOf(smsDate));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                    String date = simpleDateFormat.format(dateFormat);
                    Log.d("date", date);
//                    Date new_date = new Date(Long.valueOf(date));

                    String type = "";
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            break;
                    }

                    this.sms = new Sms(smsDate, number, body, date, type);

                    if (sms == null) {
                        Log.d("Sms status:","Null");
                    } else {
                        if (sms.getNumber().equals("MPESA")){
                            smses.add(sms);
                        }
                    }
                    c.moveToNext();
                }
            }



            c.close();

        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted.
                    Log.d("Permission Status:", "Permission Granted!");
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    getAllSms(SmsActivity.this);
                } else {
                    // permission denied
                    Log.d("Permission Status:", "Permission Denied!");
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Cursor resultsCursor = smsDatabaseTable.getWordMatches(query, null);
            //process Cursor and display results
            if (resultsCursor == null){
                Log.d("Search results:", "cursor is null");
            }else{
                Log.d("Search results:", resultsCursor.getString(resultsCursor.getColumnIndex("BODY")));

                resultsCursor.moveToFirst();

                while (!resultsCursor.isAfterLast()) {

                    String smsDate = resultsCursor.getString(0);
                    String number = resultsCursor.getString(1);
                    String body = resultsCursor.getString(2);
                    //Date dateFormat= new Date(Long.valueOf(smsDate));
                    //DateFormat date = new DateFormat(dateFormat);

                    String type = resultsCursor.getString(4);

                    this.searchResult = new Sms(smsDate, number, body, smsDate, type);

                    if (searchResult == null) {
                        Log.d("Search result status:","Null");
                    } else {
                        searchResults.add(searchResult);
                    }

                    resultsCursor.moveToNext();
                }
//                Log.d("Search results:", searchResults.get(0).getBody());
                Intent search_intent = new Intent(SmsActivity.this, SearchResultsActivity.class);
                search_intent.putExtra("Results", searchResults);
                SmsActivity.this.startActivity(search_intent);

            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("App resume:", "resumed");
        handleIntent(getIntent());
    }
}
