package pocketteam.pocketteam.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Justin Hyland on 11/10/2015.
 */
public class StatList {

    public enum Stat
    {
        Batting_Average, Slugging_Percentage,
        RBI, Hits, ERA, Home_Runs, Walks, Wins, Losses
    }
    //private ArrayList<String> m_StatList;
    private HashMap<Stat,Float> m_MapList;
    private static StatList uniqueInstance;

    public StatList(){

        m_MapList = new HashMap<>();

        /*String[] statlist = {"Batting Average",
                            "Slugging Percentage",
                             "RBI", "Hits", "ERA"};*/

        m_MapList.put(Stat.Hits, 0f);
        m_MapList.put(Stat.RBI, 0f);
        m_MapList.put(Stat.Walks, 0f);
        m_MapList.put(Stat.Batting_Average, .000f);
        m_MapList.put(Stat.Slugging_Percentage, .000f);
        m_MapList.put(Stat.ERA, 0f);
        m_MapList.put(Stat.Wins, 0f);
        m_MapList.put(Stat.Losses, 0f);



        // m_StatList = new ArrayList<>();


    }//constructor




    public HashMap<Stat,Float> getMap(){
        return m_MapList;
    }


    public void addStat(Stat stat, float value){
        m_MapList.put(stat, value);
    }

/*    public void remove(Stat stat){
        m_StatList.remove(stat);
    }*/


    public Stat getBattingAvgStat(Stat stat){
        return Stat.Batting_Average;
    }

    public ArrayList<String> getStats()
    {
        ArrayList<String> tmp = new ArrayList<>();
        Iterator<Stat> it = m_MapList.keySet().iterator();

        while (it.hasNext()) {
            Stat curr = it.next();
            tmp.add(curr.name());
        }
        return tmp;
    }

    public Map<StatList.Stat, Float> getStatsMap()
    {
        return m_MapList;
    }
}//end StatList
