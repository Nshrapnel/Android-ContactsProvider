package com.example.androidstudio.contactsprovider.providers;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.androidstudio.contactsprovider.contracts.ContactContract.ContactEntry;
import com.example.androidstudio.contactsprovider.helpers.ContactsDBHelper;

public class ContactsProvider extends ContentProvider {

    private static final String AUTHORITY = ContactsProvider.class.getPackage().getName(),
            CONTACT_ENTITY = "contacts";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CONTACTS = 1,
            CONTACT = 2;

    static {
        uriMatcher.addURI(AUTHORITY, CONTACT_ENTITY, CONTACTS);
        uriMatcher.addURI(AUTHORITY, CONTACT_ENTITY + "/#", CONTACT);
    }

    private ContactsDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new ContactsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == CONTACT || uriMatcher.match(uri) == CONTACTS) {
            if (uriMatcher.match(uri) == CONTACT) {
                if (selection == null) {
                    selection = "";
                }
                selection += ContactEntry._ID + " = " + uri.getLastPathSegment();
            }
            return dbHelper.getReadableDatabase().query(ContactEntry.TABLE_NAME, projection,
                    selection, selectionArgs, sortOrder, null, null);
        } else return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CONTACTS:
                return "contacts.dir/contacts";
            case CONTACT:
                return "contacts.item/contacts";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == CONTACTS) {
            long id = dbHelper.getWritableDatabase().insert(ContactEntry.TABLE_NAME, null, values);
            return Uri.withAppendedPath(uri, "/" + id);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == CONTACT) {
            if (selection == null) {
                selection = "";
            }
            selection += ContactEntry._ID + " = " + uri.getLastPathSegment();
            return dbHelper.getWritableDatabase().delete(ContactEntry.TABLE_NAME, selection,
                    selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (uriMatcher.match(uri) == CONTACT) {
            if (selection == null) {
                selection = "";
            }
            selection += ContactEntry._ID + " = " + uri.getLastPathSegment();
            return dbHelper.getWritableDatabase().update(ContactEntry.TABLE_NAME, values,
                    selection, selectionArgs);
        }
        return 0;
    }
}
