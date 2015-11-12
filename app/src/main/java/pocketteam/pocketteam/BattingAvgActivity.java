package pocketteam.pocketteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class BattingAvgActivity extends AppCompatActivity {

    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batting_average_layout);

            currentPlayer = PlayerProfileActivity.currentPlayer;




    }


    public void batAvgeCanceBtn(View view) {
        finish();
    }

    public void batAvgOkBtn(View view) {

        final EditText atBats = (EditText) findViewById(R.id.atBats);
        final EditText hits = (EditText) findViewById(R.id.hits);
        if(isEmpty(atBats) || isEmpty(hits) == true){

        }else{

            currentPlayer.calcBatAvg(Float.valueOf(hits.getText().toString()),Float.valueOf(atBats.getText().toString()));

            finish();



        }

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
