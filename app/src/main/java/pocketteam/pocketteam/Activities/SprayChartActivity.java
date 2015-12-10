package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import pocketteam.pocketteam.view.SprayChartView;

import java.util.ArrayList;

import pocketteam.pocketteam.R;
import pocketteam.pocketteam.view.SprayChartView;

public class SprayChartActivity extends AppCompatActivity {

    private ArrayList<SprayChartView.Point> currentPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_chart);

        if(PlayerProfileActivity.currentPlayer.getPoints() != null){
            SprayChartView view = (SprayChartView) findViewById(R.id.spray_view);

            view.setSprayPoints(PlayerProfileActivity.currentPlayer.getPoints());

            view.invalidate();
        }

        Context context = getApplicationContext();
        CharSequence text = "Touch the field to Add Hit";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }

    public void SaveSprayChartActivityOnClick(MenuItem item) {

        SprayChartView chart;

        chart = (SprayChartView) findViewById(R.id.spray_view);

        currentPoints = chart.getPoints();

        PlayerProfileActivity.currentPlayer.setPoints(currentPoints);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spraychart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public void backToRosterOnCLickSpray(MenuItem item) {
        finish();
    }

    public void ClearSprayChartActivityOnClick(MenuItem item) {
        ((SprayChartView)findViewById(R.id.spray_view)).clearPoints();

    }

    public void UndoSprayChartActivityOnClick(MenuItem item) {
        ((SprayChartView)findViewById(R.id.spray_view)).undoPoint();

    }
}