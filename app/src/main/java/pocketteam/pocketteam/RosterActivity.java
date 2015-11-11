package pocketteam.pocketteam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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




    /**
     * Opens a dialog box so a User can edit the Team name
     * @param player - selected team
     */
    public void openDialogForEdit(final Player player){



        final AlertDialog alertDialog = new AlertDialog.Builder((RosterActivity)this).create(); //Read Update
        alertDialog.setTitle("Edit your Player Name");
        alertDialog.setMessage("First Name and Last Name: ");

//        final EditText input = new EditText(RosterActivity.this);
//        final EditText lasName = new EditText(RosterActivity.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
//        alertDialog.setView(input);

        LayoutInflater inflater = getLayoutInflater();

        alertDialog.setView(inflater.inflate(R.layout.namedialog, null));

        final EditText firstNameInput = (EditText) findViewById(R.id.fname);
        final EditText lastNameInput = (EditText) findViewById(R.id.lname);
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
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        alertDialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);



        alertDialog.show();  //<-- See This!
    }//end OpenDialogForEdit



    public void AddPlayerEventOnClickHandler(View view) {

        Intent addPlayerIntent = new Intent(this, AddPlayerActivity.class);
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
}//end Activiy
