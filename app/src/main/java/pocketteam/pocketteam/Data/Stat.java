/**
 * This is the Stat Class.
 */
package pocketteam.pocketteam.Data;

/**
 *
 * @author Justin Hyland
 */
public class Stat {
    
    
    private static Stat uniqueInstance;
    
    
    public Stat(){
        
    }
    
    public static Stat getInstance(){
        
        if(uniqueInstance == null){
            uniqueInstance = new Stat();
        }
        
        return uniqueInstance;
        
    }
    
    /**
     * Returns the batting average of a player based on hits and atBats
     * @param hits - number of hits
     * @param atBats - number of atBats
     * @return 
     */
    public float battingAverage(float hits, float atBats) {

        float avg = (hits / atBats);
        
        return avg;
    }//end battingAverage

    
    /**
     * Returns the ERA of a player based on earned runs and innings pitched
     * @param earnedRuns - player's earned runs
     * @param innings - innings pitched by player
     * @return 
     */
    public float getERA(float earnedRuns, float innings){
        return (earnedRuns * 9) / innings;
    }
    
    /**
     * Returns the slugging percentage of the player based on singles, doubles, triples, homeruns
     * @param singles
     * @param doubles
     * @param triples
     * @param homeruns
     * @param atBats
     * @return 
     */
    public float sluggingPercentage(float singles, float doubles, float triples, float homeruns, float atBats){
       
       float totalBases = 0;
       
        totalBases = singles + (doubles * 2) + (triples * 3) + (homeruns * 4);
        
        return totalBases / atBats; 
    }
    
    public double onBasePercentage(){
        double per = 0;
        
        return per;
    }
    
    
    
    
    
    
}//end Stat
