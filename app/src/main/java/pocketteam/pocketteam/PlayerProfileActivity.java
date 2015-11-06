package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class PlayerProfileActivity extends AppCompatActivity {

    public static String stats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);
 //       List<Stat> stat = StatDataProvider.getData();

        //Player currentPlayer = TeamList.getInstance().getTeam()

//        ArrayAdapter<Stat> statArrayAdapter =
//                new ArrayAdapter<Stat>(this, android.R.layout.simple_list_item_1, stat);
//        ListView listView = (ListView) findViewById(android.R.id.list);
//        listView.setAdapter(statArrayAdapter);
    }



    public void SprayChartOnClickEventHandler(View view) {

        Intent sprayChartIntent = new Intent(this, SprayChartActivity.class);
        startActivity(sprayChartIntent);

    }

    public void ProfilePictureOnClickEventHandler(View view) {
        Intent cameraIntent = new Intent(Intent.CATEGORY_APP_GALLERY);
        startActivity(cameraIntent);

    }
}
