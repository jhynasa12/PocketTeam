package pocketteam.pocketteam.Activities;

/**
 * This is the EraActivity Class. This screen lets a user input the Runs and Innings to calculate the player's ERA
 *
 * @author Justin Hyland
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Utilities.Utility;

public class EraActivity extends AppCompatActivity {
    public static Player currentPlayer;

    /**
     * On Creation of EraActivity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.era_layout);

        currentPlayer = PlayerProfileActivity.currentPlayer;
    }


    /**
     * Ok Button - When user clicks on the button it calculates the ERA of the player.
     *
     * @param view
     */
    public void eraOkBtnHandler(View view) {

        final EditText earnedRuns = (EditText) findViewById(R.id.earned_runs);
        final EditText innings = (EditText) findViewById(R.id.innings);
        if (Utility.getInstance().isEmpty(earnedRuns) || Utility.getInstance().isEmpty(innings) == true) {

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {

            currentPlayer.calcERA(Float.valueOf(earnedRuns.getText().toString()), Float.valueOf(innings.getText().toString()));
            WelcomeActivity.teamDB.updateERA(currentPlayer);
            this.setResult(0);
            finish();


        }


    }

    /**
     * Cancel Button - When a user clicks the cancel button the activity finishes
     *
     * @param view
     */
    public void eraCancelBtn(View view) {

        finish();
    }

}
