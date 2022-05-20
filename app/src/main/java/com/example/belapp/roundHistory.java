package com.example.belapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class roundHistory extends AppCompatActivity {
    private static final String NORMAL="normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_history);
        boolean normal=getIntent().getBooleanExtra(NORMAL,false);
        TextView name1=findViewById(R.id.name11);
        TextView name2=findViewById(R.id.name22);
        GlobalGame globalGame=(GlobalGame)getApplication();
        if(normal){
            name1.setText(globalGame.team1);
            name2.setText(globalGame.team2);
        }
        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        int partijaIndex=getIntent().getIntExtra("partijaID",-1);
        Partija currentPartija=currentGame.getPartijas().get(partijaIndex);
        for(Round round:currentPartija.getRounds()){
            if(normal){
                LinearLayout roundLayout=new LinearLayout(getApplicationContext());
                roundLayout.setOrientation(LinearLayout.HORIZONTAL);
                roundLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView points1=new TextView(getApplicationContext());
                TextView points2=new TextView(getApplicationContext());
                TextView usao=new TextView(getApplicationContext());
                points1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                points2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                usao.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                points1.setGravity(Gravity.CENTER);
                points2.setGravity(Gravity.CENTER);
                usao.setGravity(Gravity.CENTER);
                points1.setText(String.valueOf(round.getPointsTeam1()+round.getZvanjaTeam1()));
                points2.setText(String.valueOf(round.getPointsTeam2()+round.getZvanjaTeam2()));
                usao.setText(round.getUsao());
                points1.setTextSize(50);
                points2.setTextSize(50);
                usao.setTextSize(30);
                roundLayout.addView(points1);
                roundLayout.addView(usao);
                roundLayout.addView(points2);
                ((LinearLayout)findViewById(R.id.rounds)).addView(roundLayout);
            }else{
                LinearLayout layout=new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                TextView mi=new TextView(this);
                TextView vi=new TextView(this);
                mi.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                vi.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                mi.setTextSize(50);
                vi.setTextSize(50);
                mi.setGravity(Gravity.CENTER);
                vi.setGravity(Gravity.CENTER);
                mi.setText(String.valueOf(round.getPointsTeam1()+round.getZvanjaTeam1()));
                vi.setText(String.valueOf(round.getPointsTeam2()+round.getZvanjaTeam2()));
                layout.addView(mi);
                layout.addView(vi);
                ((LinearLayout)findViewById(R.id.rounds)).addView(layout);
            }

        }
    }
}