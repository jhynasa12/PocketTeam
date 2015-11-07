package pocketteam.pocketteam;

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
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table"+TABLE_NAME+"(NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT,POSITION TEXT);");
        }
        catch (Exception e) {

        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
