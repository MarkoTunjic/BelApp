package com.example.belapp;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class NormalGame extends AppCompatActivity{
    private TextView team1;
    private TextView team2;
    private TextView scoreTeam1;
    private TextView scoreTeam2;
    private TextView pointsTeam1;
    private TextView pointsTeam2;
    private TextView zvanjaTeam1;
    private TextView zvanjaTeam2;
    private TextView differencePoints;
    private TextView differnceZvanja;
    private LinearLayout results;
    private static final String ISEDIT="isEdit";
    private static final String EDITINDEX="editIndex";
    private static final String POINTSTEAM1="pointsTeam1";
    private static final String POINTSTEAM2="pointsTeam2";
    private static final String ZVANJATEAM1="zvanjaTeam1";
    private static final String ZVANJATEAM2="zvanjaTeam2";
    private static final String USAOINDEX="usaoIndex";
    private static final String CONTINUE="continue";
    private static final String SCORE="score";
    private static final String ROUNDS="rounds";
    private static final String SAVED_ID="savedId";
    private static final String NAMES="names";
    private static final String SETTINGS="settings";
    private static final String NORMAL="normal";
    private boolean cont;
    private boolean entered=false;
    private boolean setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        setup=globalGame.setup;
        cont=getIntent().getBooleanExtra(CONTINUE,false);
        setContentView(R.layout.activity_normal_game);
        team1=findViewById(R.id.team1);
        team2=findViewById(R.id.team2);
        scoreTeam1=findViewById(R.id.scoreTeam1);
        scoreTeam2=findViewById(R.id.scoreTeam2);
        pointsTeam1=findViewById(R.id.pointsTeam1);
        pointsTeam2=findViewById(R.id.pointsTeam2);
        zvanjaTeam1=findViewById(R.id.callsTeam1);
        zvanjaTeam2=findViewById(R.id.callsTeam2);
        differencePoints=findViewById(R.id.differencePointsNormal);
        differnceZvanja=findViewById(R.id.differnceCallsNormal);
        results=findViewById(R.id.results);

        cont=getIntent().getBooleanExtra(CONTINUE,false);
        if(!cont){
            globalGame.setup=false;
            openDialog();
        }

        else{
            globalGame.setup=true;
            setup=true;
            team1.setText(globalGame.team1);
            team2.setText(globalGame.team2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button unesi=findViewById(R.id.newInputNormal);
        unesi.setEnabled(true);
        drawGame();
        GlobalGame globalGame=(GlobalGame)this.getApplication();

        setup=globalGame.setup;
        if(setup) {
            saveGame();
            if(!globalGame.edit && globalGame.cont)
                shuffleInfo();
            globalGame.cont=false;
            globalGame.edit=false;
        }
    }

    public void openShuffleDialog(){
        entered=false;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Tko miješa?");
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(!entered){
                    Random r=new Random();
                    globalGame.shuffler=r.nextInt(3);
                    saveGame();
                    shuffleInfo();

                }

            }
        });
        CharSequence players[]={globalGame.player1,globalGame.player2,globalGame.player3,globalGame.player4,"random"};
        builder.setItems(players, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==4){
                    Random r=new Random();
                    globalGame.shuffler=r.nextInt(3);
                }else{
                    globalGame.shuffler=which;
                }
                saveGame();
                shuffleInfo();
                entered=true;

            }
        });
        setup=true;
        builder.show();
    }

    public void shuffleInfo(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        String pShuffler="";
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        Game currentGame=globalGame.getGame();
        Partija currentPartija= currentGame.getPartijas().get(currentGame.getPartijas().size()-1);

        switch (globalGame.shuffler){
            case 0:
                pShuffler=globalGame.player1;
                break;
            case 1:
                pShuffler=globalGame.player2;
                break;
            case 2:
                pShuffler=globalGame.player3;
                break;
            case 3:
                pShuffler=globalGame.player4;
                break;
        }
        if(currentPartija.getRounds().size()==0&&currentGame.getPartijas().size()>1){
            if(currentGame.getPartijas().get(currentGame.getPartijas().size()-2).getFinalScoreTeam1()>currentGame.getPartijas().get(currentGame.getPartijas().size()-2).getFinalScoreTeam2()){
                if(globalGame.shuffler!=0&&globalGame.shuffler!=1){
                    switch(globalGame.shuffler){
                        case 2:
                            globalGame.shuffler=1;
                            break;
                        case 3:
                            globalGame.shuffler=0;
                            break;
                    }
                    switch (globalGame.shuffler){
                        case 0:
                            pShuffler=globalGame.player1;
                            break;
                        case 1:
                            pShuffler=globalGame.player2;
                            break;
                        case 2:
                            pShuffler=globalGame.player3;
                            break;
                        case 3:
                            pShuffler=globalGame.player4;
                            break;
                    }
                }
            }else{
                if(globalGame.shuffler!=2&&globalGame.shuffler!=3){
                    switch(globalGame.shuffler){
                        case 0:
                            globalGame.shuffler=2;
                            break;
                        case 1:
                            globalGame.shuffler=3;
                            break;
                    }
                    switch (globalGame.shuffler){
                        case 0:
                            pShuffler=globalGame.player1;
                            break;
                        case 1:
                            pShuffler=globalGame.player2;
                            break;
                        case 2:
                            pShuffler=globalGame.player3;
                            break;
                        case 3:
                            pShuffler=globalGame.player4;
                            break;
                    }
                }
            }
        }
        builder.setTitle("Sada miješa: "+pShuffler);
        builder.setPositiveButton("U redu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        switch(globalGame.shuffler){
            case 0:
                globalGame.shuffler=2;
                break;
            case 1:
                globalGame.shuffler=3;
                break;
            case 2:
                globalGame.shuffler=1;
                break;
            case 3:
                globalGame.shuffler=0;
                break;
        }
        builder.show();
    }

    public void drawGame(){
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        Log.d("currentgame", currentGame.toString());
        Partija currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
        results.removeAllViews();

        if(cont&&(currentPartija.getFinalScoreTeam1()>1000||currentPartija.getFinalScoreTeam2()>1000)){
            currentGame.addPartija(new Partija(currentGame.getPartijas().size()));
            currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
            cont=false;
        }else{
            cont=false;
        }
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
        scoreTeam1.setText(String.valueOf(currentGame.getScoreTeam1()));
        scoreTeam2.setText(String.valueOf(currentGame.getScoreTeam2()));
        pointsTeam1.setText(String.valueOf(currentPartija.getFinalScoreTeam1()));
        pointsTeam2.setText(String.valueOf(currentPartija.getFinalScoreTeam2()));
        zvanjaTeam1.setText(String.valueOf(currentPartija.getSumZvanjaTeam1()));
        zvanjaTeam2.setText(String.valueOf(currentPartija.getSumZvanjaTeam2()));
        differencePoints.setText(String.valueOf(Math.abs(currentPartija.getFinalScoreTeam2()-currentPartija.getFinalScoreTeam1())));
        differnceZvanja.setText(String.valueOf(Math.abs(currentPartija.getSumZvanjaTeam2()-currentPartija.getSumZvanjaTeam1())));

        for(Round round: currentPartija.getRounds()){
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

            roundLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<results.getChildCount();i++){
                        if(results.getChildAt(i)==v){
                            Game cgame=((GlobalGame)getApplication()).getGame();
                            Round currentRound=cgame.getPartijas().get(cgame.getPartijas().size()-1).getRounds().get(i);
                            Intent intent=new Intent(getApplicationContext(),PointsNormal.class);
                            intent.putExtra(ISEDIT,true);
                            intent.putExtra(EDITINDEX,i);
                            intent.putExtra(POINTSTEAM1,currentRound.getPointsTeam1());
                            intent.putExtra(POINTSTEAM2,currentRound.getPointsTeam2());
                            intent.putExtra(ZVANJATEAM1,currentRound.getZvanjaTeam1());
                            intent.putExtra(ZVANJATEAM2,currentRound.getZvanjaTeam2());
                            intent.putExtra(USAOINDEX,currentRound.getUsaoIndex());
                            startActivity(intent);
                        }
                    }
                }
            });
            roundLayout.addView(points1);
            roundLayout.addView(usao);
            roundLayout.addView(points2);
            results.addView(roundLayout);
        }
    }

    public void openDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.dialog_layout,null);

        builder.setView(v).setTitle("unesite informacije o timovima:").setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        EditText tim1=v.findViewById(R.id.team1name);
        EditText tim2=v.findViewById(R.id.team2name);
        EditText player1=v.findViewById(R.id.player1);
        EditText player2=v.findViewById(R.id.player2);
        EditText player3=v.findViewById(R.id.player3);
        EditText player4=v.findViewById(R.id.player4);
        AlertDialog alertDialog=builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(!entered){
                    finish();
                }

            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t1=tim1.getText().toString();
                String t2=tim2.getText().toString();
                String pl1=player1.getText().toString();
                String pl2=player2.getText().toString();
                String pl3=player3.getText().toString();
                String pl4=player4.getText().toString();
                boolean isError=false;
                if(t1.isEmpty()){
                    tim1.setError("Ime tima ne smije prazno!");
                    isError=true;
                }
                
                if(t2.isEmpty()){
                    tim2.setError("Ime tima ne smije prazno!");
                    isError=true;
                }
                if(t1.equals(t2)){
                    tim2.setError("Imena timova ne smiju biti ista");
                    isError=true;
                }
                if(pl1.isEmpty()){
                    player1.setError("Ime igrača ne smije biti prazno!");
                    isError=true;
                }
                if(pl2.isEmpty()){
                    player2.setError("Ime igrača ne smije biti prazno!");
                    isError=true;
                }
                if(pl3.isEmpty()){
                    player3.setError("Ime igrača ne smije biti prazno!");
                    isError=true;
                }
                if(pl4.isEmpty()){
                    player4.setError("Ime igrača ne smije biti prazno!");
                    isError=true;
                }
                if(team2.equals(team1)){
                    tim2.setError("Imena timova ne smiju biti jednaka");
                    isError=true;
                }
                if(pl1.equals(pl2)||pl1.equals(pl3)||pl1.equals(pl4)){
                    player1.setError("Imena igrača moraju biti različita");
                    isError=true;
                }
                if(pl2.equals(pl3)||pl2.equals(pl4)){
                    player2.setError("Imena igrača moraju biti različita");
                    isError=true;
                }
                if(pl3.equals(pl4)){
                    player3.setError("Imena igrača moraju biti različita");
                    isError=true;
                }

                if(t1.contains(",")){
                    tim1.setError("Imena ne smju sadrzavat zarez!");
                    isError=true;
                }
                if(t2.contains(",")){
                    tim2.setError("Imena ne smju sadrzavat zarez!");
                    isError=true;
                }
                if(pl1.contains(",")){
                    player1.setError("Imena ne smju sadrzavat zarez!");
                    isError=true;
                }
                if(pl2.contains(",")){
                    player2.setError("Imena ne smju sadrzavat zarez!");
                    isError=true;
                }
                if(pl3.contains(",")){
                    player3.setError("Imena ne smju sadrzavat zarez!");
                    isError=true;
                }
                if(pl4.contains(",")){
                    player4.setError("Imena ne smju sadrzavat zarez!");
                    isError=true;
                }
                if(!isError){
                    team1.setText(t1);
                    team2.setText(t2);
                    ((GlobalGame)getApplication()).team1=t1;
                    ((GlobalGame)getApplication()).team2=t2;
                    ((GlobalGame)getApplication()).player1=pl1;
                    ((GlobalGame)getApplication()).player2=pl2;
                    ((GlobalGame)getApplication()).player3=pl3;
                    ((GlobalGame)getApplication()).player4=pl4;
                    saveGame();
                    Log.d("player1", ((GlobalGame)getApplication()).player1);
                    entered=true;
                    ((GlobalGame)getApplication()).setup=true;
                    alertDialog.dismiss();
                    openShuffleDialog();
                }
            }
        });
    }

    public void input(View v){
        ((Button)v).setEnabled(false);
        Intent intent=new Intent(this,PointsNormal.class);
        intent.putExtra(ISEDIT,false);

        startActivity(intent);
    }

    public void saveGame(){
        GlobalGame globalGame=(GlobalGame)this.getApplication();
        Game currentGame=globalGame.getGame();
        SharedPreferences sharedPreferences=getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        StringBuilder score=new StringBuilder();
        score.append(currentGame.getScoreTeam1()).append(",").append(currentGame.getScoreTeam2()).append(",").append(currentGame.getType());
        Set<String> rounds=new HashSet<>();
        for(Partija partija:currentGame.getPartijas()){
            for(Round round:partija.getRounds()){
                StringBuilder roundBuilder=new StringBuilder();
                roundBuilder.append(partija.getPartijaId()).append(",").append(round.getPointsTeam1()).append(",").append(round.getPointsTeam2())
                        .append(",").append(round.getZvanjaTeam1()).append(",").append(round.getZvanjaTeam2()).append(",")
                        .append(round.getRoundId()).append(",").append(round.getUsao()).append(",").append(round.getUsaoIndex())
                        .append(",").append(round.isPad());
                rounds.add(roundBuilder.toString());
            }
        }
        StringBuilder nameBuilder=new StringBuilder();
        nameBuilder.append(globalGame.team1).append(",").append(globalGame.team2).append(",").append(globalGame.player1)
                .append(",").append(globalGame.player2).append(",").append(globalGame.player3).append(",").append(globalGame.player4)
                .append(",").append(globalGame.shuffler);
        StringBuilder settingsBuilder=new StringBuilder();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        settingsBuilder.append(globalGame.mala).append(",").append(globalGame.mulj).append(",").append(formattedDate);
        editor.putInt(SAVED_ID,currentGame.getGameId());
        editor.putString(NAMES,nameBuilder.toString());
        editor.putString(SETTINGS,settingsBuilder.toString());
        editor.putStringSet(ROUNDS,rounds);
        editor.putString(SCORE,score.toString());
        Log.d("names", nameBuilder.toString());
        editor.apply();
    }

    public void starePartije(View v){
        //create new Intent
        Intent historyIntent=new Intent(this,HistoryOfPartijas.class);
        historyIntent.putExtra(NORMAL,true);
        //start new activity
        startActivity(historyIntent);
    }
}