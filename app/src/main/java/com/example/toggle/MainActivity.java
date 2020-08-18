package com.example.toggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton mToggleButton;
    boolean switchState2 = false;

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate view's
        mToggleButton = (ToggleButton) findViewById(R.id.toggle);

        sharedPrefs = getSharedPreferences("com.example.xyz", MODE_PRIVATE);
        mToggleButton.setChecked(sharedPrefs.getBoolean("NameOfThingToSave", false));
        switchState2 = sharedPrefs.getBoolean("NameOfThingToSave", false);
        mToggleButton.setChecked(sharedPrefs.getBoolean("toggleButton", false));  //default is false

        mToggleButton.setOnClickListener(new ToggleButton.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean("toggleButton", mToggleButton.isChecked());
                editor.commit();
            }
        });

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean("NameOfThingToSave", true);
                    editor.commit();
                    startService();
                    switchState2 = true;
                    Toast.makeText(MainActivity.this, "This is on " + switchState2, Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.xyz", MODE_PRIVATE).edit();
                    editor.putBoolean("NameOfThingToSave", false);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "This is off " + switchState2, Toast.LENGTH_SHORT).show();
                    stopService();
                }
            }
        });

    }
 //Forgraound service start
    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    //Forgraound service stop

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}
