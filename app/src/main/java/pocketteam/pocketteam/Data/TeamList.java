package pocketteam.pocketteam.Data;

import java.util.ArrayList;

/**
 * @author Justin Hyland
 */
public class TeamList {

    private static ArrayList<Team> m_Teams;
    private static TeamList uniqueInstance = null;




    public TeamList() {


        m_Teams = new ArrayList<>(); //create TeamList
    }

    /**
     * Singleton Pattern - only need one instance of TeamList
     *
     * @return uniqueInstance
     */
    public static TeamList getInstance() {

        if (uniqueInstance == null) {
            uniqueInstance = new TeamList();
        }

        return uniqueInstance;

    }

    /**
     * Returns the size of the TeamList
     *
     * @return
     */
    public int getSize() {
        return m_Teams.size();
    }

    /**
     * Checks to see if there are any teams in the list
     *
     * @return true or false
     */
    public boolean noTeams() {
        if (m_Teams.isEmpty())
            return true;
        else
            return false;
    }


    /**
     * Returns list of teams
     *
     * @return roster
     */
    public ArrayList<Team> getTeams() {
        return m_Teams;
    }

    /**
     * Adds team to the list
     *
     * @param team a Team object
     */
    public void addTeam(Team team) {
        m_Teams.add(team);
    }

    /**
     * removes team from list
     *
     * @param team - a Team object
     */
    public void removeTeam(Team team) {
        m_Teams.remove(team);
    }


    public Team getTeam(String name) {
        for (Team team : m_Teams) {
            if (team.getTeamName().equals(name)) return team;
        }
        return null;
    }

    public Player findPlayerByName(String firstname, String lastname) {

        ArrayList<Player> roster = Team.roster;

        for (Team team : m_Teams) {

            for (Player player : roster) {
                if (player.getFirstName().equals(firstname) && player.getLastName().equals(lastname)) {
                    return player;
                }


            }

        }

        return null;
    }



    public boolean findTeamByName(String name){

        for(Team team: m_Teams){
            if(name.equals(team.getTeamName())){
                return true;
            }
        }
        return false;
    }


    public Team returnTeamByName(String name){

        for(Team team: m_Teams){
            if(name.equals(team.getTeamName())){
                return team;
            }
        }
        return null;
    }


}//end Team
