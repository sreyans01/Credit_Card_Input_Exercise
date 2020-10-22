package app.wildcardcoder.creditcardinputexercise.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import app.wildcardcoder.creditcardinputexercise.HelperFunctions.CardNumber;
import app.wildcardcoder.creditcardinputexercise.R;

public class MainActivity extends AppCompatActivity{
    private Context context = MainActivity.this;
    public static final String TAG = "MainActivity";

    TextInputEditText e_cardnumber,e_mmyy,e_securitycode,e_firstname,e_lastname;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //A function where we assign the views with their respective ids.
        allfindviewbyids();
        //A function where we specify the OnClicks() of the views.
        HandleOnClicks();

    }

    private void allfindviewbyids(){
        submitBtn = findViewById(R.id.submitBtn);
        e_cardnumber = findViewById(R.id.e_cardnumber);
        e_mmyy       = findViewById(R.id.e_mmyy);
        e_securitycode = findViewById(R.id.e_securitycode);
        e_firstname = findViewById(R.id.e_firstname);
        e_lastname = findViewById(R.id.e_lastname);
    }
    private void HandleOnClicks() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkIsFieldEmpty(e_cardnumber,e_mmyy,e_securitycode,e_firstname,e_lastname)){
                    // Show a Toast message if the fields are left empty by the user.
                    Toast.makeText(context, getString(R.string.fill_fields),Toast.LENGTH_SHORT).show();
                    return;
                }

                //Assigning the default card number to avoid NullPointerException
                long cardnumber = 123456;
                if(!TextUtils.isEmpty(e_cardnumber.getText())){
                    cardnumber = Long.parseLong(e_cardnumber.getText().toString());
                }

                CardNumber cardNumber = new CardNumber();
                //The below function will check if the card number entered is a valid card number. It uses Luhn's Algorithm.
                boolean isValid = cardNumber.isValid(cardnumber);

                if(isValid) {
                    Log.d(TAG,getString(R.string.log_validcard));
                    String securityCode = e_securitycode.getText().toString();
                    String americanCardCheck = cardNumber.toString();
                    if(americanCardCheck.startsWith("34") || americanCardCheck.startsWith("37")){

                        // American Express Card starts with 34 or 37, and has a CID number of 4 digits.
                        if(securityCode.length()!=4){
                            e_securitycode.setError(getString(R.string.error_invalid_security_code));
                            return;
                        }
                    }else{
                        //Other than American Express Cards, a valid security code must be of 3 digits.
                        if(securityCode.length()!=3){
                            e_securitycode.setError(getString(R.string.error_invalid_security_code));
                            return;
                        }
                    }

                    // Show Alert Dialog when all the details are correct.
                    showAlertDialog("Payment Successful");
                    nullifyFields(e_cardnumber,e_mmyy,e_securitycode,e_firstname,e_lastname);
                }
                else {
                    Log.d(TAG,"Hi there. Card not valid.");
                    e_cardnumber.setError(getString(R.string.error_invalid_card_number));
                }


            }
        });
    }

    private void showAlertDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);


        builder.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // A function to check whether the given edittexts are empty or not.
    private boolean checkIsFieldEmpty(EditText... editTexts){
        boolean check = false;
        for(EditText editText : editTexts){
            if(TextUtils.isEmpty(editText.getText()))
                check = true;
        }
        return check;
    }
    // A function to set the fields to null
    private void nullifyFields(EditText... editTexts){

        for(EditText editText : editTexts){
            editText.setText("");
        }

    }

}