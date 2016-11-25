package com.example.androidstudio.contactsprovider.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidstudio.contactsprovider.contracts.ContactContract.ContactEntry;

public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Contacts.db",
            CONTACTS_CREATE_ENTRIES = "CREATE TABLE " +
                    ContactEntry.TABLE_NAME + " (" +
                    ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME_NAME + " TEXT," +
                    ContactEntry.COLUMN_NAME_NUMBER + " TEXT," +
                    ContactEntry.COLUMN_NAME_EMAIL + " TEXT)",
            CONTACTS_SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                    ContactEntry.TABLE_NAME;


    public ContactsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONTACTS_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CONTACTS_SQL_DELETE_ENTRIES);
        this.onCreate(db);
    }
}
