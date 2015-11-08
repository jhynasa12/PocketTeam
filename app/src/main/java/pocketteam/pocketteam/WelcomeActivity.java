package pocketteam.pocketteam;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    DataHelper myDb;
    EditText editFirstName, editLastName, editPosition;
    Button btnAddData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myDb = new DataHelper(this);

        editPosition = (EditText) findViewById(R.id.editPlayerPosition);
        editFirstName = (EditText) findViewById(R.id.editPlayerPosition);
        editLastName = (EditText) findViewById(R.id.editPlayerPosition);
        btnAddData = (Button) findViewById(R.id.add_player_button);
        AddData();

        String fontPath = "fonts/varsity_regular.ttf";

        // text view label
        TextView txtVarsity = (TextView) findViewById(R.id.textView);


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtVarsity.setTypeface(tf);

    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                       boolean isInserted = myDb.insertData(editFirstName.getText().toString(), editLastName.getText().toString(), editPosition.getText().toString() );
                        if (isInserted = true)
                            Toast.makeText(WelcomeActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(WelcomeActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                    }
                }
        );
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
