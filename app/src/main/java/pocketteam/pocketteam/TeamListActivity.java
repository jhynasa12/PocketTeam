package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TeamListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);


        ArrayList<Team> teams = TeamList.getInstance().getTeams();

        ArrayAdapter<Team> teamArrayAdapter = new ArrayAdapter<Team>(this, android.R.layout.simple_expandable_list_item_1, teams);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(teamArrayAdapter);

    }

    public void AddTeamOnClickEventHandler(View view) {
        Intent addTeamIntent = new Intent(this,AddTeamActivity.class);
        startActivity(addTeamIntent);

    }
}
