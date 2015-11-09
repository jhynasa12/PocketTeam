package pocketteam.pocketteam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Max on 11/7/2015.
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "playerName.db";
    public static final String TABLE_NAME = "player_table.db";
    public static final String COL_1 = "NUMBER";
    public static final String COL_2 = "FIRSTNAME";
    public static final String COL_3 = "LASTNAME";
    public static final String COL_4 = "POSITION";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE "+TABLE_NAME+" (NUMBER INTEGER, FIRSTNAME TEXT, LASTNAME TEXT,POSITION TEXT);");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String number, String firstName, String lastName, String position) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,number);
        contentValues.put(COL_2,firstName);
        contentValues.put(COL_3,lastName);
        contentValues.put(COL_4,position);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return  true;
    }
}
