package pocketteam.pocketteam.Data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;



import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.Team;
import java.util.Currency;

/**
 * Created by Max on 11/7/2015.
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pocketDB.db"; //name of database

    public static final String TABLE_TEAM = "Team_table"; //name of table
    public static final String TABLE_PLAYERS = "Players_table"; //name of table

    //Team table column names
    public static final String KEY_ID = "id";
    int id = 1;
    public static final String teamName = "team_name";
    //Players table column names
    public static final String NUMBER = "NUMBER";
    public static final String FirstName = "FIRST_NAME";
    public static final String LastName = "LAST_NAME";
    public static final String Position = "POSITION";
    public static final String TeamName = "TEAM_NAME";
    public static final String ParentCell = "PARENT_CELL";
    public static final String ParentName = "PARENT_NAME";
    //Team Table create statement
    private static final String CREATE_TABLE_TEAM = "CREATE TABLE " + TABLE_TEAM + "(" + teamName + " TEXT" + ");";
    //Players Table create statement
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE " + TABLE_PLAYERS + "(" + NUMBER + " INTEGER PRIMARY KEY," + FirstName + " TEXT," + LastName + " TEXT," + Position + " TEXT," + TeamName + " TEXT," + ParentCell + " TEXT," + ParentName + " TEXT" + ");";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    //creates table in the database
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_TEAM + "(" + teamName + " TEXT" + ");");
            //db.execSQL(CREATE_TABLE_PLAYERS);


    }


    @Override
    //updates table with latest data
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PLAYERS);
        onCreate(db);
    }

    // ------------------------ "TEAMS" table methods ----------------//

    public void addTeams(Team team){
        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("CREATE TABLE " + team.getTeamName() + "(" + NUMBER + " INTEGER PRIMARY KEY," + FirstName + " TEXT," + LastName + " TEXT," + Position + " TEXT," + TeamName + " TEXT," + ParentCell + " TEXT," + ParentName + " TEXT" + ");");

        ContentValues contentValues = new ContentValues();
        //contentValues.put(KEY_ID, id);
        contentValues.put(teamName,team.getTeamName());
        //contentValues.put(FirstName,player.getFirstName());

        db.insert(TABLE_TEAM, null, contentValues);
        db.close();
    }


    public void deleteTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEAM, teamName + " = ?", new String[] { team.getTeamName() });

        db.close();
    }



    // ------------------------ "PLAYERS" table methods ----------------//
    //adds player object to table
    public void addPlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER,player.getPlayerNumber());
        contentValues.put(FirstName,player.getFirstName());
        contentValues.put(LastName,player.getLastName());
        contentValues.put(Position,player.getPosition());
        contentValues.put(TeamName,player.getTeamName());
        contentValues.put(ParentCell,player.getPhoneNumber());
        contentValues.put(ParentName,player.getParentName());
        db.insert(player.getTeamName(), null, contentValues);
        db.close();
    }
    //gets a player by number from table in database
    public Player getPlayer(String number, String teamName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(teamName, new String[] {number, FirstName, LastName, Position, TeamName, ParentCell, ParentName}, number + "=?", new String[] { String.valueOf(number) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Player player = new Player(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return player;
    }

    // gets all players in table
    public List<Player> getAllPlayers(String team) {
        List<Player> playersList = new ArrayList<Player>();

        String selectQuery = "SELECT  * FROM " + team;

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

    ////////////////////get all teams



    public List<Team> getAllTeams() {
        List<Team> teamList = new ArrayList<Team>();

        String selectQuery = "SELECT  * FROM " + TABLE_TEAM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Team team = new Team(null);
                team.setTeamName(cursor.getString(0));


                teamList.add(team);
            } while (cursor.moveToNext());
        }

        // return contact list
        return teamList;
    }





}
