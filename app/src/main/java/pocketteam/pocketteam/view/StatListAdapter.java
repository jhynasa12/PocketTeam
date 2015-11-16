package pocketteam.pocketteam.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pocketteam.pocketteam.Data.StatList;
import pocketteam.pocketteam.R;

/**
 * Created by hylan on 11/16/2015.
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
    public Object getItem(int position)
    {
        return myData.keySet().toArray()[position];
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

        ((TextView)convertView.findViewById(R.id.statname)).setText(getItem(position).toString().replace("_", " "));
        ((TextView)convertView.findViewById(R.id.statvalue)).setText(myData.get(getItem(position)).toString());


        return convertView;
    }
}
