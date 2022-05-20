package com.example.belapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

public class History extends AppCompatActivity {
    private LinearLayout history;
    private DB db;
    private static final String CONTINUE="continue";
    int gameId,score1,score2,type,shuffler;
    boolean mala,mulj;
    String team1,team2,player1,player2,player3,player4,date;
    private static final String GAME_ID="clashID";
    private static final String SCORE="score";
    private static final String ROUNDS="rounds";
    private static final String SAVED_ID="savedId";
    private static final String NAMES="names";
    private static final String SETTINGS="settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        history=findViewById(R.id.history);
        db=new DB(this);
    }

    public void draw(){
        history.removeAllViews();
        Cursor games=db.getAllGames();

        while(games.moveToNext()){
            //int gameId,boolean mala,boolean mulj,String team1,String team2,String player1,String player2,String player3,String player4,int score1,int score2,int type


            gameId=games.getInt(0);
            Log.d("a",String.valueOf(gameId));
            mala=games.getInt(1)==0?false:true;
            mulj=games.getInt(2)==0?false:true;
            team1=games.getString(3);
            team2=games.getString(4);
            player1=games.getString(5);
            player2=games.getString(6);
            player3=games.getString(7);
            player4=games.getString(8);
            score1=games.getInt(9);
            score2=games.getInt(10);
            type=games.getInt(11);
            date=games.getString(12);

            LinearLayout gameLayout=new LinearLayout(getApplicationContext());
            gameLayout.setOrientation(LinearLayout.HORIZONTAL);
            gameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            gameLayout.setGravity(Gravity.CENTER);
            TextView points1=new TextView(getApplicationContext());
            //TextView points2=new TextView(getApplicationContext());
            TextView name1=new TextView(getApplicationContext());
            //TextView name2=new TextView(getApplicationContext());
            TextView datum=new TextView(getApplicationContext());
            points1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            //points2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            name1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            //name2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            datum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            points1.setGravity(Gravity.CENTER);
            //points2.setGravity(Gravity.CENTER);
            name1.setGravity(Gravity.CENTER);
            //name2.setGravity(Gravity.CENTER);
            datum.setGravity(Gravity.CENTER);
            points1.setText(String.valueOf(score1)+":"+String.valueOf(score2));
            //points2.setText(String.valueOf(score2));
            name1.setText(team1+" vs "+team2);
            //name2.setText(team2);
            datum.setText(date);
            points1.setTextSize(40);
            //points2.setTextSize(50);
            name1.setTextSize(40);
            //name2.setTextSize(50);
            datum.setTextSize(20);

            gameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences=getSharedPreferences(((GlobalGame)getApplication()).getSharedPrefs(),MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    int myId=sharedPreferences.getInt(GAME_ID,-1);
                    AlertDialog.Builder builder=new AlertDialog.Builder(History.this);
                    builder.setMessage("Želite li spremiti prethodnu igru u pvoijest?").setTitle("Spremiti?");
                    builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(int i=0;i<history.getChildCount();i++){
                                if(history.getChildAt(i)==v){
                                    nastavi(i);
                                    break;
                                }
                            }
                        }
                    });
                    builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveGame();
                            for(int i=0;i<history.getChildCount();i++){
                                if(history.getChildAt(i)==v){
                                    nastavi(i);
                                    break;
                                }
                            }
                        }
                    });
                    AlertDialog delete=builder.create();
                    Set<String> a=sharedPreferences.getStringSet(ROUNDS,null);
                    if(a!=null)
                        delete.show();
                    else{
                        for(int i=0;i<history.getChildCount();i++){
                            if(history.getChildAt(i)==v){
                                nastavi(i);
                                break;
                            }
                        }
                    }

                }
            });

            gameLayout.addView(name1);
            gameLayout.addView(points1);
            gameLayout.addView(datum);
            //gameLayout.addView(points2);
            //gameLayout.addView(name2);
            history.addView(gameLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        draw();
    }

    public void saveGame(){
        DB db=new DB(this);
        SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();


        int gameId=sharedPreferences.getInt(SAVED_ID,-1);
        int save=sharedPreferences.getInt(GAME_ID,-1);
        db.deleteGame(gameId);
        db.deleteRounds(gameId);
        Set<String> rounds=sharedPreferences.getStringSet(ROUNDS,null);
        String score[]=sharedPreferences.getString(SCORE,"").split(",");
        String names[]=sharedPreferences.getString(NAMES,"").split(",");
        String settings[]=sharedPreferences.getString(SETTINGS,"").split(",");
        if(score[2].equals("0"))
            db.insertGame(gameId,Boolean.valueOf(settings[0]),Boolean.valueOf(settings[1]),"MI","VI","","","","",Integer.parseInt(score[0]),Integer.parseInt(score[1]),Integer.parseInt(score[2]),settings[2],0);
        else
            db.insertGame(gameId,Boolean.valueOf(settings[0]),Boolean.valueOf(settings[1]),names[0],names[1],names[2],names[3],names[4],names[5],Integer.parseInt(score[0]),Integer.parseInt(score[1]),Integer.parseInt(score[2]),settings[2],Integer.valueOf(names[6]));
        for(String round:rounds) {
            String splitano[] = round.split(",");
            Log.d("round", round);
            if(score[2].equals("1"))
                db.insertRound(Integer.parseInt(splitano[5]),Integer.parseInt(splitano[0]),gameId,Integer.parseInt(splitano[1]),Integer.parseInt(splitano[2]),Integer.parseInt(splitano[3]),Integer.parseInt(splitano[4]),splitano[6],Integer.parseInt(splitano[7]));
            else
                db.insertRound(Integer.parseInt(splitano[5]),Integer.parseInt(splitano[0]),gameId,Integer.parseInt(splitano[1]),Integer.parseInt(splitano[2]),Integer.parseInt(splitano[3]),Integer.parseInt(splitano[4]),"",-1);
        }
        editor.clear();
        editor.putInt(GAME_ID,save);
        editor.apply();
    }
    public void nastavi(int index){
        GlobalGame globalGame=(GlobalGame)getApplication();
        Cursor games=db.getAllGames();
        for(int i=0;i<index+1;i++){
            games.moveToNext();
        }
        gameId=games.getInt(0);
        mala=games.getInt(1)==0?false:true;
        mulj=games.getInt(2)==0?false:true;
        team1=games.getString(3);
        team2=games.getString(4);
        player1=games.getString(5);
        player2=games.getString(6);
        player3=games.getString(7);
        player4=games.getString(8);
        score1=games.getInt(9);
        score2=games.getInt(10);
        type=games.getInt(11);
        date=games.getString(12);
        shuffler=games.getInt(13);

        globalGame.team1=team1;
        globalGame.team2=team2;
        globalGame.player1= player1;
        globalGame.player2=player2;
        globalGame.player3=player3;
        globalGame.player4=player4;
        globalGame.mala=mala;
        globalGame.mulj=mulj;
        globalGame.setup=true;
        globalGame.shuffler=shuffler;
        globalGame.setupGame(gameId,type);
        Game game=globalGame.getGame();
        game.addPartija(new Partija(game.getPartijas().size()));
        game.setScoreTeam1(score1);
        game.setScoreTeam2(score2);
        Cursor rounds=db.getRounds(gameId);
        globalGame.cont=true;
        globalGame.setup=true;
        while(rounds.moveToNext()){
            int roundId,partijaId,points1,points2,zvanja1,zvanja2,usaoIndex;
            String usao;
            roundId=rounds.getInt(0);
            partijaId=rounds.getInt(1);
            points1=rounds.getInt(3);
            points2=rounds.getInt(4);
            zvanja1=rounds.getInt(5);
            zvanja2=rounds.getInt(6);
            usao=rounds.getString(7);
            usaoIndex=rounds.getInt(8);
            Round newRound=new Round(roundId,points1,points2,zvanja1,zvanja2);
            newRound.setUsao(usao);
            newRound.setUsaoIndex(usaoIndex);
            if(partijaId+1>game.getPartijas().size()){
                game.addPartija(new Partija(partijaId));
            }
            game.sortPartijas();
            game.getPartijas().get(partijaId).addRound(newRound);
            for(Partija partija:game.getPartijas())
                partija.sortRounds();
        }
        if(type==0){
            //create new Intent
            Intent fastGameIntent=new Intent(getApplicationContext(),FastGame.class);
            fastGameIntent.putExtra(CONTINUE,true);
            //start new activity
            startActivity(fastGameIntent);
        }else{
            //create new Intent
            Intent normalGameIntent=new Intent(getApplicationContext(),NormalGame.class);
            normalGameIntent.putExtra(CONTINUE,true);
            //start new activity
            startActivity(normalGameIntent);
        }
    }

    public void deleteHistory(View v){
        DB db=new DB(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Jeste li sigurni da želite obrisati sve podatke");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.clearData();
                draw();
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}