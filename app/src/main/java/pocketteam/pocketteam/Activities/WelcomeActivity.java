package pocketteam.pocketteam.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import pocketteam.pocketteam.Data.Team;
import pocketteam.pocketteam.Data.TeamList;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Data.DataHelper;

public class WelcomeActivity extends AppCompatActivity {
   public static DataHelper teamDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        teamDB = new DataHelper(getApplicationContext());

        String fontPath = "fonts/varsity_regular.ttf";

        // text view label
        TextView txtVarsity = (TextView) findViewById(R.id.textView);


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtVarsity.setTypeface(tf);

    }



    public void ScoutModeOnClickEventHandler(View view) {

        Intent addPlayerIntent = new Intent(this, ScoutPlayerActivity.class);
        startActivity(addPlayerIntent);

    }

    public void TeamsOnClickEventHandler(View view) {

        Intent teamsIntent = new Intent(this, TeamListActivity.class);
        startActivity(teamsIntent);



       // Pulls existing teams from db
        if (!teamDB.getAllTeams().isEmpty()) //checks to see if there are any teams

            //clears roster list
            for (Team p : TeamList.getInstance().getTeams())
                TeamList.getInstance().removeTeam(p);

            //re-adds players to list
            for (Team p : teamDB.getAllTeams())
                TeamList.getInstance().addTeam(p);

        ///////////////////we might need to end method???/////////////////////////////

    }
}
