package pocketteam.pocketteam;

import java.util.ArrayList;

/**
 *
 * @author Justin Hyland
 */
public class Team {
    
    private ArrayList<Player> roster;
    private String m_TeamName;
    
    public Team(String name){
        m_TeamName = name;

        roster = new ArrayList<>();
    }


    public Player findPlayerOnTeamByLastName(String lastname){
        for(Player x: roster){
            if(x.getLastName().equals(lastname)){
                return x;
            }
        }
        return null;
    }
    
    /**
     * Sets the Team name
     * @param name 
     */
    public void setTeamName(String name){
        m_TeamName = name;
    }
    
    /**
     * Returns the Team Name
     * @return 
     */
    public String getTeamName(){
        return m_TeamName;
    }
    
    /**
     * Returns the roster of players
     * @return roster
     */
    public ArrayList<Player> getRoster(){
        return roster;
    }
    
    /**
     * Adds player to the roster
     * @param player - a player object 
     */
    public void addPlayer(Player player){
        roster.add(player);
    }
    
    /**
     * removes player from roster 
     * @param player - a player object
     */
    public void removePlayer(Player player){
        roster.remove(player);
    }


    public Player findPlayerByLastName(String lastName){

        for(Player player: roster){
            if(player.getLastName().equals(lastName)){
                return player;
            }

        }
        return null;
    }//end findPlayerByLastName

    @Override
    public String toString(){
        return m_TeamName;
    }
    
    
}//end Team
