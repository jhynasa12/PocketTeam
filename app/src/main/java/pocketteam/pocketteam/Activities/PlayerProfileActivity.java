package pocketteam.pocketteam.Activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
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
import android.util.Log;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;


import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.Team;
import pocketteam.pocketteam.R;
import pocketteam.pocketteam.Data.StatList;
import pocketteam.pocketteam.Data.TeamList;
import pocketteam.pocketteam.Utilities.Utility;
import pocketteam.pocketteam.view.StatListAdapter;


public class PlayerProfileActivity extends AppCompatActivity
{

    public static String playerName;
    public static String lastName;
    public static String teamName;
    public static String firstName;
    public static final String test = "TEST";
    private Bitmap yourSelectedImage;
    public static Player currentPlayer;
    private ImageView image;
    private HashMap<StatList.Stat, Float> statList;
    private StatListAdapter<StatList.Stat, Float> statArrayAdapter;
    private TextView positionText, parentText, numberText, playerNumberText, teamText;
    private StatList playerStatList;
    private ArrayAdapter<String> positionAdapter;
    private String playerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        if (currentPlayer.getProfilePicture() != null)
        {
            image.setImageBitmap(currentPlayer.getProfilePicture());
        }


        playerStatList = new StatList();

        statList = playerStatList.getMap();

        statList.put(StatList.Stat.Slugging_Percentage, currentPlayer.getSlugg());
        statList.put(StatList.Stat.Batting_Average, currentPlayer.getBatAvg());
        statList.put(StatList.Stat.ERA, currentPlayer.getERA());
        statList.put(StatList.Stat.Hits, (float)currentPlayer.getHits());
        statList.put(StatList.Stat.Wins, (float)currentPlayer.getWins());
        statList.put(StatList.Stat.Losses, (float)currentPlayer.getLosses());
        statList.put(StatList.Stat.RBI, (float) currentPlayer.getRBI());
        statList.put(StatList.Stat.Walks, (float) currentPlayer.getWalks());


        statArrayAdapter = new StatListAdapter<StatList.Stat, Float>(this, statList);
        final ListView listView = (ListView) findViewById(R.id.statlist);
        listView.setAdapter(statArrayAdapter);

