package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.StatList;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Utilities.Utility;

public class BattingAvgActivity extends AppCompatActivity {

    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batting_average_layout);

            currentPlayer = PlayerProfileActivity.currentPlayer;




    }


    public void batAvgeCanceBtn(View view) {
        finish();
    }

    public void batAvgOkBtn(View view) {

        final EditText atBats = (EditText) findViewById(R.id.atBats);
        final EditText hits = (EditText) findViewById(R.id.hits);
        if(Utility.getInstance().isEmpty(atBats) || Utility.getInstance().isEmpty(hits) == true) {

            Context context = getApplicationContext();
            CharSequence text = "You have field blank...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        }else if(Integer.valueOf(atBats.getText().toString()) < Integer.valueOf(hits.getText().toString())) {
            Context context = getApplicationContext();
            CharSequence text = "Hits cannot be greater than atBats";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        else
        {
            currentPlayer.calcBatAvg(Float.valueOf(hits.getText().toString()),Float.valueOf(atBats.getText().toString()));

          //  StatList.getInstance().getMap().put(StatList.Stat.Batting_Average, Float.parseFloat(String.valueOf(currentPlayer.getBatAvg())));

            finish();


        }

    }


}
