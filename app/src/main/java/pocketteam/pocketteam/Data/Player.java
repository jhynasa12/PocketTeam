/**
 * 
 * This is the Player Class. This class will hold a player's information such as 
 * name, contact information,and player's statistics.
 * 
 * 
 */
package pocketteam.pocketteam.Data;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 *
 * @author Justin Hyland
 */
public class Player implements Comparable<Player>{
    
    private String m_FirstName; 
    private String m_LastName;
    private String m_Position;
    private String m_PhoneNumber;
    private String m_PlayerNumber;
    private String m_TeamName;
    private String m_ParentName;
    private float m_BatAverage;
    private float m_SluggingPercentage;
    private float m_ERA;
    private int m_RBIs;
    private int m_Saves;
    private int m_Wins;
    private int m_Losses;
    private int m_Hits;
    private int m_HomeRuns;
    private int m_Strikeouts_Pitcher;
    private int m_Strikeouts_Batter;
    private int m_Walks_Pitching;
    private int m_Walks_Batter;
    private Bitmap m_ProfilePicture;
    private ArrayList<Point> m_Points;

   
    
    
    
    public Player(String firstName, String lastName, String position, String number, String teamName){
        
        m_ERA = 0;
        m_RBIs = 0;
        m_Saves = 0;
        m_Wins = 0;
        m_Losses = 0;

        m_FirstName = firstName;
        m_LastName = lastName;
        m_Position = position;
        m_PlayerNumber = number;
        m_TeamName = teamName;

        
      
    }


    @Override
    public String toString(){
        return m_FirstName + " " + m_LastName;
    }
    
    /**
     * Sets first name of player
     * @param firstname 
     */
    public void setFirstName(String firstname){
        m_FirstName = firstname;
    }
    
    /**
     * Returns the player's first name
     * @return 
     */
    public String getFirstName(){
        return m_FirstName;
    }
    
    /**
     * Sets the player's last name
     * @param lastname 
     */
    public void setLastName(String lastname){
        m_LastName = lastname;
    }
    
    /**
     * Returns the last name of player
     * @return 
     */
    public String getLastName(){
        
        return m_LastName;
    }
   
    /**
     * Sets the position of the player
     * @param position 
     */
    public void setPosition(String position){
        
        m_Position = position;
    }
    
    /**
     * Returns the position of the player
     * @return 
     */
    public String getPosition(){
        
        return m_Position;
    }
    
    /**
     * Sets the player's phone number
     * @param number 
     */
    public void setPhoneNumber(String number){
        m_PhoneNumber = number;
    }
    
    /**
     * Returns the players phone number
     * @return 
     */
    public String getPhoneNumber(){

        return m_PhoneNumber;
    }
    
    /**
     * Sets player's jersey number
     * @param number 
     */
    public void setPlayerNumber(String number){
        m_PlayerNumber = number;
    }
    
    /**
     * Returns player's Jersey number
     * @return 
     */
    public String getPlayerNumber(){
        return m_PlayerNumber;
    }
    
    /**
     * Set the Team name that the player is on
     * @param name 
     */
    public void setTeamName(String name){
        m_TeamName = name;
    }
    
    /**
     * Returns the Team that the player is on
     * @return 
     */
    public String getTeamName(){
        return m_TeamName;
    }


    public void setParentName(String name){
        m_ParentName = name;
    }

    public String getParentName(){
        return m_ParentName;
    }
    
    /**
     * Calculates the player's batting average
     * @param hits - number of hits
     * @param atBats - number of at bats
     * @return m_BatAverage
     */
    public void calcBatAvg(Float hits, Float atBats){
      m_BatAverage = Stat.getInstance().battingAverage(hits, atBats);

        String.format(".3f", m_BatAverage);
        Log.d("calcBatAvg()", "" + m_BatAverage);
    }
    
    /**
     * Returns the batting average of the player
     * @return 
     */
    public float getBatAvg(){ return m_BatAverage; }
    
