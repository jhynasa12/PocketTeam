package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Data.Team;
import pocketteam.pocketteam.Data.TeamList;
import pocketteam.pocketteam.Utilities.Utility;

public class ScoutPlayerActivity extends AppCompatActivity {

    EditText editFirstName, editLastName, editPosition, editNumber, editPhoneNumber, editParentContact;
    private String teamName;
    private Team currentTeam;

    public static final String LOG_TAG = "ScoutPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scout_player_layout);


        editPosition = (EditText) findViewById(R.id.scout_position);
        editFirstName = (EditText) findViewById(R.id.scout_first_name);
        editLastName = (EditText) findViewById(R.id.scout_last_name);
        editNumber = (EditText) findViewById(R.id.scout_jersey_number);
        editPhoneNumber = (EditText) findViewById(R.id.scout_player_number);
        editParentContact = (EditText) findViewById(R.id.scout_parent_contact);


    }

    public void btnScoutOnClickCancelEventHandler(View view) {
        finish();
    }

    public void AddPlayerScoutEventClickHandler(View view) {


        if (TeamList.getInstance().findTeamByName("Scouted Players") == false) {

            TeamList.getInstance().addTeam(new Team("Scouted Players"));
            currentTeam = TeamList.getInstance().returnTeamByName("Scouted Players");
        } else {
            currentTeam = TeamList.getInstance().returnTeamByName("Scouted Players");
        }


        if (Utility.getInstance().isEmpty(editFirstName) || Utility.getInstance().isEmpty(editLastName) || Utility.getInstance().isEmpty(editPosition) || Utility.getInstance().isEmpty(editNumber) || Utility.getInstance().isEmpty(editPhoneNumber) || Utility.getInstance().isEmpty(editParentContact) == true) {

            Context context = getApplicationContext();
            CharSequence text = "You're missing the fields...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //check if phone number is not too long or short
        } else if (editPhoneNumber.getText().toString().length() > 10 || editPhoneNumber.getText().toString().length() < 10) {

            showToastMessage("Phone number is too short or too long");

            //this the number is greater than double digits
        }else if(editNumber.getText().toString().length() > 2){
            showToastMessage("A player number cannot be that big...");


        }
        boolean matchingPlayer = true;


        /// if the current roster has players in it
        if (!currentTeam.getRoster().isEmpty()) {


            //check if any other player on the team has the same number
            for (Player x : currentTeam.getRoster()) {
                //if the numbers are the same
                if (editNumber.getText().toString().equals(x.getPlayerNumber())) {
                    showToastMessage("Another player on your team has that number..."); // show message
                    matchingPlayer = false;
                } else {
                    matchingPlayer = true;
                }

            }
        }

        if (matchingPlayer) {      //create Player




            //create Player

            Player player = new Player(editFirstName.getText().toString(), editLastName.getText().toString(), editPosition.getText().toString(), editNumber.getText().toString(), currentTeam.getTeamName()); //creates player

            player.setPhoneNumber(editPhoneNumber.getText().toString());

            player.setParentName(editParentContact.getText().toString());


            //      myDb.addPlayer(player);
            Log.d(LOG_TAG, player.getFirstName() + " " + player.getLastName() + " " + player.getPosition() + " " + player.getPlayerNumber() + " " + player.getTeamName());

            Log.d(LOG_TAG, player.getTeamName() + ": " + TeamList.getInstance().getSize() + " " + TeamList.getInstance().getTeam(player.getTeamName()).getTeamName());


            //add player to the Team
            currentTeam.addPlayer(player);

            Log.d(LOG_TAG, player.getFirstName() + "'s team is " + currentTeam.getTeamName());


            //Go to the Team Screen
            finish();

        }

    }


    public void showToastMessage(String message) {

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
