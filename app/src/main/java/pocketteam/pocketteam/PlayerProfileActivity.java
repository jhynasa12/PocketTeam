package pocketteam.pocketteam;

import android.app.AlertDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        currentPlayer = TeamList.getInstance().findPlayerByName(firstName,lastName);

        if(currentPlayer.getProfilePicture() != null){
            image.setImageBitmap(currentPlayer.getProfilePicture());
        }


        statList = StatList.getInstance().getStats();

       statArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,statList);
        ListView listView = (ListView) findViewById(R.id.statlist);
        listView.setAdapter(statArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = statList.get(position);
                statDetail(stat,position);
            }
        });


    }

    private void statDetail(String stat, int position) {

        if(stat.equals(StatList.Stat.Batting_Average.name())){
            Intent batAvgIntent = new Intent(this,BattingAvgActivity.class);
            startActivity(batAvgIntent);

            openStatDialogBox("Batting Average", currentPlayer.getBatAvg());


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

    public void btnChoosePhotoPressed(View view)
    {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
    }



    public void OnClickDialogEventHandler(View view) {


        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity)this).create(); //Read Update
        alertDialog.setTitle("This is a dialog box");
        alertDialog.setMessage("this is my app");

        alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });

        alertDialog.show();  //<-- See This!
    }


    public void btnRosterOnClickEventHandler(View view) {

        finish();
    }


    public void openStatDialogBox(String stat,float number)
    {
        AlertDialog alertDialog = new AlertDialog.Builder((PlayerProfileActivity) this).create(); //Read Update
        alertDialog.setTitle(stat);
        alertDialog.setMessage("Your " + stat + " is " + number);


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


}// end Activity
