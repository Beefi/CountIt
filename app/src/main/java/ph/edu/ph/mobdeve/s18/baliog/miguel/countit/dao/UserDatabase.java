package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String USERS_ID = "id";
    public static final String USERS_NAME = "name";
    public static final String USERS_EMAIL = "email";

    private static final String CREATE_USER_TABLE =
            "create table " + TABLE_USERS +
                    " ( "
                    + USERS_ID + " integer primary key, "
                    + USERS_NAME + " text, "
                    + USERS_EMAIL + " text ); ";

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}