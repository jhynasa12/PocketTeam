package pocketteam.pocketteam.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketteam.pocketteam.Data.Player;
import pocketteam.pocketteam.R;

public class RbiActivity extends AppCompatActivity {

    public static Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rbi_layout);

        currentPlayer = PlayerProfileActivity.currentPlayer;
    }

    public void rbiCancelBtn(View view) {
        finish();
    }

    public void rbiOkBtnHandler(View view) {

        final EditText rbis = (EditText) findViewById(R.id.rbis_text);
        if(isEmpty(rbis) == true){

            Context context = getApplicationContext();
            CharSequence text = "You're missing a field...";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        }else{

            currentPlayer.setRBI(Integer.valueOf(rbis.getText().toString()));

            finish();



        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
