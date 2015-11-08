package pocketteam.pocketteam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RosterActivity extends AppCompatActivity {

    public static String teamName;
    public static final String PLAYER_NAME = "playerName";
    public static final int DETAIL_REQUEST_CODE = 1001;
    protected ArrayList<Player> players;
    public static ArrayAdapter<Player> playerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster_layout);

        teamName = getIntent().getStringExtra(TeamListActivity.TEAM_NAME);
        TextView rosterText = (TextView) findViewById(R.id.roster_team_name);
        rosterText.setText(teamName);

        players = TeamList.getInstance().getTeam(teamName).getRoster();

        playerArrayAdapter = new ArrayAdapter<Player>(this, android.R.layout.simple_expandable_list_item_1, players);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(playerArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player player = players.get(position);
                displayRosterDetail(player);
            }
        });
    }



    public void AddPlayerEventOnClickHandler(View view) {

        Intent addPlayerIntent = new Intent(this, AddPlayerActivity.class);
        startActivity(addPlayerIntent);
        finishActivity(DETAIL_REQUEST_CODE);

    }

    private void displayRosterDetail(Player player) {
        Log.d("RosterActivity", "Displaying player: " + player.getLastName());
        Intent playerProfileIntent = new Intent(this, PlayerProfileActivity.class);
        playerProfileIntent.putExtra(PLAYER_NAME, player.getFirstName() + " " + player.getLastName());
        startActivityForResult(playerProfileIntent, DETAIL_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        playerArrayAdapter.notifyDataSetChanged();

        Context context = getApplicationContext();
        CharSequence text = "This runs.... ";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }
}//end Activiy
