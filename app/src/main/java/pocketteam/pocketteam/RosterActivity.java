package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class RosterActivity extends AppCompatActivity {

    protected String teamName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster_layout);

        teamName = getIntent().getStringExtra(TeamListActivity.TEAM_NAME);
        TextView rosterText = (TextView) findViewById(R.id.roster_team_name);
        rosterText.setText(teamName);


    }


    public void AddPlayerEventOnClickHandler(View view) {

        Intent addPlayerIntent = new Intent(this, AddPlayerActivity.class);
        startActivity(addPlayerIntent);

    }
}
