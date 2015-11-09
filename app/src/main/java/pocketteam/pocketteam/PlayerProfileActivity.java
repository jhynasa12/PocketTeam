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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class PlayerProfileActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 12;
    private static int IMAGE_GALLERY = 10;
    public static String stats;
    public static String playerName;
    public static final String test = "TEST";
    private Bitmap yourSelectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);


        playerName = getIntent().getStringExtra(RosterActivity.PLAYER_NAME);
        TextView rosterText = (TextView) findViewById(R.id.player_profile_name);
        rosterText.setText(playerName);


        //        List<Stat> stat = StatDataProvider.getData();

        // Player currentPlayer = TeamList.getInstance().getTeam().

//        ArrayAdapter<Stat> statArrayAdapter = new ArrayAdapter<Stat>(this, android.R.layout.simple_list_item_1, stat);
//        ListView listView = (ListView) findViewById(android.R.id.list);
//        listView.setAdapter(statArrayAdapter);
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

                    ImageView image = (ImageView) findViewById(R.id.imagePlayer);
                     yourSelectedImage = BitmapFactory.decodeFile(filePath);
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




//    Button more = (Button) findViewById(R.id.more);
//    more.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View view) {
//            //Intent myIntent = new Intent(view.getContext(), agones.class);
//            //startActivityForResult(myIntent, 0);
//
//
//            AlertDialog alertDialog = new AlertDialog.Builder(<YourActivityName>this).create(); //Read Update
//            alertDialog.setTitle("hi");
//            alertDialog.setMessage("this is my app");
//
//            alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    // here you can add functions
//                }
//            });
//
//            alertDialog.show();  //<-- See This!
//        }
//
//    }

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
}// end Activity
