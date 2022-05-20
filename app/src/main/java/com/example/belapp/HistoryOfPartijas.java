package com.example.belapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HistoryOfPartijas extends AppCompatActivity {
    private static final String NORMAL="normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_partijas);
        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        boolean normal=getIntent().getBooleanExtra(NORMAL,false);
        TextView name1=findViewById(R.id.name1);
        TextView name2=findViewById(R.id.name22);
        GlobalGame globalGame=(GlobalGame)getApplication();
        if(normal){
            name1.setText(globalGame.team1);
            name2.setText(globalGame.team2);
        }
        for(Partija partija:currentGame.getPartijas()){
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
            mi.setText(String.valueOf(partija.getFinalScoreTeam1()));
            vi.setText(String.valueOf(partija.getFinalScoreTeam2()));
            layout.addView(mi);
            layout.addView(vi);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout scores=findViewById(R.id.partijas);

                    for(int i=0;i<scores.getChildCount();i++){
                        if(scores.getChildAt(i)==v){
                            Intent roundHistory=new Intent(HistoryOfPartijas.this,roundHistory.class);
                            roundHistory.putExtra("partijaID",i);
                            roundHistory.putExtra(NORMAL,normal);
                            startActivity(roundHistory);
                        }
                    }
                }
            });
            ((LinearLayout)findViewById(R.id.partijas)).addView(layout);
        }
    }
}