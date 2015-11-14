package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.R;

public class HomeRunActivity extends AppCompatActivity {
    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_run_layout);

        currentPlayer = PlayerProfileActivity.currentPlayer;
    }

    public void hrOkBtnHandler(View view) {


        final EditText hr = (EditText) findViewById(R.id.home_runs_text);
        if(isEmpty(hr) == true){

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }else{

            currentPlayer.setHomeRuns(Integer.valueOf(hr.getText().toString()));

            finish();



        }


    }

    public void hrCancelBtn(View view) {

        finish();

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
