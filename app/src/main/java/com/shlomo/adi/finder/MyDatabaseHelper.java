package com.shlomo.adi.finder;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = myParkingHistoryDB.DB_NAME;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_HISTORY =
            "CREATE TABLE " + myParkingHistoryDB .TableParking.TABLE_NAME  + " (" +
                    myParkingHistoryDB .TableParking.COLUMN_NAME_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + myParkingHistoryDB .TableParking.COLUMN_LONGITUDE +
                     TEXT_TYPE + COMMA_SEP + myParkingHistoryDB .TableParking.COLUMN_LATITUDE + TEXT_TYPE + COMMA_SEP+ myParkingHistoryDB .TableParking.COLUMN_ADDRESS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS " + myParkingHistoryDB .TableParking.TABLE_NAME;

    public MyDatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);        }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY);        }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_HISTORY);
        onCreate(db);        }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
