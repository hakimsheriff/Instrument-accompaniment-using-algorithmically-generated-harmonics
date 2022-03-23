package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "DemoInitialApp";
    Spinner keySpinner;
    Spinner majorMinorSpinner;
    SeekBar seekbarBPM;
    SeekBar seekbarLength;
    String message = "SGB000G000KA00O0A00000S0000000";
    boolean keyManualAuto = true;
    boolean arpegiatorEnabled = true;
    boolean sequentialRandom = true;
    boolean singleContinuous = true;
    boolean lockedKey = true;
    boolean lockedTime = true;
    int octaveNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        seekbarBPM = (SeekBar)findViewById(R.id.seekBar);
        seekbarBPM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //this is where you use the value 'progress'
                message = message.substring(0,3)+(progress/100)+((progress/10)%10)+(progress%10)+message.substring(6);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarLength = (SeekBar)findViewById(R.id.seekBar2);
        seekbarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //this is where you use the value 'progress'
                message = message.substring(0,7)+(progress/100)+((progress/10)%10)+(progress%10)+message.substring(10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        keySpinner = findViewById(R.id.spinnerKeys);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keySpinner.setAdapter(adapter2);
        keySpinner.setOnItemSelectedListener(this);

        majorMinorSpinner = findViewById(R.id.spinnerMajorMinor);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorMinorSpinner.setAdapter(adapter3);
        majorMinorSpinner.setOnItemSelectedListener(this);

        Button buttonKey = (Button) findViewById(R.id.keyManual);
        buttonKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(keyManualAuto) {
                    keyManualAuto = !keyManualAuto;
                    buttonKey.setText("Manual");
                    message = message.substring(0,11)+'M'+message.substring(12);
                    keySpinner.setEnabled(true);
                    majorMinorSpinner.setEnabled(true);
                    keySpinner.setVisibility(View.VISIBLE);
                    majorMinorSpinner.setVisibility(View.VISIBLE);
                } else {
                    keyManualAuto = !keyManualAuto;
                    buttonKey.setText("Auto");
                    message = message.substring(0,11)+'A'+message.substring(12);
                    message = message.substring(0,12)+'0'+'0'+message.substring(14);
                    keySpinner.setEnabled(false);
                    majorMinorSpinner.setEnabled(false);
                    keySpinner.setVisibility(View.INVISIBLE);
                    majorMinorSpinner.setVisibility(View.INVISIBLE);
                }
                //Log.i(TAG, "This is a magic log message!");
            }
        });

        Button buttonOctaveDown = (Button) findViewById(R.id.button_octave_down);
        buttonOctaveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(octaveNumber>1) {
                    octaveNumber--;
                }
                message = message.substring(0,14)+(char)(octaveNumber+48)+message.substring(15);
            }
        });

        Button buttonOctaveUp = (Button) findViewById(R.id.button_octave_up);
        buttonOctaveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(octaveNumber<7) {
                    octaveNumber++;
                }
                message = message.substring(0,14)+(char)(octaveNumber+48)+message.substring(15);
            }
        });

        Button buttonArpegiator = (Button) findViewById(R.id.buttonArpegiator);
        Button buttonSequential = (Button) findViewById(R.id.buttonSequential);
        Button buttonContinuous = (Button) findViewById(R.id.buttonContinuous);
        buttonArpegiator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(arpegiatorEnabled) {
                    arpegiatorEnabled = !arpegiatorEnabled;
                    buttonArpegiator.setText("Disabled");
                    message = message.substring(0,16)+'D'+message.substring(17);
                    buttonSequential.setEnabled(false);
                    buttonContinuous.setEnabled(false);
                    buttonSequential.setVisibility(View.INVISIBLE);
                    buttonContinuous.setVisibility(View.INVISIBLE);
                } else {
                    arpegiatorEnabled = !arpegiatorEnabled;
                    buttonArpegiator.setText("Enabled");
                    message = message.substring(0,16)+'E'+message.substring(17);
                    buttonSequential.setEnabled(true);
                    buttonContinuous.setEnabled(true);
                    buttonSequential.setVisibility(View.VISIBLE);
                    buttonContinuous.setVisibility(View.VISIBLE);
                }

            }
        });


        buttonSequential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(sequentialRandom) {
                    sequentialRandom = !sequentialRandom;
                    buttonSequential.setText("Sequential");
                    message = message.substring(0,17)+'S'+message.substring(18);
                } else {
                    sequentialRandom = !sequentialRandom;
                    buttonSequential.setText("Auto");
                    message = message.substring(0,17)+'R'+message.substring(18);
                }
            }
        });


        buttonContinuous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(singleContinuous) {
                    singleContinuous = !singleContinuous;
                    buttonContinuous.setText("Continuous");
                    message = message.substring(0,18)+'C'+message.substring(19);
                } else {
                    singleContinuous = !singleContinuous;
                    buttonContinuous.setText("Standard");
                    message = message.substring(0,18)+'S'+message.substring(19);
                }
            }
        });

        Button buttonLock = (Button) findViewById(R.id.buttonLock);
        buttonLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(lockedKey) {
                    lockedKey = !lockedKey;
                    buttonLock.setText("Free key");
                    message = message.substring(0,19)+'F'+message.substring(20);
                } else {
                    lockedKey = !lockedKey;
                    buttonLock.setText("Lock to key");
                    message = message.substring(0,19)+'L'+message.substring(20);
                }
            }
        });

        Button buttonLockTime = (Button) findViewById(R.id.buttonLockTime);
        buttonLockTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lockedTime) {
                    lockedTime = !lockedTime;
                    buttonLockTime.setText("Free time");
                    message = message.substring(0,20)+'L'+message.substring(21);
                } else {
                    lockedTime = !lockedTime;
                    buttonLockTime.setText("Lock to time");
                    message = message.substring(0,20)+'F'+message.substring(21);
                }
            }
        });

        Button buttonStart = (Button) findViewById(R.id.button);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                buttonStart.setEnabled(false);
                Toast.makeText(getApplicationContext(), "It's magic!", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        Button buttonPattern = (Button) findViewById(R.id.button_show_pattern);
        buttonPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");

            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            /*
            case R.id.radio_key_auto:
                keySpinner.setVisibility(View.INVISIBLE);
                majorMinorSpinner.setVisibility(View.INVISIBLE);
                break;
            case R.id.radio_key_manual:
                //show drop down menus
                keySpinner.setVisibility(View.VISIBLE);
                majorMinorSpinner.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_key_arpegiator:
                keySpinner.setVisibility(View.INVISIBLE);
                majorMinorSpinner.setVisibility(View.INVISIBLE);
                break;

             */
        }

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_1:

                break;
            case R.id.checkbox_2:

                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        if(text.equals("Major")) {

        } else if(text.equals("Minor")) {

        } else if(text.equals("A")) {
            message = message.substring(0,12)+'0'+'1'+message.substring(14);
        } else if(text.equals("A#/Bb")) {
            message = message.substring(0,12)+'0'+'2'+message.substring(14);
        } else if(text.equals("B")) {
            message = message.substring(0,12)+'0'+'3'+message.substring(14);
        } else if(text.equals("C")) {
            message = message.substring(0,12)+'0'+'4'+message.substring(14);
        } else if(text.equals("C#/Db")) {
            message = message.substring(0,12)+'0'+'5'+message.substring(14);
        } else if(text.equals("D")) {
            message = message.substring(0,12)+'0'+'6'+message.substring(14);
        } else if(text.equals("D#/Eb")) {
            message = message.substring(0,12)+'0'+'7'+message.substring(14);
        } else if(text.equals("E")) {
            message = message.substring(0,12)+'0'+'8'+message.substring(14);
        } else if(text.equals("F")) {
            message = message.substring(0,12)+'0'+'9'+message.substring(14);
        } else if(text.equals("F#/Gb")) {
            message = message.substring(0,12)+'1'+'0'+message.substring(14);
        } else if(text.equals("G")) {
            message = message.substring(0,12)+'1'+'1'+message.substring(14);
        } else if(text.equals("G#/Ab")) {
            message = message.substring(0,12)+'1'+'2'+message.substring(14);
        } else {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}