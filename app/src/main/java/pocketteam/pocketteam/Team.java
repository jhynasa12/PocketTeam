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
    
    
}//end Team
