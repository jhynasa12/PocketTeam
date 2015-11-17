package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Data.DataHelper;
import pocketteam.pocketteam.Data.Team;
import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.TeamList;

import pocketteam.pocketteam.Utilities.Utility;


public class AddTeamActivity extends AppCompatActivity {

    public static final String LOG_TAG = "AddTeamActivity";
    private Team newTeam;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team_layout);


        Log.d(LOG_TAG, "OnCreate");


    }

    public void AddTeamClickEventHandler(View view) {

        final EditText teamName = (EditText) findViewById(R.id.team_text);

        if (Utility.getInstance().isEmpty(teamName)) {

            Context context = getApplicationContext();
            CharSequence text = "You need a team name...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {

            newTeam = new Team(teamName.getText().toString()); //creates a new team
            WelcomeActivity.teamDB.addTeams(newTeam); //add team to database
            TeamList.getInstance().addTeam(newTeam); //add to team list

        }

            Log.d(LOG_TAG, newTeam.getTeamName());
            Log.d(LOG_TAG, TeamList.getInstance().getTeam(newTeam.getTeamName()).getTeamName());

            Context context = getApplicationContext();
            CharSequence text = "Team Added";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Intent teamListIntent = new Intent(this, TeamListActivity.class);
            startActivity(teamListIntent);
        }

    }// end AddTeamClickEventHandler





