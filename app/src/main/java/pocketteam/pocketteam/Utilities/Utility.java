package pocketteam.pocketteam.Utilities;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by hylan on 11/14/2015.
 */
public class Utility {

    private static Utility uniqueInstance;
    private Context applicationContext;

    public Utility(){

    }


    public static Utility getInstance(){

        if(uniqueInstance == null){
            uniqueInstance = new Utility();
        }

        return uniqueInstance;
    }


    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


    public String setPhoneNumberFormat(String number) {
        number = "(" + number.substring(0, 3) + ")-" + number.substring(3, 6) + "-" + number.substring(6, 10);
        return number;
    }

    public void callToastMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}
