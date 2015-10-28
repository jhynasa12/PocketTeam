package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPlayerActivity extends AppCompatActivity {


    public static final String LOG_TAG = "AddPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_layout);

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
        team = (EditText) findViewById(R.id.team_name);


        //create Player
        Player player = new Player(firstName.getText().toString(), lastName.getText().toString(), position.getText().toString(), number.getText().toString(), team.getText().toString()); //creates player

        Log.d(LOG_TAG, player.getFirstName() + " " + player.getLastName() + " " + player.getPosition() + " " + player.getPlayerNumber() + " " + player.getTeamName());

        //add player to the Team
        TeamList.getInstance().getTeam(team.getText().toString()).addPlayer(player);

        Log.d(LOG_TAG, player.getFirstName() + "'s team is " + TeamList.getInstance().getTeam(player.getTeamName()).getTeamName());

        //Go to the Roster Screen
        Intent rosterIntent = new Intent(this,RosterActivity.class);
        startActivity(rosterIntent);










    }


}


