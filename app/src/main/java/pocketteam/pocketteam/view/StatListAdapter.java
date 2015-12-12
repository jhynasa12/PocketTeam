package pocketteam.pocketteam.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pocketteam.pocketteam.Data.StatList;
import pocketteam.pocketteam.R;

/**
 * Created by Justin Hyland
 */
public class StatListAdapter<K, V> extends BaseAdapter
{
    Map<K, V> myData;
    Context context;

    public StatListAdapter(Context context, Map<K, V> stats)
    {
        this.context = context;
        myData = stats;
    }
    @Override
    public int getCount()
    {
        return myData.size();
    }

    @Override
    public K getItem(int position)
    {
        return (K)myData.keySet().toArray()[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.statslist, null);
        }

        String formatted_value;
        K item = getItem(position);
        V value = myData.get(item);

        NumberFormat numFmt;

        if(item.toString().equals("ERA"))
        {
            numFmt = new DecimalFormat("#0.00");
            formatted_value = numFmt.format(value);
        }
        else if(item.toString().equals("Batting_Average"))
        {
            numFmt = new DecimalFormat(".000");
            formatted_value = numFmt.format(value);
        }
        else if(item.toString().equals("Slugging_Percentage"))
        {
            numFmt = new DecimalFormat(".000");
            formatted_value = numFmt.format(value);
        }
        else
        {
            numFmt = new DecimalFormat("#####");
            formatted_value = numFmt.format(value);
        }

        ((TextView)convertView.findViewById(R.id.statname)).setText(item.toString().replace("_", " "));
        ((TextView)convertView.findViewById(R.id.statvalue)).setText(formatted_value);

        return convertView;
    }
}
