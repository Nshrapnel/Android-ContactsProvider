package com.example.androidstudio.contactsprovider.contracts;


import android.provider.BaseColumns;

public class ContactContract {

    private ContactContract() {
    }

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contact",
                COLUMN_NAME_NAME = "mName",
                COLUMN_NAME_NUMBER = "mPhone",
                COLUMN_NAME_EMAIL = "mEmail";
    }
}

