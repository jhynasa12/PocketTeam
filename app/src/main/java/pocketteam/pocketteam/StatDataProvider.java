package pocketteam.pocketteam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 11/2/2015.
 */
public class StatDataProvider {
    private static List<Stat> data = new ArrayList<>();
    public static List<Stat> getData(){return data;}

    static {
        data.add(new Stat());
    }
}
