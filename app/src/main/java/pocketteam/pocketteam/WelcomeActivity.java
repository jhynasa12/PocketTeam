package pocketteam.pocketteam;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    DataHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myDb = new DataHelper(this);

        String fontPath = "fonts/varsity_regular.ttf";

        // text view label
        TextView txtVarsity = (TextView) findViewById(R.id.textView);


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtVarsity.setTypeface(tf);

    }

    public void ScoutModeOnClickEventHandler(View view) {

        Intent addPlayerIntent = new Intent(this, AddPlayerActivity.class);
        startActivity(addPlayerIntent);

    }

    public void TeamsOnClickEventHandler(View view) {

        Intent teamsIntent = new Intent(this, TeamListActivity.class);
        startActivity(teamsIntent);
    }
}
