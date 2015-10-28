package pocketteam.pocketteam;

import java.util.ArrayList;

/**
 *
 * @author Justin Hyland
 */
public class TeamList {

    private ArrayList<Team> m_Teams;
    private static TeamList uniqueInstance;

    public TeamList(){


        m_Teams = new ArrayList<>();
    }

    public static TeamList getInstance(){

        if(uniqueInstance == null){
            uniqueInstance = new TeamList();
        }

        return uniqueInstance;

    }


    public boolean noTeams(){
        if(m_Teams.isEmpty())
            return true;
        else
            return false;
    }


    /**
     * Returns the roster of players
     * @return roster
     */
    public ArrayList<Team> getTeams(){
        return m_Teams;
    }

    /**
     * Adds team to the roster
     * @param team a player object
     */
    public void addTeam(Team team){
        m_Teams.add(team);
    }

    /**
     * removes player from roster 
     * @param team - a player object
     */
    public void removeTeam(Team team){
        m_Teams.remove(team);
    }


    public Team getTeam(String name){
        for(Team team: m_Teams){
            if(team.getTeamName() == name) return team;
        }
        return null;
    }


}//end Team
