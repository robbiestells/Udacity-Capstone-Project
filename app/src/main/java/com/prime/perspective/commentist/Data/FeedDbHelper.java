package com.prime.perspective.commentist.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prime.perspective.commentist.Data.FeedContract.FeedEntry;

/**
 * Created by rsteller on 1/27/2017.
 */

public class FeedDbHelper extends SQLiteOpenHelper {
    //current version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "feed.db";

    public FeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FEED_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
        FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FeedEntry.COLUMN_SHOW_NAME + " TEXT, " +
                FeedEntry.COLUMN_EPISODE_TITLE + " TEXT, " +
                FeedEntry.COLUMN_EPIOSDE_LINK + " TEXT, " +
                FeedEntry.COLUMN_EPISODE_DESCRIPTION + " TEXT, " +
                FeedEntry.COLUMN_EPISODE_DATE + " TEXT, " +
                FeedEntry.COLUMN_EPIOSDE_LENGTH + " TEXT, " +
                FeedEntry.COLUMN_EPIOSDE_AUDIO + " TEXT);";


        sqLiteDatabase.execSQL(SQL_CREATE_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
