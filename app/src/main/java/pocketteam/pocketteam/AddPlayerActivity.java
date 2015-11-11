package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity {
    DataHelper myDb;
    EditText editFirstName, editLastName, editPosition, editNumber;
    Button btnAddData;

    public static final String LOG_TAG = "AddPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_layout);
        myDb = new DataHelper(this);


        editPosition = (EditText) findViewById(R.id.position);
        editFirstName = (EditText) findViewById(R.id.first_name);
        editLastName = (EditText) findViewById(R.id.last_name);
        editNumber = (EditText) findViewById(R.id.jersey_number);
        btnAddData = (Button) findViewById(R.id.add_player_button);

        Log.d(LOG_TAG, "OnCreate");

    }



    public void AddPlayerEventClickHandler(View view) {

        Button mButton;

        final EditText firstName;
        final EditText lastName;
        final EditText position;
        final EditText number;
        final EditText team;




        //create Player

        Player player = new Player(editNumber.getText().toString(), editFirstName.getText().toString(), editLastName.getText().toString(), editPosition.getText().toString(), RosterActivity.teamName); //creates player
        myDb.addPlayer(player);
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





    public void btnOnClickCancelEventHandler(View view) {

        finish();

    }
}


