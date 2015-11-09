package pocketteam.pocketteam;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static pocketteam.pocketteam.R.id.player_name;
import static pocketteam.pocketteam.R.id.add_player_button;

public class AddTeamActivity extends AppCompatActivity {

    public static final String LOG_TAG = "AddTeamActivity";
    public Team newTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team_layout);

        Log.d(LOG_TAG, "OnCreate");


    }

    public void AddTeamClickEventHandler(View view) {


        final EditText teamName = (EditText) findViewById(R.id.team_text);


        if (isEmpty(teamName) == true) {

            Context context = getApplicationContext();
            CharSequence text = "You need a team name...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {

            newTeam = new Team(teamName.getText().toString()); //creates a new team


            TeamList.getInstance().addTeam(newTeam); //add to team list


            Log.d(LOG_TAG, newTeam.getTeamName());
            Log.d(LOG_TAG, TeamList.getInstance().getTeam(newTeam.getTeamName()).getTeamName());

            Intent teamListIntent = new Intent(this, TeamListActivity.class);
            startActivity(teamListIntent);
        }

    }// end AddTeamClickEventHandler

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}

