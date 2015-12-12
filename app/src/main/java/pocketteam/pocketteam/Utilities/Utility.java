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

    /**
     * Singleton Pattern
     * @return
     */

    public static Utility getInstance(){

        if(uniqueInstance == null){
            uniqueInstance = new Utility();
        }

        return uniqueInstance;
    }


    /**
     * Checks to see if the text field is Empty
     * @param etText
     * @return
     */
    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


    /**
     * Sets the phone number to correct format
     * @param number
     * @return
     */
    public String setPhoneNumberFormat(String number) {
        number = "(" + number.substring(0, 3) + ")-" + number.substring(3, 6) + "-" + number.substring(6, 10);
        return number;
    }


}
