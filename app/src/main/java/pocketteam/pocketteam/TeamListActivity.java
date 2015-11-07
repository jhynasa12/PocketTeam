package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TeamListActivity extends AppCompatActivity {

    public static final String TEAM_NAME = "teamName";
    public static final int DETAIL_REQUEST_CODE = 1001;
    protected ArrayList<Team> teams;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);



        teams = TeamList.getInstance().getTeams();

        ArrayAdapter<Team> teamArrayAdapter = new ArrayAdapter<Team>(this, android.R.layout.simple_expandable_list_item_1, teams);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(teamArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = teams.get(position);
                displayTeamDetail(team);
            }
        });

    }

    private void displayTeamDetail(Team team) {
        Log.d("TeamListActivity", "Displaying Team: " + team.getTeamName());

        Intent intent = new Intent(this, RosterActivity.class);
        intent.putExtra(TEAM_NAME, team.getTeamName());
        startActivityForResult(intent, DETAIL_REQUEST_CODE);
    }

    public void AddTeamOnClickEventHandler(View view) {
        Intent addTeamIntent = new Intent(this, AddTeamActivity.class);
        startActivity(addTeamIntent);

    }
}