        final Intent hitsIntent = new Intent(this,HitsActivity.class);
        final Intent rbiIntent = new Intent(this,RbiActivity.class);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                statDetail(position);
                return true;
            }
        });


        final AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update

        numberText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentPlayer.getPhoneNumber()));

                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                alertDialog.setTitle("Call Contact");
                alertDialog.setMessage("Would you like to call the player's Emergency Contact?");

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Call",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(sIntent);
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
            }
        });


    }

    private void statDetail(int position)
    {
        switch (position)
        {
            case 0:
                onWalksClick();
                statList.put(StatList.Stat.Walks, Float.parseFloat(String.valueOf(currentPlayer.getWalks())));
                break;
            case 1:
                onWinsClick();
                statList.put(StatList.Stat.Wins, Float.parseFloat(String.valueOf(currentPlayer.getWins())));

                break;
            case 2:
                Intent sluggingIntent = new Intent(this, SluggingActivity.class);
                startActivityForResult(sluggingIntent, 2);
                break;
            case 3:
                onRbiClick();
                statList.put(StatList.Stat.RBI, Float.parseFloat(String.valueOf(currentPlayer.getRBI())));
                break;
            case 4:
                Intent batAvgIntent = new Intent(this, BattingAvgActivity.class);
                startActivityForResult(batAvgIntent, 4);

                break;
            case 5:
                Intent eraIntent = new Intent(this, EraActivity.class);
                startActivityForResult(eraIntent, 5);
                break;
            case 6:
                onLossesClick();
                statList.put(StatList.Stat.Losses, Float.parseFloat(String.valueOf(currentPlayer.getLosses())));
                break;

            case 7:
                onHitsClick();
                statList.put(StatList.Stat.Hits, Float.parseFloat(String.valueOf(currentPlayer.getHits())));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1234:
                if (resultCode == RESULT_OK)
                {
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
                break;
            case 2:
                statList.put(StatList.Stat.Slugging_Percentage, currentPlayer.getSlugg());
                break;
            case 4:
                statList.put(StatList.Stat.Batting_Average, currentPlayer.getBatAvg());
                break;
            case 5:
                statList.put(StatList.Stat.ERA, currentPlayer.getERA());
                break;
            default:
                // Nothing for now.
        }
        statArrayAdapter.notifyDataSetChanged();

    }

    ;

    public void btnChoosePhotoPressed(View view)
    {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
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
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String firstname = input.getText().toString();
                        String lastname = input.getText().toString();

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


    public void backToRosterProfileOnClick(MenuItem item)
    {
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
                        if(Utility.getInstance().isEmpty(input) || input.getText().toString().length() < 10 || input.getText().toString().length() > 10)
                        {
                            showToastMessage("The phone number is either too short or too long...");
                        }
                        else
                        {
                            currentPlayer.setPhoneNumber(Utility.getInstance().setPhoneNumberFormat(newNumber));
                            numberText.setText(currentPlayer.getPhoneNumber());
                        }
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
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String newNumber = input.getText().toString();
                        //check if any other player on the team has the same number
                        Team currentTeam = TeamList.getInstance().getTeam(currentPlayer.getTeamName());
                        for (Player x : currentTeam.getRoster()) {
                            //if the numbers are the same
                            if (newNumber.equals(x.getPlayerNumber())) {
                                showToastMessage("Another player on your team has that number...Please enter another one"); // show message
                            } else {
                                currentPlayer.setPlayerNumber(newNumber);
                                WelcomeActivity.teamDB.updatePlayer(currentPlayer);
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

        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle("Change Player Position");
        alertDialog.setMessage("Enter position: ");

        final EditText input = new EditText(PlayerProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setButton("Confirm",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String newPosition = input.getText().toString();
                        currentPlayer.setPosition(newPosition);
                        WelcomeActivity.teamDB.updatePlayer(currentPlayer);
                        positionText.setText(currentPlayer.getPosition());

                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!







//
//        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
//        alertDialog.setTitle("Change Player Number");
//        alertDialog.setMessage("Select Position: ");
//
//        String[] items = new String[]{"P", "C", "1B", "2B", "SS", "3B", "LF", "CF", "RF"};
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View SpinnerView = inflater.inflate(R.layout.spinner_position, null);
//
//        final Spinner dropdown = (Spinner) findViewById(R.id.profile_position_list);
//
//        alertDialog.setView(SpinnerView);
//
//         playerPosition = dropdown.toString();
//        positionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        dropdown.setAdapter(positionAdapter);
//
//
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                Log.v("item", (String) parent.getItemAtPosition(position));
//
//                switch (position) {
//                    case 0:
//                        playerPosition = positionAdapter.getItem(0);
//                        break;
//                    case 1:
//                        playerPosition = positionAdapter.getItem(1);
//                        break;
//                    case 2:
//                        playerPosition = positionAdapter.getItem(2);
//                        break;
//                    case 3:
//                        playerPosition = positionAdapter.getItem(3);
//                        break;
//                    case 4:
//                        playerPosition = positionAdapter.getItem(4);
//                        break;
//                    case 5:
//                        playerPosition = positionAdapter.getItem(5);
//                        break;
//                    case 6:
//                        playerPosition = positionAdapter.getItem(6);
//                        break;
//                    case 7:
//                        playerPosition = positionAdapter.getItem(7);
//                        break;
//                    case 8:
//                        playerPosition = positionAdapter.getItem(8);
//                        break;
//
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//
//        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentPlayer.setPosition(playerPosition);
//                        positionText.setText(currentPlayer.getPosition());
//                    }
//                });
//
//
//        alertDialog.setCanceledOnTouchOutside(true);
//
//
//        alertDialog.show();  //<-- See This!


    }


    public void onHitsClick() {
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update

        LayoutInflater inflater = getLayoutInflater();

        final View statsPickerView = inflater.inflate(R.layout.numericstatpicker, null);

        alertDialog.setView(statsPickerView);
        final NumberPicker p = (NumberPicker)statsPickerView.findViewById(R.id.numericstatvalue);
        TextView text = (TextView)statsPickerView.findViewById(R.id.numericstatname);
        text.setText("Hits");

        p.setMinValue(0);
        p.setMaxValue(10000);
        p.setWrapSelectorWheel(false);
        p.setValue(currentPlayer.getHits());

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int statValue = p.getValue();
                        currentPlayer.setHits(statValue);
                        WelcomeActivity.teamDB.updateHits(currentPlayer);
                        statList.put(StatList.Stat.Hits, (float) statValue);

                        statArrayAdapter.notifyDataSetChanged();

                    }
                });



        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!

    }


    public void onWalksClick()
    {
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update

        LayoutInflater inflater = getLayoutInflater();

        final View statsPickerView = inflater.inflate(R.layout.numericstatpicker, null);

        alertDialog.setView(statsPickerView);
        final NumberPicker p = (NumberPicker)statsPickerView.findViewById(R.id.numericstatvalue);
        TextView text = (TextView)statsPickerView.findViewById(R.id.numericstatname);
        text.setText("Walks");

        p.setMinValue(0);
        p.setMaxValue(10000);
        p.setWrapSelectorWheel(false);
        p.setValue(currentPlayer.getWalks());

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int statValue = p.getValue();
                        currentPlayer.setWalks(statValue);
                        WelcomeActivity.teamDB.updateWalks(currentPlayer);
                        statList.put(StatList.Stat.Walks, (float) statValue);

                        statArrayAdapter.notifyDataSetChanged();

                    }
                });



        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!

    }

    public void onWinsClick(){

        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update

        LayoutInflater inflater = getLayoutInflater();

        final View statsPickerView = inflater.inflate(R.layout.numericstatpicker, null);

        alertDialog.setView(statsPickerView);
        final NumberPicker p = (NumberPicker)statsPickerView.findViewById(R.id.numericstatvalue);
        TextView text = (TextView)statsPickerView.findViewById(R.id.numericstatname);
        text.setText("Wins");

        p.setMinValue(0);
        p.setMaxValue(10000);
        p.setWrapSelectorWheel(false);
        p.setValue(currentPlayer.getWins());

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int statValue = p.getValue();
                        currentPlayer.setWins(statValue);
                        WelcomeActivity.teamDB.updateWins(currentPlayer);
                        statList.put(StatList.Stat.Wins, (float) statValue);

                        statArrayAdapter.notifyDataSetChanged();

                    }
                });



        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!


    }


    public void onRbiClick(){
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update

        LayoutInflater inflater = getLayoutInflater();

        final View statsPickerView = inflater.inflate(R.layout.numericstatpicker, null);

        alertDialog.setView(statsPickerView);
        final NumberPicker p = (NumberPicker)statsPickerView.findViewById(R.id.numericstatvalue);
        TextView text = (TextView)statsPickerView.findViewById(R.id.numericstatname);
        text.setText("RBIs");

        p.setMinValue(0);
        p.setMaxValue(10000);
        p.setWrapSelectorWheel(false);
        p.setValue(currentPlayer.getRBI());

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int statValue = p.getValue();
                        currentPlayer.setRBI(statValue);
                        WelcomeActivity.teamDB.updateRBI(currentPlayer);
                        statList.put(StatList.Stat.RBI, (float) statValue);

                        statArrayAdapter.notifyDataSetChanged();

                    }
                });



        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!


    }


    public void onLossesClick()
    {
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update

        LayoutInflater inflater = getLayoutInflater();

        final View statsPickerView = inflater.inflate(R.layout.numericstatpicker, null);

        alertDialog.setView(statsPickerView);
        final NumberPicker p = (NumberPicker)statsPickerView.findViewById(R.id.numericstatvalue);
        TextView text = (TextView)statsPickerView.findViewById(R.id.numericstatname);
        text.setText("Losses");

        p.setMinValue(0);
        p.setMaxValue(10000);
        p.setWrapSelectorWheel(false);
        p.setValue(currentPlayer.getLosses());

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int statValue = p.getValue();
                        currentPlayer.setLosses(statValue);
                        WelcomeActivity.teamDB.updateLosses(currentPlayer);
                        statList.put(StatList.Stat.Losses, (float) statValue);

                        statArrayAdapter.notifyDataSetChanged();

                    }
                });



        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });


        alertDialog.setCanceledOnTouchOutside(true);


        alertDialog.show();  //<-- See This!


    }




}// end Activity