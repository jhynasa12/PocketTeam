package pocketteam.pocketteam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity {


    public static final String LOG_TAG = "AddPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_layout);
        Log.d(LOG_TAG, "OnCreate");

    }

    public void AddPlayerEventClickHandler(View view) {

        Button mButton;

        final EditText firstName;
        final EditText lastName;
        final EditText position;
        final EditText number;
        final EditText team;

        mButton = (Button) findViewById(R.id.add_player_button);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        position = (EditText) findViewById(R.id.position);
        number = (EditText) findViewById(R.id.jersey_number);

        if (isEmpty(firstName) || isEmpty(lastName) || isEmpty(position) || isEmpty(number) == true) {

            Context context = getApplicationContext();
            CharSequence text = "You are missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {

            //create Player
            Player player = new Player(firstName.getText().toString(), lastName.getText().toString(), position.getText().toString(), number.getText().toString(), RosterActivity.teamName); //creates player

            Log.d(LOG_TAG, player.getFirstName() + " " + player.getLastName() + " " + player.getPosition() + " " + player.getPlayerNumber() + " " + player.getTeamName());

            Log.d(LOG_TAG, player.getTeamName() + ": " + TeamList.getInstance().getSize() + " " + TeamList.getInstance().getTeam(player.getTeamName()).getTeamName());

            Team currentTeam = TeamList.getInstance().getTeam(player.getTeamName());

            //add player to the Team
            currentTeam.addPlayer(player);

            Log.d(LOG_TAG, player.getFirstName() + "'s team is " + currentTeam.getTeamName());

            RosterActivity.playerArrayAdapter.notifyDataSetChanged();

            //Go to the Roster Screen
            finish();
        }

    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void btnOnClickCancelEventHandler(View view) {

        finish();

    }
}


