package com.shlomo.adi.finder;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class myParkingHistoryDB {

    public myParkingHistoryDB(){

    }

    public static final String DB_NAME = "MyDatabase.db";

    /* Inner class that defines the table contents */
    public static abstract class TableParking implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_PLACE_ID = "placeId";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_ADDRESS = "address";

    }

}


