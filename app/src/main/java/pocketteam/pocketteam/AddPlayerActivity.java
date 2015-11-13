package pocketteam.pocketteam;
import java.util.List;
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
    EditText editFirstName, editLastName, editPosition, editNumber, editPhoneNumber, editParentContact;
    public static String teamName;

    Button btnAddData;

    public static final String LOG_TAG = "AddPlayerActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_layout);


        editPosition = (EditText) findViewById(R.id.position);
        editFirstName = (EditText) findViewById(R.id.first_name);
        editLastName = (EditText) findViewById(R.id.last_name);
        editNumber = (EditText) findViewById(R.id.jersey_number);
        btnAddData = (Button) findViewById(R.id.add_player_button);
        editPhoneNumber = (EditText) findViewById(R.id.addplayer_player_number);
        editParentContact = (EditText) findViewById(R.id.addplayer_parent_contact);


        Log.d(LOG_TAG, "OnCreate");

        teamName = getIntent().getStringExtra(TeamListActivity.TEAM_NAME);

    }



    public void AddPlayerEventClickHandler(View view) {

        DataHelper myDb = new DataHelper(this); //creates database


        if (isEmpty(editFirstName) || isEmpty(editLastName) || isEmpty(editPosition) || isEmpty(editNumber) || isEmpty(editPhoneNumber) || isEmpty(editParentContact) == true ) {

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {


            //create Player

            Player player = new Player(editFirstName.getText().toString(), editLastName.getText().toString(), editPosition.getText().toString(), editNumber.getText().toString(), teamName); //creates player

            player.setPhoneNumber(editPhoneNumber.getText().toString());
            player.setParentName(editParentContact.getText().toString());



            Log.d(LOG_TAG, player.getFirstName() + " " + player.getLastName() + " " + player.getPosition() + " " + player.getPlayerNumber() + " " + player.getTeamName());

            Log.d(LOG_TAG, player.getTeamName() + ": " + TeamList.getInstance().getSize() + " " + TeamList.getInstance().getTeam(player.getTeamName()).getTeamName());

            Team currentTeam = TeamList.getInstance().getTeam(player.getTeamName());

            //add player to the Team
            currentTeam.addPlayer(player);
            //add player to the database
            myDb.addPlayer(player);
            Log.d("Reading: ", "Reading all contacts..");
            //finds all players from the database and adds them to a list
            List<Player> players = myDb.getAllPlayers();

            //finds players from database and adds them to the roster
            for (Player p : players) {
                currentTeam.addPlayer(p);
//                // Writing Contacts to log
//                Log.d("Name: ", log);
           }
//        }

            //Log.d(LOG_TAG, player.getFirstName() + "'s team is " + currentTeam.getTeamName());

            RosterActivity.playerArrayAdapter.notifyDataSetChanged();

            Context context = getApplicationContext();
            CharSequence text = "Player Added";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

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


