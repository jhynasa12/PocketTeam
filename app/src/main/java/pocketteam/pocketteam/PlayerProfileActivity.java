package pocketteam.pocketteam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class PlayerProfileActivity extends AppCompatActivity {

    private static int IMAGE_GALLERY = 10;
    public static String stats;
    public static String playerName;
    public static final String test = "TEST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        playerName = getIntent().getStringExtra(RosterActivity.PLAYER_NAME);
        TextView rosterText = (TextView) findViewById(R.id.player_profile_name);
        rosterText.setText(playerName);


 //       List<Stat> stat = StatDataProvider.getData();

        //Player currentPlayer = TeamList.getInstance().getTeam()

//        ArrayAdapter<Stat> statArrayAdapter =
//                new ArrayAdapter<Stat>(this, android.R.layout.simple_list_item_1, stat);
//        ListView listView = (ListView) findViewById(android.R.id.list);
//        listView.setAdapter(statArrayAdapter);
    }


    public void ProfilePictureOnClickEventHandler(View view) {
        //we want to pick an image from a gallery, specify action pic
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        //
        //give me the file path to the directory
      String path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();

        //convert to URI
        Uri picturesDirectory = Uri.parse(path);

        //set data and look for files
        galleryIntent.setDataAndType(picturesDirectory, "image/*");
        //

        //startActivity
        startActivityForResult(galleryIntent, IMAGE_GALLERY );

    }


    public void SprayChartOnClickEventHandler(View view) {
        Intent sprayChartIntent = new Intent(this, SprayChartActivity.class);
        startActivity(sprayChartIntent);

    }
}
