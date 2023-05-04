package com.example.moneysensev1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegistrationActivity1 extends AppCompatActivity {

    private EditText mUsername;
    private RadioGroup radioGroupReg_Gender;
    private RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    private TextView date_select_txt;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button btnNext;
    private TextView mSignIn;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        //Radio Buttons for Gender
        radioGroupReg_Gender = findViewById(R.id.radio_group_reg_gender);
        maleRadioButton = findViewById(R.id.radio_male);
        femaleRadioButton = findViewById(R.id.radio_female);
        otherRadioButton = findViewById(R.id.radio_others);

        //Date Picker
        initDatePicker();
        dateButton = findViewById(R.id.date_picker_button);

        mUsername = (EditText) findViewById(R.id.username_reg);
        date_select_txt = (TextView) findViewById(R.id.select_birthday_txt);
        dateButton = (Button) findViewById(R.id.date_picker_button);
        btnNext = (Button) findViewById(R.id.btn_next);
        mSignIn = (TextView) findViewById(R.id.signin_here3);

        mDialog = new ProgressDialog(this);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Set only 1 radio button is selected
        RadioGroup gender_radio_group = findViewById(R.id.radio_group_reg_gender);
        gender_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Get the ID of the currently checked RadioButton
                int selectedGenderId = gender_radio_group.getCheckedRadioButtonId();
                // Deselect any previously selected RadioButton
                if (selectedGenderId != -1 && selectedGenderId != checkedId) {
                    RadioButton previouslySelectedGenderRadioButton = findViewById(selectedGenderId);
                    previouslySelectedGenderRadioButton.setChecked(false);
                }
            }
        });

        //Next button functionality
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId = radioGroupReg_Gender.getCheckedRadioButtonId();

                //Obtain data
                String username = mUsername.getText().toString();
                String date = dateButton.getText().toString();

                if (TextUtils.isEmpty(username)){
                    mUsername.setError("Field can't be empty");
                }

                else if (username.length()<4){
                    Toast.makeText(RegistrationActivity1.this,"Username should be at least 4 characters",Toast.LENGTH_LONG).show();
                    mUsername.setError("Invalid username");
                    mUsername.requestFocus();
                }

                else if (selectedGenderId == -1) {
                    Toast.makeText(getApplicationContext(),"Please select a gender",Toast.LENGTH_SHORT).show();
                }

                else if (date.isEmpty()) {
                    date_select_txt.setError("Birthday input is required");
                }

                else {
                    RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
                    String selectedGender = selectedGenderRadioButton.getText().toString();

                    Intent intent = new Intent(RegistrationActivity1.this,RegistrationActivity2.class);
                    intent.putExtra("selectedGender", selectedGender);
                    startActivity(intent);

                }
            }
        });

    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    //end of date picker

}

