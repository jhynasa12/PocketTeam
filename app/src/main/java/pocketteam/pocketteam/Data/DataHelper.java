package pocketteam.pocketteam.Data;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.widget.ImageView;


import pocketteam.pocketteam.Activities.WelcomeActivity;
import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.Team;
import java.util.Currency;

/**
 * Created by Max on 11/7/2015.
 */
public class DataHelper extends SQLiteOpenHelper {

    ImageView imageView1;
    //name of database
     public static final String DATABASE_NAME = "pocketDB.db";
    //name of table that contains the team names
    public static final String TABLE_TEAM = "Team_table";
    //name of table that contains the players for each team
    public static final String TABLE_PLAYERS = "Players_table";
    //name of column for team names
    public static final String teamName = "team_name";
    //Players table column names
    public static final String NUMBER = "NUMBER";
    public static final String FirstName = "FIRST_NAME";
    public static final String LastName = "LAST_NAME";
    public static final String Position = "POSITION";
    public static final String TeamName = "TEAM_NAME";
    public static final String ParentCell = "PARENT_CELL";
    public static final String ParentName = "PARENT_NAME";
    public static final String Walks = "WALKS";
    public static final String Wins = "WINS";
    public static final String Slugging = "SLUGGING";
    public static final String RBI = "RBI";
    public static final String BatAvrg = "BATTING_AVRG";
    public static final String ERA = "ERA";
    public static final String Losses = "LOSSES";
    public static final String Hits = "HITS";
    public static final String PlayerImage = "PLAYER_IMAGE";
    public static final String SprayChartPoints = "SPRAY_CHART_POINTS";

    //Team Table create statement
    private static final String CREATE_TABLE_TEAM = "CREATE TABLE " + TABLE_TEAM + "(" + teamName + " TEXT" + ");";
    //Players Table create statement
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE " + TABLE_PLAYERS + "(" + NUMBER + " INTEGER PRIMARY KEY," + FirstName + " TEXT," + LastName + " TEXT," + Position + " TEXT," +
                                                                                                TeamName + " TEXT," + ParentCell + " TEXT," + ParentName + " TEXT," + PlayerImage + " BLOB," + Walks + " TEXT,"
            + Wins + " TEXT,"+ Slugging + " TEXT,"+ RBI + " TEXT,"+ BatAvrg + " TEXT,"+ ERA + " TEXT," + Losses + " TEXT," + Hits + " TEXT" + SprayChartPoints + " TEXT" + ");";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    //creates table in the database
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_TEAM + "(" + teamName + " TEXT" + ");");
            //db.execSQL(CREATE_TABLE_PLAYERS);
        //imageView1 = (ImageView) this.findViewById(R.id.imageView1);


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


        db.execSQL("CREATE TABLE " + team.getTeamName() + "(" + NUMBER + " INTEGER PRIMARY KEY," + FirstName + " TEXT," + LastName + " TEXT," + Position + " TEXT," +
                TeamName + " TEXT," + ParentCell + " TEXT," + ParentName + " TEXT," + PlayerImage + " BLOB," + Walks + " TEXT,"
                + Wins + " TEXT," + Slugging + " TEXT," + RBI + " TEXT," + BatAvrg + " TEXT," + ERA + " TEXT," + Losses + " TEXT," + Hits + " TEXT" + SprayChartPoints + " TEXT" + ");");

        ContentValues contentValues = new ContentValues();

        contentValues.put(teamName, team.getTeamName());


        db.insert(TABLE_TEAM, null, contentValues);
        db.close();
    }


    public void deleteTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEAM, teamName + " = ?", new String[]{team.getTeamName()});
        db.execSQL("DROP TABLE " + team.getTeamName());
        TeamList.getInstance().removeTeam(team);
        db.close();
    }

    public int renameTeam(Team team, String newName, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        db.execSQL("ALTER TABLE " + oldName + " RENAME TO " + newName);

        values.put(teamName, newName);


        // updating row


        return db.update(TABLE_TEAM, values, teamName + " = ?",
                new String[] { String.valueOf(team.getTeamName()) });

    }



    // ------------------------ "PLAYERS" table methods ----------------//
    //adds player object to table
    public void addPlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER, player.getPlayerNumber());
        contentValues.put(FirstName,player.getFirstName());
        contentValues.put(LastName,player.getLastName());
        contentValues.put(Position,player.getPosition());
        contentValues.put(TeamName,player.getTeamName());
        contentValues.put(ParentCell, player.getPhoneNumber());
        contentValues.put(ParentName, player.getParentName());
        db.insert(player.getTeamName(), null, contentValues);
        db.close();
    }

    //removes player from database. For some reason, the position field holds team. Works for now, but should be looked into.
    public void removePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(player.getPosition(), NUMBER + " = ?", new String[]{player.getPlayerNumber()});
        db.close();
    }
