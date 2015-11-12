package pocketteam.pocketteam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SluggingActivity extends AppCompatActivity {
    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slugging_layout);

        currentPlayer = PlayerProfileActivity.currentPlayer;
    }

    public void sluggingOkBtnHandler(View view) {

        final EditText singles = (EditText) findViewById(R.id.singles_slugging);
        final EditText doubles = (EditText) findViewById(R.id.doubles_slugging);
        final EditText triples = (EditText) findViewById(R.id.triples_slugging);
        final EditText homeruns = (EditText) findViewById(R.id.homeruns_slugging);
        final EditText atBats = (EditText) findViewById(R.id.atbats_slugging);


        if(isEmpty(singles) || isEmpty(doubles) || isEmpty(triples) || isEmpty(homeruns) || isEmpty(atBats) == true){

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }else {


            currentPlayer.calcSlugg(Float.valueOf(singles.getText().toString()), Float.valueOf(doubles.getText().toString()), Float.valueOf(triples.getText().toString()), Float.valueOf(homeruns.getText().toString()), Float.valueOf(atBats.getText().toString()));

            finish();
        }

    }

    public void sluggingCancelBtn(View view) {
        finish();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
