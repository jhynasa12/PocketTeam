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
   public static DataHelper nice;
    private static ArrayList<Team> teams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        nice = new DataHelper(getApplicationContext());

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
        if (!WelcomeActivity.nice.getAllTeams().isEmpty())


            for (Team p : TeamList.getInstance().getTeams())
                TeamList.getInstance().removeTeam(p);

            for (Team p : WelcomeActivity.nice.getAllTeams())
                TeamList.getInstance().addTeam(p);
        ///////////////////we might need to end method???/////////////////////////////

    }
}
