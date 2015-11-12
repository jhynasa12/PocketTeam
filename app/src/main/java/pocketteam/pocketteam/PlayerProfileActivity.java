package pocketteam.pocketteam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    ArrayAdapter<String> statArrayAdapter;


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


        TextView positionText = (TextView) findViewById(R.id.player_position_profile);
        positionText.setText(currentPlayer.getPosition());

        TextView parentText = (TextView) findViewById(R.id.parent_name_profile);
        parentText.setText(currentPlayer.getParentName());


        TextView numberText = (TextView) findViewById(R.id.player_phone_number);
        numberText.setText(currentPlayer.getPhoneNumber());


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

                    case 2:
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


    }

    private void statDetail(String stat, int position) {

        if (stat.equals(StatList.Stat.Batting_Average.name())) {
            Intent batAvgIntent = new Intent(this, BattingAvgActivity.class);
            startActivity(batAvgIntent);

        } else if (stat.equals(StatList.Stat.ERA.name())) {

            Intent eraIntent = new Intent(this, EraActivity.class);
            startActivity(eraIntent);

        } else if (stat.equals(StatList.Stat.Hits.name())) {
            Intent hitsIntent = new Intent(this, HitsActivity.class);
            startActivity(hitsIntent);

        } else if (stat.equals(StatList.Stat.Slugging_Percentage.name())) {
            Intent sluggingIntent = new Intent(this, SluggingActivity.class);
            startActivity(sluggingIntent);

        } else if (stat.equals(StatList.Stat.RBI.name())) {

            Intent rbiIntent = new Intent(this, RbiActivity.class);
            startActivity(rbiIntent);

        }

    }


    public void SprayChartOnClickEventHandler(View view) {
        Intent sprayChartIntent = new Intent(this, SprayChartActivity.class);
        startActivity(sprayChartIntent);

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


    public void btnRosterOnClickEventHandler(View view) {

        finish();
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



}// end Activity
