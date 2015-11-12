package pocketteam.pocketteam;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Currency;

/**
 * Created by Max on 11/7/2015.
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "playerName.db";
    public static final String TABLE_NAME = "player_table";
    public static final String NUMBER = "NUMBER";
    public static final String FirstName = "FIRSTNAME";
    public static final String LastName = "LASTNAME";
    public static final String Position = "POSITION";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + NUMBER + " INTEGER PRIMARY KEY," + FirstName + " TEXT," + LastName + " TEXT," + Position + " TEXT" + ");");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


//    void addPlayer(Player player){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,player.getPlayerNumber());
//        contentValues.put(COL_2,player.getFirstName());
//        contentValues.put(COL_3,player.getLastName());
//        contentValues.put(COL_4,player.getPosition());
//
//        db.insert(TABLE_NAME, null, contentValues);
//        db.close();
//    }
    void insertData(String number, String firstName, String lastName, String position) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER,number);
        contentValues.put(FirstName,firstName);
        contentValues.put(LastName,lastName);
        contentValues.put(Position, position);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();

//        long result = db.insert(TABLE_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return  true;
    }
//    public Player getPlayer(String number) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_1, COL_2, COL_3, COL_4}, COL_1 + "=?", new String[] { String.valueOf(number) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Player player = new Player(cursor.getString(0),
//                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
//        return player;
//    }
//
//    // Getting All Contacts
//    public List<Player> getAllContacts() {
//        List<Player> contactList = new ArrayList<Player>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Player player = new Player(null,null,null,null,null);
//                player.setPlayerNumber(cursor.getString(0));
//                player.setFirstName(cursor.getString(1));
//                player.setLastName(cursor.getString(2));
//                player.setPosition(cursor.getString(3));
//                player.setPosition(cursor.getString(4));
//                // Adding contact to list
//                contactList.add(player);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }


}
