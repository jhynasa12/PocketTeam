package pocketteam.pocketteam;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Justin Hyland on 11/10/2015.
 */
public class StatList {

    private ArrayList<String> m_StatList;
    private static StatList uniqueInstance;

    public StatList(){

        String[] statlist = {"Batting Average",
                            "Slugging Percentage",
                             "RBI", "Hits", "ERA"};

        m_StatList = new ArrayList<>();

        for(int i = 0; i< statlist.length; i++){
            m_StatList.add(statlist[i]);
        }




    }//constructor


    /**
     * Singleton Pattern - only need one instance of StatList
     *
     * @return uniqueInstance
     */
    public static StatList getInstance() {

        if (uniqueInstance == null) {
            uniqueInstance = new StatList();
        }

        return uniqueInstance;

    }


    public void addStat(String stat){
        m_StatList.add(stat);
    }

    public void remove(String stat){
        m_StatList.remove(stat);
    }

    public ArrayList<String> getStats(){
        return m_StatList;
    }



}//end StatList
