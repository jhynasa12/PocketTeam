package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.R;

public class EraActivity extends AppCompatActivity {
    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.era_layout);

        currentPlayer = PlayerProfileActivity.currentPlayer;
    }





    public void eraOkBtnHandler(View view) {

        final EditText earnedRuns = (EditText) findViewById(R.id.earned_runs);
        final EditText innings = (EditText) findViewById(R.id.innings);
        if(isEmpty(earnedRuns) || isEmpty(innings) == true){

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }else{

            currentPlayer.calcERA(Float.valueOf(earnedRuns.getText().toString()),Float.valueOf(innings.getText().toString()));
            this.setResult(0);
            finish();



        }


    }

    public void eraCancelBtn(View view) {

        finish();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
