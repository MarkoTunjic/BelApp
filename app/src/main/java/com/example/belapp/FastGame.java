package com.example.belapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class FastGame extends AppCompatActivity {
    private static final String SCORE="score";
    private static final String ROUNDS="rounds";
    private static final String SAVED_ID="savedId";
    private static final String CONTINUE="continue";
    private static final String SETTINGS="settings";
    boolean cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_game);
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        cont=getIntent().getBooleanExtra(CONTINUE,false);
        globalGame.team1="MI";
        globalGame.team2="VI";
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button unesi=findViewById(R.id.newInputFast);
        unesi.setEnabled(true);
        updateRounds();
        updatePoints();
        saveGame();
    }

    public void updateRounds(){
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        Partija currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
        currentPartija.updateVariables();

        if(cont&&(currentPartija.getFinalScoreTeam1()>1000||currentPartija.getFinalScoreTeam2()>1000)){
            currentGame.addPartija(new Partija(currentGame.getPartijas().size()));
            currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
            cont=false;
        }else{
            cont=false;
        }
        Log.d("score1", String.valueOf(currentPartija.getFinalScoreTeam1()+" "+currentPartija.getFinalScoreTeam2()));
        if(currentPartija.getFinalScoreTeam1()>1000 && currentPartija.getFinalScoreTeam2()<currentPartija.getFinalScoreTeam1()){
            Log.d("TAG", "updateRounds: ");
            if(currentPartija.getFinalScoreTeam2()<=250&&globalGame.mulj){
                currentGame.addScoreTeam1(3);
            }else if(currentPartija.getFinalScoreTeam2()<=500&&globalGame.mala){
                currentGame.addScoreTeam1(2);
            }else
                currentGame.addScoreTeam1(1);
            Log.d("score", String.valueOf(currentGame.getScoreTeam1()+" "+currentGame.getScoreTeam2()));
            currentGame.addPartija(new Partija(currentGame.getPartijas().size()));
            currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
        }else if(currentPartija.getFinalScoreTeam2()>1000 && currentPartija.getFinalScoreTeam1()<currentPartija.getFinalScoreTeam2()){
            Log.d("TAG", "updateRounds: ");
            if(currentPartija.getFinalScoreTeam1()<=250&&globalGame.mulj){
                currentGame.addScoreTeam2(3);
            }else if(currentPartija.getFinalScoreTeam1()<=500&&globalGame.mala){
                currentGame.addScoreTeam2(2);
            }else
                currentGame.addScoreTeam2(1);
            Log.d("score", String.valueOf(currentGame.getScoreTeam1()+" "+currentGame.getScoreTeam2()));
            currentGame.addPartija(new Partija(currentGame.getPartijas().size()));
            currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
        }

        LinearLayout results=findViewById(R.id.results);
        results.removeAllViews();

        for(Round round:currentPartija.getRounds()){
            LinearLayout roundLayout=new LinearLayout(getApplicationContext());
            roundLayout.setOrientation(LinearLayout.HORIZONTAL);
            roundLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView pointsMi=new TextView(getApplicationContext());
            TextView pointsVi=new TextView(getApplicationContext());
            pointsMi.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            pointsVi.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            pointsMi.setText(String.valueOf(round.getPointsTeam1()+ round.getZvanjaTeam1()));
            pointsVi.setText(String.valueOf(round.getPointsTeam2()+ round.getZvanjaTeam2()));
            pointsMi.setGravity(Gravity.CENTER);
            pointsVi.setGravity(Gravity.CENTER);
            pointsMi.setTextSize(50);
            pointsVi.setTextSize(50);

            roundLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent=findViewById(R.id.results);

                    for(int i=0;i<parent.getChildCount();i++){
                        if(parent.getChildAt(i)==v){
                            Game currentGame=((GlobalGame)FastGame.this.getApplication()).getGame();
                            Partija currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
                            Round clickedRound=currentPartija.getRounds().get(i);

                            //create new Intent
                            Intent pointsIntent=new Intent(FastGame.this,Points.class);
                            pointsIntent.putExtra("pointsMi",clickedRound.getPointsTeam1());
                            pointsIntent.putExtra("pointsVi",clickedRound.getPointsTeam2());
                            pointsIntent.putExtra("zvanjaMi",clickedRound.getZvanjaTeam1());
                            pointsIntent.putExtra("zvanjaVi",clickedRound.getZvanjaTeam2());
                            pointsIntent.putExtra("edit",true);
                            pointsIntent.putExtra("index",i);
                            //start new activity
                            startActivity(pointsIntent);
                        }
                    }
                }
            });
            roundLayout.addView(pointsMi);
            roundLayout.addView(pointsVi);
            results.addView(roundLayout);
        }
    }

    public void updatePoints(){
        TextView scoreMi=findViewById(R.id.scoreTeamMI);
        TextView scoreVi=findViewById(R.id.scoreTeamVI);
        TextView pointsVi=findViewById(R.id.pointsTeamVI);
        TextView pointsMi=findViewById(R.id.pointsTeamMI);
        TextView zvanjaMi=findViewById(R.id.callsTeamMI);
        TextView zvanjaVi=findViewById(R.id.callsTeamVI);
        TextView differencePoints=findViewById(R.id.differencePoints);
        TextView differenceCalls=findViewById(R.id.differnceCalls);

        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        Partija currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);

        currentPartija.updateVariables();
        Log.d("a"  , String.valueOf(currentGame.getScoreTeam1()));
        scoreMi.setText(String.valueOf(currentGame.getScoreTeam1()));
        scoreVi.setText(String.valueOf(currentGame.getScoreTeam2()));
        pointsMi.setText(String.valueOf(currentPartija.getFinalScoreTeam1()));
        pointsVi.setText(String.valueOf(currentPartija.getFinalScoreTeam2()));
        zvanjaMi.setText(String.valueOf(currentPartija.getSumZvanjaTeam1()));
        zvanjaVi.setText(String.valueOf(currentPartija.getSumZvanjaTeam2()));
        differencePoints.setText(String.valueOf(Math.abs(currentPartija.getFinalScoreTeam1()-currentPartija.getFinalScoreTeam2())));
        differenceCalls.setText(String.valueOf(Math.abs(currentPartija.getSumZvanjaTeam1()-currentPartija.getSumZvanjaTeam2())));
    }

    public void saveGame(){
        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        String score=String.valueOf(currentGame.getScoreTeam1())+","+String.valueOf(currentGame.getScoreTeam2()+",0");
        Set<String> runde=new LinkedHashSet<>();
        for(Partija partija:currentGame.getPartijas()){

            for(Round round:partija.getRounds()){
                StringBuilder runda=new StringBuilder();
                runda.append(partija.getPartijaId()).append(",");
                runda.append(round.getPointsTeam1()).append(",");
                runda.append(round.getPointsTeam2()).append(",");
                runda.append(round.getZvanjaTeam1()).append(",");
                runda.append(round.getZvanjaTeam2()).append(",");
                runda.append(round.getRoundId());
                runde.add(runda.toString());

            }
        }
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        GlobalGame globalGame=(GlobalGame)this.getApplication();
        SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        StringBuilder settingsBuilder=new StringBuilder();
        settingsBuilder.append(globalGame.mala).append(",").append(globalGame.mulj).append(",").append(formattedDate);
        editor.putString(SETTINGS,settingsBuilder.toString());
        editor.putString(SCORE,score);
        editor.putStringSet(ROUNDS,runde);
        editor.putInt(SAVED_ID,currentGame.getGameId());
        editor.apply();
    }


    //onClick function to open a new activity
    public void points(View v){
        ((Button)v).setEnabled(false);
        //create new Intent
        Intent pointsIntent=new Intent(this,Points.class);

        //start new activity
        startActivity(pointsIntent);
    }

    public void historyOfPartijas(View v){
        //create new Intent
        Intent historyIntent=new Intent(this,HistoryOfPartijas.class);

        //start new activity
        startActivity(historyIntent);
    }
}