//adds image to db
    public void addImage (Player player, Bitmap image) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PlayerImage, getBytes(image));


//         updating row
        db.update(player.getTeamName(), values, NUMBER + " = ?",
               new String[] { String.valueOf(player.getPlayerNumber()) });
       // db.insert(player.getTeamName(),null,values);




    }
    //turn bitmap into byte in order to store
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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
                player.setTeamName(cursor.getString(4));
                player.setPhoneNumber(cursor.getString(5));
                player.setParentName(cursor.getString(6));
                if(cursor.getString(7) != null){
                byte [] imgByte = cursor.getBlob(7);
                player.setProfilePicture(getImage(imgByte));}
                if(cursor.getString(8) != null)
                player.setWalks(Integer.parseInt(cursor.getString(8)));
                if(cursor.getString(9) != null)
                player.setWins(Integer.parseInt(cursor.getString(9)));
                if(cursor.getString(10) != null)
                player.setSlugg(Float.parseFloat(cursor.getString(10)));
                if(cursor.getString(11) != null)
                player.setRBI(Integer.parseInt(cursor.getString(11)));
                if(cursor.getString(12) != null)
                player.setBatAvrg(Float.parseFloat(cursor.getString(12)));
                if(cursor.getString(13) != null)
                player.setERA(Float.parseFloat(cursor.getString(13)));
                if(cursor.getString(14) != null)
                player.setLosses(Integer.parseInt(cursor.getString(14)));
                if(cursor.getString(15) != null)
                player.setHits(Integer.parseInt(cursor.getString(15)));
              //  if(cursor.getString(16) != null)
                    //player.setPoints(Integer.parseInt(cursor.getString(16)));



                playersList.add(player);
            } while (cursor.moveToNext());
        }

        // return contact list
        return playersList;
    }




    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NUMBER, player.getPlayerNumber());
        values.put(FirstName, player.getFirstName());
        values.put(LastName, player.getLastName());
        values.put(Position, player.getPosition());
        values.put(TeamName, player.getTeamName());
        values.put(ParentCell, player.getPhoneNumber());
        values.put(ParentName, player.getParentName());
        if(player.getProfilePicture() != null)
        values.put(PlayerImage, WelcomeActivity.teamDB.getBytes(player.getProfilePicture()));
        if(player.getWalks() != 0)
        values.put(Walks, player.getWalks());
        if(player.getWins() != 0)
        values.put(Wins, player.getWins());
        if(player.getSlugg() != 0.0)
        values.put(Slugging, player.getSlugg());
        if(player.getRBI() != 0)
        values.put(RBI, player.getRBI());
        if(player.getBatAvg() != 0.0)
        values.put(BatAvrg, player.getBatAvg());
        if(player.getERA() != 0.0)
        values.put(ERA, player.getERA());
        if(player.getLosses() != 0)
        values.put(Losses, player.getLosses());
        if(player.getHits() != 0)
        values.put(Hits, player.getHits());



        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }
    //-------------------------------------------------UPDATE PLAYER STATS-----------------------------------------------------------------------------//

    public int updateWalks(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Walks, player.getWalks());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }


    public int updateWins(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Wins, player.getWins());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }

    public int updateSlugging(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Slugging, player.getSlugg());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }



    public int updateRBI(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RBI, player.getRBI());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }


    public int updateBattingAvrg(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BatAvrg, player.getBatAvg());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }


    public int updateERA(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ERA, player.getERA());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }

    public int updateLosses(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Losses, player.getLosses());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
    }


    public int updateHits(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Hits, player.getHits());

        // updating row
        return db.update(player.getTeamName(), values, NUMBER + " = ?",
                new String[] { String.valueOf(player.getPlayerNumber()) });
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
