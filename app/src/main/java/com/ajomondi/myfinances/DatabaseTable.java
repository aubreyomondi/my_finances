package com.ajomondi.myfinances;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseTable {
    private static final String TAG = "SmsDatabase";

    //The columns we'll include in the dictionary table
    public static final String COL_SMS_DATE = "DATE";
    public static final String COL_NUMBER = "NUMBER";
    public static final String COL_BODY = "BODY";
    public static final String COL_DATE_FORMAT = "DATE_FORMAT";
    public static final String COL_TYPE = "TYPE";


    private static final String DATABASE_NAME = "SMS";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;

    private final DatabaseOpenHelper databaseOpenHelper;

    private static ArrayList<Sms> smses;

    public DatabaseTable(Context context, ArrayList<Sms> smses) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
        this.smses = smses;
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context helperContext;
        private SQLiteDatabase mDatabase;

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        COL_SMS_DATE + ", " +
                        COL_NUMBER + ", " +
                        COL_BODY + ", " +
                        COL_DATE_FORMAT + ", " +
                        COL_TYPE + ")";

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE);
            loadSMS();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }

        private void loadSMS() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        loadSmsDetails();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadSmsDetails() throws IOException {
            ContentValues initialValues = new ContentValues();

            for (int i = 0; i < smses.size(); i++){

                initialValues.put(COL_SMS_DATE, (smses.get(i)).getSmsDate());
                initialValues.put(COL_NUMBER, (smses.get(i)).getNumber());
                initialValues.put(COL_BODY, (smses.get(i)).getBody());
                initialValues.put(COL_DATE_FORMAT, ((smses.get(i)).getDateFormat()).toString());
                initialValues.put(COL_TYPE, (smses.get(i)).getType());

                mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
            }

        }

    }
}
