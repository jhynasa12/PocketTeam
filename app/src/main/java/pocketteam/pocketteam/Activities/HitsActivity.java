package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.Data.StatList;
import pocketteam.pocketteam.R;

public class HitsActivity extends AppCompatActivity {
    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hits_layout);

        currentPlayer = PlayerProfileActivity.currentPlayer;
    }





    public void hitsCancelButton(View view) {
        finish();

    }

    public void hitsOkBtnHandler(View view) {

        final EditText hits = (EditText) findViewById(R.id.hits_text);
        if(isEmpty(hits) == true){

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }else{

            currentPlayer.setHits(Integer.valueOf(hits.getText().toString()));

            StatList.getInstance().getMap().put(StatList.Stat.Hits, Float.parseFloat(String.valueOf(currentPlayer.getHits())));

            finish();



        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
