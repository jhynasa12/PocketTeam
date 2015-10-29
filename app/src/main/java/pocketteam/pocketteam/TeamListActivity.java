package pocketteam.pocketteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TeamListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

    }

    public void AddTeamOnClickEventHandler(View view) {
        Intent addTeamIntent = new Intent(this,AddTeamActivity.class);
        startActivity(addTeamIntent);

    }
}
