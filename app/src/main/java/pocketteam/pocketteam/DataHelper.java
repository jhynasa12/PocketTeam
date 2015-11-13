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
    public static final String DATABASE_NAME = "playerName.db"; //name of database
    public static final String TABLE_NAME = "player_table"; //name of table
    public static final String NUMBER = "NUMBER";
    public static final String FirstName = "FIRSTNAME";
    public static final String LastName = "LASTNAME";
    public static final String Position = "POSITION";
    public static final String TeamName = "TEAMNAME";
    public static final String ParentCell = "PARENTCELL";
    public static final String ParentName = "PARENTNAME";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    //creates table in the database
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + NUMBER + " INTEGER PRIMARY KEY," + FirstName + " TEXT," + LastName + " TEXT," + Position + " TEXT," + TeamName + " TEXT," + ParentCell + " TEXT," + ParentName + " TEXT" + ");");


    }


    @Override
    //updates table with latest data
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adds player object to table
    void addPlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER,player.getPlayerNumber());
        contentValues.put(FirstName,player.getFirstName());
        contentValues.put(LastName,player.getLastName());
        contentValues.put(Position,player.getPosition());
        contentValues.put(TeamName,player.getTeamName());
        contentValues.put(ParentCell,player.getPhoneNumber());
        contentValues.put(ParentName,player.getParentName());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
    //gets a player by number from table in database
    public Player getPlayer(String number) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {number, FirstName, LastName, Position, TeamName, ParentCell, ParentName}, number + "=?", new String[] { String.valueOf(number) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Player player = new Player(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return player;
    }

    // gets all players in table
    public List<Player> getAllPlayers() {
        List<Player> playersList = new ArrayList<Player>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Player player = new Player(null,null,null,null,null);
                player.setPlayerNumber(cursor.getString(0));
                player.setFirstName(cursor.getString(1));
                player.setLastName(cursor.getString(2));
                player.setPosition(cursor.getString(3));
                player.setPosition(cursor.getString(4));

                playersList.add(player);
            } while (cursor.moveToNext());
        }

        // return contact list
        return playersList;
    }


}
