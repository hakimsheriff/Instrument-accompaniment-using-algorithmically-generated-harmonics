package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "DemoInitialApp";
    boolean octaveUpDown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button btn = (Button) findViewById(R.id.btnDoMagic);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                btn.setEnabled(false);
                Toast.makeText(getApplicationContext(), "It's magic!", Toast.LENGTH_SHORT)
                        .show();
            }
        });


        Button buttonOctave = (Button) findViewById(R.id.button_octave_down);
        buttonOctave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "This is a magic log message!");
                octaveUpDown = !octaveUpDown;

                if(octaveUpDown) {
                    buttonOctave.setText("Octave Down");
                } else {
                    buttonOctave.setText("Octave Up");
                }
            }
        });

    }

    int genreSelected = 0;
    RadioButton genreButton;
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_key_auto:
                if (genreSelected == 1) {
                    genreButton = (RadioButton) findViewById(R.id.radio_key_auto);
                    genreButton.setChecked(false);
                    genreSelected = 0;
                    break;
                } else {
                    genreSelected = 1;
                    break;
                }
            case R.id.radio_key_manual:
                if (genreSelected == 2) {
                    genreButton = (RadioButton) findViewById(R.id.radio_key_manual);
                    genreButton.setChecked(false);
                    genreSelected = 0;
                    break;
                } else {
                    genreSelected = 2;
                    break;
                }
            case R.id.radio_key_arpegiator:
                if(genreSelected == 3) {
                    genreButton = (RadioButton) findViewById(R.id.radio_key_arpegiator);
                    genreButton.setChecked(false);
                    genreSelected = 0;
                    break;
                } else {
                    genreSelected = 3;
                    break;
                }
        }

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_1:
                if (checked) {
                    genreSelected = 0;
                }
                // Remove the meat
                break;
            case R.id.checkbox_2:
                if (checked) {

                }
                break;
            // TODO: Veggie sandwich
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}