    /**
     * Calculates the slugging percentage of a player
     * @param singles
     * @param doubles
     * @param triples
     * @param homeruns
     * @param atBats 
     */
    public void calcSlugg(float singles, float doubles, float triples, float homeruns, float atBats){
        m_SluggingPercentage = Stat.getInstance().sluggingPercentage(singles, doubles, triples, homeruns, atBats);
    }
    
    /**
     * Returns the slugging percentage of the player
     * @return 
     */
    public float getSlugg(){
        return m_SluggingPercentage;
    }
    
    /**
     * Calculates ERA of the player
     * @param runs
     * @param innings 
     */
    public void calcERA(float runs, float innings){
        m_ERA = Stat.getInstance().getERA(runs, innings);
    }
    
    /**
     * Returns the player's ERA
     * @return 
     */
    public float getERA(){
        return m_ERA;
    }
    
    /**
     * Sets the RBIs of the player
     * @param rbi 
     */
    public void setRBI(int rbi){
        m_RBIs = rbi;
    }
    
    /**
     * Increments the player's RBIs by + 1
     */
    public void incrementRBIs(){
        m_RBIs++;
    }
    
    /**
     * Decrements the players RBIs by - 1
     */
    public void decrementRBIs(){
        m_RBIs--;
    }
   
    /**
     * Returns the current RBIs of the player
     *
     * @return 
     */
    public int getRBI(){
        return m_RBIs;
    }
    
    
    
    /**
     * Sets the Saves of the player
     * @param saves 
     */
    public void setSaves(int saves){
        m_Saves = saves;
    }
    
    /**
     * Increments the player's Saves by + 1
     */
    public void incrementSaves(){
        m_Saves++;
    }
    
    /**
     * Decrements the players Saves by - 1
     */
    public void decrementSaves(){
        m_Saves--;
    }
    
    /**
     * Returns the Saves of the players
     * @param saves
     * @return 
     */
    public int getSaves(int saves){
        return m_Saves;
    }
    
    /**
     * Sets number of wins the player has
     * @param number 
     */
    public void setWins(int number){
        m_Wins = number;
    }
    
    /**
     * Increments the players win by + 1
     */
    public void incrementWins(){
        m_Wins++;
    }
    
    /**
     * Decrements the players wins by - 1
     */
    public void decrementWins(){
        m_Wins--;
    }
    
    /**
     * Returns the number of wins the player has
     * @param wins
     * @return int
     */
     public int getWins( int wins){
        return m_Wins;
    }
     
     
     /**
     * Sets number of losses the player has
     * @param number 
     */
    public void setLosses(int number){
        m_Losses = number;
    }
    
    /**
     * Increments the players losses by + 1
     */
    public void incrementLosses(){
        m_Losses++;
    }
    
    /**
     * Decrements the players losses by - 1
     */
    public void decrementLosses(){
        m_Losses--;
    }
     
    /**
     * Returns the number of losses a player has
     * @return 
     */
     public int getLosses(){
        return m_Losses;
    }
     
     /**
      * Sets a players hits
      * @param hits - number hits for a player
      */
     public void setHits(int hits){
         m_Hits = hits;
     }
     
     /**
      * Increments the current amount of hits by + 1
      */
     public void incrementHits(){
         m_Hits++;
     }
     
     /**
      * Decrements the current amount of hits by - 1
      */
     public void decrimentHits(){
         m_Hits--;
     }
    
     /**
      * Returns the current hits of the player
      * @return 
      */
     public int getHits(){
         return m_Hits;
     }//
     
     public void setStrikeouts(){
         
         
     }

    public void setHomeRuns(int hr){
        m_HomeRuns = hr;
    }

    public int getHomeRuns(){
        return m_HomeRuns;
    }

    public void setProfilePicture(Bitmap image){
        m_ProfilePicture = image;
    }

    public Bitmap getProfilePicture(){
        return m_ProfilePicture;
    }

    public void setPoints(ArrayList<Point> points){
        m_Points = points;
    }

    public ArrayList<Point> getPoints(ArrayList<Point> points){
       return m_Points;
    }

    @Override
    public int compareTo(Player another)
    {
        if(m_BatAverage < another.m_BatAverage)
        {
            return 1;
        }
        else if(m_BatAverage == another.m_BatAverage)
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }


}//end Player
