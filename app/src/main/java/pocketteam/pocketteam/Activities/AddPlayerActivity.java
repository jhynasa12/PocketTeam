package pocketteam.pocketteam.Activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pocketteam.pocketteam.Data.DataHelper;
import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Data.Team;
import pocketteam.pocketteam.Data.TeamList;
import pocketteam.pocketteam.Utilities.Utility;

public class AddPlayerActivity extends AppCompatActivity {
    EditText editFirstName, editLastName, editNumber, editPhoneNumber, editParentContact;
    public static String teamName;
    private String[] items;
    private String playerPosition;
    private ArrayAdapter<String> positionAdapter;

    Button btnAddData;

    public static final String LOG_TAG = "AddPlayerActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_layout);

        items = new String[]{"P", "C", "1B", "2B", "3B", "LF", "CF", "RF"};

        editFirstName = (EditText) findViewById(R.id.first_name);
        editLastName = (EditText) findViewById(R.id.last_name);
        editNumber = (EditText) findViewById(R.id.jersey_number);
        btnAddData = (Button) findViewById(R.id.add_player_button);
        editPhoneNumber = (EditText) findViewById(R.id.addplayer_player_number);
        editParentContact = (EditText) findViewById(R.id.addplayer_parent_contact);


        Log.d(LOG_TAG, "OnCreate");

        teamName = getIntent().getStringExtra(TeamListActivity.TEAM_NAME);

        Spinner dropdown = (Spinner) findViewById(R.id.position_list);

        playerPosition = dropdown.toString();

        positionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(positionAdapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                switch (position) {
                    case 0:
                        playerPosition = positionAdapter.getItem(0);
                        break;
                    case 1:
                        playerPosition = positionAdapter.getItem(1);
                        break;
                    case 2:
                        playerPosition = positionAdapter.getItem(2);
                        break;
                    case 3:
                        playerPosition = positionAdapter.getItem(3);
                        break;
                    case 4:
                        playerPosition = positionAdapter.getItem(4);
                        break;
                    case 5:
                        playerPosition = positionAdapter.getItem(5);
                        break;
                    case 6:
                        playerPosition = positionAdapter.getItem(6);
                        break;
                    case 7:
                        playerPosition = positionAdapter.getItem(7);
                        break;
                    case 8:
                        playerPosition = positionAdapter.getItem(8);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void AddPlayerEventClickHandler(View view) {

        DataHelper myDb = new DataHelper(this); //creates database


        if (Utility.getInstance().isEmpty(editFirstName) || Utility.getInstance().isEmpty(editLastName) || Utility.getInstance().isEmpty(editNumber) || Utility.getInstance().isEmpty(editPhoneNumber) || Utility.getInstance().isEmpty(editParentContact) == true) {

            showToastMessage("Please enter in all the fields");

            //check if phone number is not too long or short
        } else if (editPhoneNumber.getText().toString().length() > 10 || editPhoneNumber.getText().toString().length() < 10) {

            showToastMessage("Phone number is too short or too long");
            return;

            //this the number is greater than double digits
        }else if(editNumber.getText().toString().length() > 2){
            showToastMessage("A player number cannot be that big...");
            return;
        }


        Team currentTeam = TeamList.getInstance().getTeam(teamName);

        boolean matchingPlayer = true;


        /// if the current roster is has players in it
        if (!currentTeam.getRoster().isEmpty()) {


            //check if any other player on the team has the same number
            for (Player x : currentTeam.getRoster()) {
                //if the numbers are the same
                if (editNumber.getText().toString().equals(x.getPlayerNumber())) {
                    showToastMessage("Another player on your team has that number..."); // show message
                    matchingPlayer = false;
                }

            }
        }

        if (matchingPlayer) {      //create Player


            Player player = new Player(editFirstName.getText().toString(), editLastName.getText().toString(), playerPosition, editNumber.getText().toString(), teamName); //creates player

            String phoneNumber = Utility.getInstance().setPhoneNumberFormat(editPhoneNumber.getText().toString());

            player.setPhoneNumber(phoneNumber);
            player.setParentName(editParentContact.getText().toString());

            Log.d(LOG_TAG, player.getPosition());

            Log.d(LOG_TAG, player.getFirstName() + " " + player.getLastName() + " " + player.getPosition() + " " + player.getPlayerNumber() + " " + player.getTeamName());

            Log.d(LOG_TAG, player.getTeamName() + ": " + TeamList.getInstance().getSize() + " " + TeamList.getInstance().getTeam(player.getTeamName()).getTeamName());

           // Team testTeam =TeamList.getInstance().getTeam("Yankees");

         //   Log.d("First Test- passes", String.valueOf(testTeam.getRoster().size()));


            //add player to the Team
            currentTeam.addPlayer(player);

            Log.d(LOG_TAG, currentTeam.getTeamName());

            //add player to the database
            //         myDb.addPlayer(player);
            //         Log.d("Reading: ", "Reading all contacts..");
            //finds all players from the database and adds them to a list
            //        List<Player> players = myDb.getAllPlayers();
//
            //finds players from database and adds them to the roster
            //        for (Player p : players) {
            //            currentTeam.addPlayer(p);
//                // Writing Contacts to log
//                Log.d("Name: ", log);
            //        }
//        }

            //Log.d(LOG_TAG, player.getFirstName() + "'s team is " + currentTeam.getTeamName());

            RosterActivity.playerArrayAdapter.notifyDataSetChanged();

            showToastMessage("Player Added");
            //Go to the Roster Screen
            finish();

       //     Log.d("AddPlayerActivity", String.valueOf(TeamList.getInstance().getTeam("Yankees").getRoster().size()));

        }


    }


    public void btnOnClickCancelEventHandler(View view) {

        finish();

    }

    public void showToastMessage(String message) {

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

}


