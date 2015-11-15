package pocketteam.pocketteam.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.Team;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Data.StatList;
import pocketteam.pocketteam.Data.TeamList;
import pocketteam.pocketteam.Utilities.Utility;

import static android.app.PendingIntent.getActivity;

public class PlayerProfileActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 12;
    private static int IMAGE_GALLERY = 10;
    public static String stats;
    public static String playerName;
    public static String lastName;
    public static String teamName;
    public static String firstName;
    public static final String test = "TEST";
    private Bitmap yourSelectedImage;
    public static Player currentPlayer;
    private ImageView image;
    private ArrayList<String> statList;
    private ArrayAdapter<String> statArrayAdapter;
    private TextView positionText, parentText, numberText, playerNumberText, teamText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        image = (ImageView) findViewById(R.id.imagePlayer);

        playerName = getIntent().getStringExtra(RosterActivity.PLAYER_NAME);
        firstName = getIntent().getStringExtra(RosterActivity.FIRST_NAME);
        lastName = getIntent().getStringExtra(RosterActivity.LAST_NAME);
        teamName = getIntent().getStringExtra(RosterActivity.TEAM_NAME);
        TextView rosterText = (TextView) findViewById(R.id.player_profile_name);
        rosterText.setText(playerName);

        currentPlayer = TeamList.getInstance().findPlayerByName(firstName, lastName);


        positionText = (TextView) findViewById(R.id.player_position_profile);
        positionText.setText(currentPlayer.getPosition());

        parentText = (TextView) findViewById(R.id.parent_name_profile);
        parentText.setText(currentPlayer.getParentName());


        numberText = (TextView) findViewById(R.id.player_phone_number);
        numberText.setText(currentPlayer.getPhoneNumber());

        playerNumberText = (TextView) findViewById(R.id.player_jersey_number_view);
        playerNumberText.setText(currentPlayer.getPlayerNumber());

        teamText = (TextView) findViewById(R.id.team_name_text);
        teamText.setText(currentPlayer.getTeamName());


        Context context = getApplicationContext();
        CharSequence text = "Touch a Statistic to add to " + firstName + "'s profile. Hold down to see the stat! ";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();


        Context context1 = getApplicationContext();
        CharSequence text1 = "Click on the Profile Picture to add one of your own...";
        int duration1 = Toast.LENGTH_SHORT;

        final Toast toast1 = Toast.makeText(context1, text1, duration1);
        toast1.show();


        if (currentPlayer.getProfilePicture() != null) {
            image.setImageBitmap(currentPlayer.getProfilePicture());
        }


        statList = StatList.getInstance().getStats();

        statArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, statList);
        ListView listView = (ListView) findViewById(R.id.statlist);
        listView.setAdapter(statArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = statList.get(position);
                statDetail(stat, position);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = statList.get(position);

                switch (position) {

                    case 0:
                        openStatDialogBox("Batting Average", currentPlayer.getBatAvg());
                        break;
                    case 1:
                        openStatDialogBox("ERA", currentPlayer.getERA());
                        break;

                    case 7:
                        openStatDialogBox("Hits", currentPlayer.getHits());
                        break;

                    case 3:
                        openStatDialogBox("Slugging Percentage", currentPlayer.getSlugg());
                        break;

                    case 4:
                        openStatDialogBox("RBIs", currentPlayer.getRBI());
                        break;


                }


                return true;
            }
        });

        numberText.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {


                Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentPlayer.getPhoneNumber()));


                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(sIntent);


            }


        });


    }

    private void statDetail(String stat, int position) {

        if (stat.equals(StatList.Stat.Batting_Average.name())) {
            Intent batAvgIntent = new Intent(this, BattingAvgActivity.class);
            startActivity(batAvgIntent);

        } else if (stat.equals(StatList.Stat.ERA.name())) {

            Intent eraIntent = new Intent(this, EraActivity.class);
            startActivity(eraIntent);

        } else if (stat.equals(StatList.Stat.Hits.name())) {
            onHitsClick();

        } else if (stat.equals(StatList.Stat.Slugging_Percentage.name())) {
            Intent sluggingIntent = new Intent(this, SluggingActivity.class);
            startActivity(sluggingIntent);

        } else if (stat.equals(StatList.Stat.RBI.name())) {

            Intent rbiIntent = new Intent(this, RbiActivity.class);
            startActivity(rbiIntent);

        }else if(stat.equals(StatList.Stat.Wins)){


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    currentPlayer.setProfilePicture(yourSelectedImage);
                    image.setImageBitmap(yourSelectedImage);

                }
        }

    }

    ;

    public void btnChoosePhotoPressed(View view) {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
    }


    public void openStatDialogBox(String stat, float number) {
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle(stat);

        String numberf = String.format("%.3f", number);

        alertDialog.setMessage("Your " + stat + " is " + numberf);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


        alertDialog.setButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!

    }


    /**
     * Opens a dialog box so a User can edit the Team name
     *
     * @param
     */
    public void openDialog(String title, String message, XmlPullParser layout) {


        final AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);


        LayoutInflater inflater = getLayoutInflater();

        View diagLayout = inflater.inflate(layout, null);
        alertDialog.setView(diagLayout);

        final EditText input = (EditText) diagLayout.findViewById(R.id.earned_runs);
        final EditText input2 = (EditText) diagLayout.findViewById(R.id.innings);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String firstname = input.getText().toString();
                        String lastname = input.getText().toString();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.player_profile_back_roster) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void backToRosterProfileOnClick(MenuItem item) {
        finish();
    }

    public void sprayChartOnClick(MenuItem item) {

        Intent sprayChartIntent = new Intent(this, SprayChartActivity.class);
        startActivity(sprayChartIntent);
    }

    public void editContactNameOnClick(MenuItem item) {

        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle("Change Emergency Contact");
        alertDialog.setMessage("Enter name: ");

        final EditText input = new EditText(PlayerProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newContact = input.getText().toString();
                        currentPlayer.setParentName(newContact);
                        parentText.setText(currentPlayer.getParentName());
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!


    }

    public void editContactNumberOnClick(MenuItem item) {

        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle("Change Emergency Contact Number");
        alertDialog.setMessage("Enter number: ");

        final EditText input = new EditText(PlayerProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newNumber = input.getText().toString();
                        currentPlayer.setPhoneNumber(Utility.getInstance().setPhoneNumberFormat(newNumber));
                        numberText.setText(currentPlayer.getPhoneNumber());
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!


    }

    public void editNumberOnClick(MenuItem item) {


        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle("Change Player Number");
        alertDialog.setMessage("Enter number: ");

        final EditText input = new EditText(PlayerProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newNumber = input.getText().toString();
                        //check if any other player on the team has the same number
                        Team currentTeam = TeamList.getInstance().getTeam(currentPlayer.getTeamName());
                        for (Player x : currentTeam.getRoster()) {
                            //if the numbers are the same
                            if (newNumber.equals(x.getPlayerNumber())) {
                                showToastMessage("Another player on your team has that number...Please enter another one"); // show message
                                break;
                            } else {
                                currentPlayer.setPlayerNumber(newNumber);
                                playerNumberText.setText(currentPlayer.getPlayerNumber());

                            }
                        }

                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!
    }

    private void showToastMessage(String message) {

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void editPositionOnClick(MenuItem item) {

        String[] items = new String[]{"P", "C", "1B", "2B", "3B", "LF", "CF", "RF"};

        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle("Change Player Number");
        alertDialog.setMessage("Select Position: ");

        final EditText input = new EditText(PlayerProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!


    }


    public void onHitsClick() {
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle("Hits");
        alertDialog.setMessage("Increment or Decrement hits: Touch outside screen to cancel ");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ADD",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currentPlayer.incrementHits();
                        showToastMessage("Hit added");
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "SUBTRACT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentPlayer.getHits() <= 0) {
                            showToastMessage("You cannot have less than 0 hits");
                        } else {
                            currentPlayer.decrimentHits();
                            showToastMessage("Hits subtracted");
                        }
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!
    }


}// end Activity
