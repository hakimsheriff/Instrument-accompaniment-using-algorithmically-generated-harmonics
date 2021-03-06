package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.myapplication.MainActivity.remoteControl;

public class SettingsScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final String EXTRA_TEXT = "com.example.myapplication.EXTRA_TEXT";

    private static final String TAG = "DemoInitialApp";
    Spinner keySpinner;
    Spinner majorMinorSpinner;
    SeekBar seekbarBPM;
    SeekBar seekbarLength;
    TextView textBPM;
    TextView textLength;
    String message = "XGB000G000KM09O1AERSFFS00000000F0";
    boolean keyManualAuto = false;
    boolean arpegiatorEnabled = true;
    boolean tuningEnabled = true;
    boolean debugEnabled = true;
    boolean sequentialRandom = true;
    boolean singleContinuous = true;
    boolean lockedKey = true;
    boolean lockedTime = true;
    int octaveNumber = 1;
    TextView buttonPattern;
    TextView octave;

    private Button stopButton;
    private Button toDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        getSupportActionBar().setTitle("guitARP Companion");


        stopButton = (Button) findViewById(R.id.buttonStop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStop();
            }
        });

        toDisplay = (Button) findViewById(R.id.buttonDisplay);
        toDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplayActivity();
            }
        });

        textBPM = findViewById(R.id.textBPM);
        textLength = findViewById(R.id.textLength);

        buttonPattern = findViewById(R.id.textDebug);
        octave = findViewById(R.id.textOctave);


        seekbarBPM = (SeekBar)findViewById(R.id.seekBar);
        seekbarBPM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //this is where you use the value 'progress'
                message = message.substring(0,3)+(progress/100)+((progress/10)%10)+(progress%10)+message.substring(6);
                textBPM.setText("BPM : " + progress);
                buttonPattern.setText(message);
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
                textLength.setText("Note length : " + progress);
                buttonPattern.setText(message);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        keySpinner = findViewById(R.id.spinnerKeys);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.keys, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keySpinner.setAdapter(adapter2);
        keySpinner.setOnItemSelectedListener(this);

        majorMinorSpinner = findViewById(R.id.spinnerMajorMinor);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.MajorMinor, android.R.layout.simple_spinner_item);
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
                buttonPattern.setText(message);
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
                    String tempOctave = Integer.toString(octaveNumber);
                    octave.setText("Octave : " + tempOctave);
                }
                message = message.substring(0,15)+(char)(octaveNumber+48)+message.substring(16);
                buttonPattern.setText(message);
            }
        });

        Button buttonOctaveUp = (Button) findViewById(R.id.button_octave_up);
        buttonOctaveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(octaveNumber<7) {
                    octaveNumber++;
                    String tempOctave = Integer.toString(octaveNumber);
                    octave.setText("Octave : " + tempOctave);
                }
                message = message.substring(0,15)+(char)(octaveNumber+48)+message.substring(16);
                buttonPattern.setText(message);
            }
        });

        Button buttonArpegiator = (Button) findViewById(R.id.buttonArpegiator);
        Switch sequential = (Switch) findViewById(R.id.buttonSequential);
        Switch continuous = (Switch) findViewById(R.id.buttonContinuous);
        Switch lockKey = (Switch) findViewById(R.id.buttonLockKey);
        Switch lockTime = (Switch) findViewById(R.id.buttonLockTime);
        LinearLayout checkboxes = (LinearLayout) findViewById(R.id.linearLayout);

        buttonArpegiator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(arpegiatorEnabled) {
                    arpegiatorEnabled = !arpegiatorEnabled;
                    buttonArpegiator.setText("Disabled");
                    message = message.substring(0,17)+'D'+message.substring(18);
                    sequential.setEnabled(false);
                    continuous.setEnabled(false);
                    sequential.setVisibility(View.INVISIBLE);
                    continuous.setVisibility(View.INVISIBLE);
                    lockKey.setEnabled(false);
                    lockTime.setEnabled(false);
                    lockKey.setVisibility(View.INVISIBLE);
                    lockTime.setVisibility(View.INVISIBLE);
                    checkboxes.setEnabled(false);
                    checkboxes.setVisibility(View.INVISIBLE);
                } else {
                    arpegiatorEnabled = !arpegiatorEnabled;
                    buttonArpegiator.setText("Enabled");
                    message = message.substring(0,17)+'E'+message.substring(18);
                    sequential.setEnabled(true);
                    continuous.setEnabled(true);
                    sequential.setVisibility(View.VISIBLE);
                    continuous.setVisibility(View.VISIBLE);
                    lockKey.setEnabled(true);
                    lockTime.setEnabled(true);
                    lockKey.setVisibility(View.VISIBLE);
                    lockTime.setVisibility(View.VISIBLE);
                    checkboxes.setEnabled(true);
                    checkboxes.setVisibility(View.VISIBLE);
                }
                buttonPattern.setText(message);
            }
        });


        sequential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if(isChecked) {
                    message = message.substring(0,18)+'S'+message.substring(19);
                } else {
                    message = message.substring(0,18)+'R'+message.substring(19);
                }
                buttonPattern.setText(message);
            }
        });

        continuous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if(isChecked) {
                    message = message.substring(0,19)+'C'+message.substring(20);
                } else {
                    message = message.substring(0,19)+'S'+message.substring(20);
                }
                buttonPattern.setText(message);
            }
        });

        lockKey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if(isChecked) {
                    message = message.substring(0,20)+'L'+message.substring(21);
                } else {
                    message = message.substring(0,20)+'F'+message.substring(21);
                }
                buttonPattern.setText(message);
            }
        });

        lockTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if(isChecked) {
                    message = message.substring(0,21)+'L'+message.substring(22);
                } else {
                    message = message.substring(0,21)+'F'+message.substring(22);
                }
                buttonPattern.setText(message);
            }
        });

        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                openActivity2();
            }
        });

        Button buttonDebug = (Button) findViewById(R.id.buttonDebug);
        buttonDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(debugEnabled) {
                    debugEnabled = !debugEnabled;
                    buttonPattern.setEnabled(false);
                    buttonPattern.setVisibility(View.INVISIBLE);
                } else {
                    debugEnabled = !debugEnabled;
                    buttonPattern.setEnabled(true);
                    buttonPattern.setVisibility(View.VISIBLE);
                }
            }
        });

        Button buttonTuning = (Button) findViewById(R.id.buttonTuningModes);
        Button buttonC1 = (Button) findViewById(R.id.buttonPlayC1);
        Button buttonA4 = (Button) findViewById(R.id.buttonPlayA4);
        Button buttonC6 = (Button) findViewById(R.id.buttonPlayC6);
        buttonTuning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                if(tuningEnabled) {
                    tuningEnabled = !tuningEnabled;
                    buttonC1.setEnabled(false);
                    buttonC1.setVisibility(View.INVISIBLE);
                    buttonA4.setEnabled(false);
                    buttonA4.setVisibility(View.INVISIBLE);
                    buttonC6.setEnabled(false);
                    buttonC6.setVisibility(View.INVISIBLE);
                } else {
                    tuningEnabled = !tuningEnabled;
                    buttonC1.setEnabled(true);
                    buttonC1.setVisibility(View.VISIBLE);
                    buttonA4.setEnabled(true);
                    buttonA4.setVisibility(View.VISIBLE);
                    buttonC6.setEnabled(true);
                    buttonC6.setVisibility(View.VISIBLE);
                }
                buttonPattern.setText(message);
            }
        });
        buttonC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tune = "X000000000000000000000000000000T0";
                remoteControl.sendWord(tune);
            }
        });
        buttonA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tune = "X000000000000000000000000000000T1";
                remoteControl.sendWord(tune);
            }
        });
        buttonC6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tune = "X000000000000000000000000000000T2";
                remoteControl.sendWord(tune);
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

    boolean check1 = false;
    boolean check2 = false;
    boolean check3 = false;
    boolean check4 = false;
    boolean check5 = false;
    boolean check6 = false;
    boolean check7 = false;
    boolean theChecker = true;
    int index = 1;
    int total = 0;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_1:
                check1 = !check1;
                break;
            case R.id.checkbox_2:
                check2 = !check2;
                break;
            case R.id.checkbox_3:
                check3 = !check3;
                break;
            case R.id.checkbox_4:
                check4 = !check4;
                break;
            case R.id.checkbox_5:
                check5 = !check5;
                break;
            case R.id.checkbox_6:
                check6 = !check6;
                break;
            case R.id.checkbox_7:
                check7 = !check7;
                break;
        }

        message = message.substring(0,24)+'0'+'0'+'0'+'0'+'0'+'0'+'0' + message.substring(31);

        if(check1) {
            message = message.substring(0,24+index)+'0'+message.substring(25+index);
            index++;
            total++;
        }
        if(check2) {
            message = message.substring(0,24+index)+'1'+message.substring(25+index);
            index++;
            total++;
        }
        if(check3) {
            message = message.substring(0,24+index)+'2'+message.substring(25+index);
            index++;
            total++;
        }
        if(check4) {
            message = message.substring(0,24+index)+'3'+message.substring(25+index);
            index++;
            total++;
        }
        if(check5) {
            message = message.substring(0,24+index)+'4'+message.substring(25+index);
            index++;
            total++;
        }
        if(check6) {
            message = message.substring(0,24+index)+'5'+message.substring(25+index);
            index++;
            total++;
        }
        if(check7) {
            message = message.substring(0,24+index)+'6'+message.substring(25+index);
            index++;
            total++;
        }

        message = message.substring(0,23)+total+message.substring(24);
        buttonPattern.setText(message);
        index = 0;
        total = 0;
    }


    String list1 = "";
    String list2 = "";
    String text = "";


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text2 = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        if(text2.equals("Major")) {
            list1 = "Major";
        } else if(text2.equals("Minor")) {
            list1 = "Minor";
        } else {
            text = text2;
        }
        if(list1.equals("Major")) {
            if(text.equals("A")) {
                message = message.substring(0,12)+'0'+'9'+message.substring(14);
            } else if(text.equals("A#/Bb")) {
                message = message.substring(0,12)+'1'+'0'+message.substring(14);
            } else if(text.equals("B")) {
                message = message.substring(0,12)+'1'+'1'+message.substring(14);
            } else if(text.equals("C")) {
                message = message.substring(0,12)+'0'+'0'+message.substring(14);
            } else if(text.equals("C#/Db")) {
                message = message.substring(0,12)+'0'+'1'+message.substring(14);
            } else if(text.equals("D")) {
                message = message.substring(0,12)+'0'+'2'+message.substring(14);
            } else if(text.equals("D#/Eb")) {
                message = message.substring(0,12)+'0'+'3'+message.substring(14);
            } else if(text.equals("E")) {
                message = message.substring(0,12)+'0'+'4'+message.substring(14);
            } else if(text.equals("F")) {
                message = message.substring(0,12)+'0'+'5'+message.substring(14);
            } else if(text.equals("F#/Gb")) {
                message = message.substring(0,12)+'0'+'6'+message.substring(14);
            } else if(text.equals("G")) {
                message = message.substring(0,12)+'0'+'7'+message.substring(14);
            } else if(text.equals("G#/Ab")) {
                message = message.substring(0,12)+'0'+'8'+message.substring(14);
            } else {

            }
        } else if(list1.equals("Minor")) {
            if(text.equals("A")) {
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
                message = message.substring(0,12)+'0'+'0'+message.substring(14);
            } else {

            }
        }

        buttonPattern.setText(message);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openActivity2() {
        //String bluetoothWord = message;

        MainActivity.remoteControl.sendWord(message);
        //Intent intent = new Intent(this, SettingsScreens.class);
        //intent.putExtra(EXTRA_TEXT, bluetoothWord);
        //startActivity(intent);
    }

    public void openDisplayActivity() {
        Intent intent = new Intent(this, Display.class);
        startActivity(intent);
    }

    public void sendStop() {
        String stopMessage = "E";
        remoteControl.sendWord(stopMessage);
        //this will also be the back button
    }

}