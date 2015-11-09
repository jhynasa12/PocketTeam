package pocketteam.pocketteam;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeamListActivity extends AppCompatActivity {

    public static final String TEAM_NAME = "teamName";
    public static final int DETAIL_REQUEST_CODE = 1001;
    protected ArrayList<Team> teams;
    private ListView listView;
    private ArrayAdapter<Team> teamArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);


        teams = TeamList.getInstance().getTeams();

        teamArrayAdapter = new ArrayAdapter<Team>(this, android.R.layout.simple_expandable_list_item_1, teams);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(teamArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = teams.get(position);
                displayTeamDetail(team);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = teams.get(position);
                openDialogForEdit(team);
                teamArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }



    public void displayTeamDetail(Team team) {
        Log.d("TeamListActivity", "Displaying Team: " + team.getTeamName());

        Intent intent = new Intent(this, RosterActivity.class);
        intent.putExtra(TEAM_NAME, team.getTeamName());
        startActivityForResult(intent, DETAIL_REQUEST_CODE);
    }

    public void AddTeamOnClickEventHandler(View view) {
        Intent addTeamIntent = new Intent(this, AddTeamActivity.class);
        startActivity(addTeamIntent);

    }

    /**
     * Opens a dialog box so a User can edit the Team name
     * @param team - selected team
     */
    public void openDialogForEdit(final Team team){



        AlertDialog alertDialog = new AlertDialog.Builder((TeamListActivity)this).create(); //Read Update
        alertDialog.setTitle("Edit Team Name");
        alertDialog.setMessage("Edit your team name: ");

        final EditText input = new EditText(TeamListActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        team.setTeamName(name);
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);

//        alertDialog.setButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });


        alertDialog.show();  //<-- See This!
    }//end OpenDialogForEdit


    public void RemoveTeamOnClickEventHandler(View view) {


        Context context = getApplicationContext();
        CharSequence text = "Please select a team to remove...";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Team team = teams.get(position);
                teams.remove(team);


                teamArrayAdapter.notifyDataSetChanged();

                Context context = getApplicationContext();
                CharSequence text = "You removed the team... ";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

    }//end RemoveTeamOnClickEventHandler
}
