package pocketteam.pocketteam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pocketteam.pocketteam.Data.Player;
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
        if(Utility.getInstance().isEmpty(atBats) || Utility.getInstance().isEmpty(hits) == true){


        }else{

            currentPlayer.calcBatAvg(Float.valueOf(hits.getText().toString()),Float.valueOf(atBats.getText().toString()));

            finish();


        }

    }


}
