package com.example.belapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        Switch sw1=findViewById(R.id.mala);
        Switch sw2=findViewById(R.id.mulj);
        sw1.setChecked(globalGame.mala);
        sw2.setChecked(globalGame.mulj);
    }

    public void changeSetting(View v){
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        Switch sw=(Switch) v;
        if(v==findViewById(R.id.mala)){
            globalGame.mala=sw.isChecked();
            if(!globalGame.mala){
                globalGame.mulj=false;
                ((Switch)findViewById(R.id.mulj)).setChecked(false);
            }
        }
        else{
            globalGame.mulj=sw.isChecked();
            if(globalGame.mulj){
                globalGame.mala=true;
                ((Switch)findViewById(R.id.mala)).setChecked(true);
            }
        }
    }
}