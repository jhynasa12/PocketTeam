package pocketteam.pocketteam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class RosterActivity extends AppCompatActivity {

    public static final String LAST_NAME = "lastName";
    public static final String TEAM_NAME = "teamName";
    public static final String FIRST_NAME = "firstName";
    public static String teamName;
    public static final String PLAYER_NAME = "playerName";
    public static final int DETAIL_REQUEST_CODE = 1001;
    protected ArrayList<Player> players;
    public static ArrayAdapter<Player> playerArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster_layout);

        teamName = getIntent().getStringExtra(TeamListActivity.TEAM_NAME);
        TextView rosterText = (TextView) findViewById(R.id.roster_team_name);
        rosterText.setText(teamName);

        players = TeamList.getInstance().getTeam(teamName).getRoster();


        playerArrayAdapter = new ArrayAdapter<Player>(this, android.R.layout.simple_expandable_list_item_1, players);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(playerArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player player = players.get(position);
                displayRosterDetail(player);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Player player = players.get(position);
                openDialogForEdit(player);
                playerArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Opens a dialog box so a User can edit the Team name
     *
     * @param player - selected team
     */
    public void openDialogForEdit(final Player player) {


        final AlertDialog alertDialog = new AlertDialog.Builder((RosterActivity) this).create(); //Read Update
        alertDialog.setTitle("Edit your Player Name");
        alertDialog.setMessage("First Name and Last Name: ");


        LayoutInflater inflater = getLayoutInflater();

        View diagLayout = inflater.inflate(R.layout.namedialog, null);
        alertDialog.setView(diagLayout);

        final EditText firstNameInput = (EditText) diagLayout.findViewById(R.id.fname);
        final EditText lastNameInput = (EditText) diagLayout.findViewById(R.id.lname);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String firstname = firstNameInput.getText().toString();
                        String lastname = lastNameInput.getText().toString();
                        player.setFirstName(firstname);
                        player.setLastName(lastname);
                    }
                });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!
    }//end OpenDialogForEdit


    public void AddPlayerEventOnClickHandler(View view) {

        Intent addPlayerIntent = new Intent(this, AddPlayerActivity.class);
        addPlayerIntent.putExtra(TEAM_NAME, teamName);
        startActivity(addPlayerIntent);
        finishActivity(DETAIL_REQUEST_CODE);

    }

    private void displayRosterDetail(Player player) {
        Log.d("RosterActivity", "Displaying player: " + player.getLastName());
        Intent playerProfileIntent = new Intent(this, PlayerProfileActivity.class);
        playerProfileIntent.putExtra(PLAYER_NAME, player.getFirstName() + " " + player.getLastName());
        playerProfileIntent.putExtra(LAST_NAME, player.getLastName());
        playerProfileIntent.putExtra(TEAM_NAME, player.getTeamName());
        playerProfileIntent.putExtra(FIRST_NAME, player.getFirstName());
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

    public void RemovePlayerOnClickEventHandler(View view) {

        Context context = getApplicationContext();
        CharSequence text = "Please select a player to remove...";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Player player = players.get(position);
                players.remove(player);


                playerArrayAdapter.notifyDataSetChanged();

                Context context = getApplicationContext();
                CharSequence text = "You removed the player... ";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


    }

    public void SortByBatAvgHighestOnClick(MenuItem item) {

        Collections.sort(players);
        playerArrayAdapter.notifyDataSetChanged();

    }

    public void SortByBatAvgLowestOnClick(MenuItem item) {

        Collections.sort(players);
        Collections.reverse(players);

        playerArrayAdapter.notifyDataSetChanged();



    }
}//end Activiy
