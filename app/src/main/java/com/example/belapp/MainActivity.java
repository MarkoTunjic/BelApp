package com.example.belapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

//the activity that shows up when you start the app
public class MainActivity extends AppCompatActivity {
    private static final String GAME_ID="clashID";
    private static final String SCORE="score";
    private static final String ROUNDS="rounds";
    private static final String SAVED_ID="savedId";
    private static final String NAMES="names";
    private static final String SETTINGS="settings";
    private static final String CONTINUE="continue";

    @Override
    //savedInstanceState is used for shared preferences
    protected void onCreate(Bundle savedInstanceState) {
        //DB db=new DB(this);
        //this.deleteDatabase("BelApp");
        //db.clearData();

        //SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        //SharedPreferences.Editor editor=sharedPreferences.edit();
        //editor.clear();
        //editor.apply();

        super.onCreate(savedInstanceState);

        //R is a collection of all activities
        setContentView(R.layout.activity_main);
    }

    //onClick function to open a new activity
    public void newFastGame(){
        SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        int gameId=sharedPreferences.getInt(GAME_ID,1);
        editor.putInt(GAME_ID,gameId+1);
        editor.apply();

        ((GlobalGame)this.getApplication()).setupGame(gameId,0);
        ((GlobalGame)this.getApplication()).getGame().addPartija(new Partija(0));

        //create new Intent
        Intent fastGameIntent=new Intent(this,FastGame.class);

        //start new activity
        startActivity(fastGameIntent);
    }

    //onClick function to open a new activity
    public void newNormalGame(){
        SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        int gameId=sharedPreferences.getInt(GAME_ID,1);
        editor.putInt(GAME_ID,gameId+1);
        Log.d("id", String.valueOf(gameId));
        editor.apply();

        ((GlobalGame)this.getApplication()).setupGame(gameId,1);
        ((GlobalGame)this.getApplication()).getGame().addPartija(new Partija(0));

        //create new Intent
        Intent normalGameIntent=new Intent(this,NormalGame.class);

        //start new activity
        startActivity(normalGameIntent);
    }

    //onClick function to open a new activity
    public void continueGame(View v){
        SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        int gameId=sharedPreferences.getInt(SAVED_ID,-1);
        Set<String> rounds=sharedPreferences.getStringSet(ROUNDS,null);
        if(rounds!=null){
            String score[]=sharedPreferences.getString(SCORE,"").split(",");
            Log.d("a",sharedPreferences.getString(SCORE,""));
            ((GlobalGame)this.getApplication()).setupGame(gameId,Integer.parseInt(score[2]));
            GlobalGame globalGame=(GlobalGame)this.getApplication();
            Game currentGame=((GlobalGame)this.getApplication()).getGame();

            currentGame.setScoreTeam1(Integer.parseInt(score[0]));
            currentGame.setScoreTeam2(Integer.parseInt(score[1]));
            int maks=0;
            for(String round:rounds){
                String splitano[]=round.split(",");
                int novi=Integer.parseInt(splitano[0]);
                if(novi>maks)
                    maks=novi;
            }
            for(int i=0;i<=maks;i++)
                currentGame.addPartija(new Partija(i));

            if(currentGame.getType()==0){
                String settings[]=sharedPreferences.getString(SETTINGS,"").split(",");
                globalGame.mala=Boolean.valueOf(settings[0]);
                globalGame.mulj=Boolean.valueOf(settings[1]);
                for(String round:rounds){
                    String splitano[]=round.split(",");
                    int partijaId=Integer.parseInt(splitano[0]);
                    Partija currentPartija=currentGame.getPartijas().get(partijaId);
                    Round newRound=new Round(Integer.parseInt(splitano[5]),Integer.parseInt(splitano[1]),Integer.parseInt(splitano[2]),Integer.parseInt(splitano[3]),Integer.parseInt(splitano[4]));
                    currentPartija.addRound(newRound);
                    currentPartija.sortRounds();
                }
                //create new Intent
                Intent fastGameIntent=new Intent(this,FastGame.class);
                fastGameIntent.putExtra(CONTINUE,true);
                //start new activity
                startActivity(fastGameIntent);
            }else{
                String names[]=sharedPreferences.getString(NAMES,"").split(",");
                String settings[]=sharedPreferences.getString(SETTINGS,"").split(",");
                globalGame.team1=names[0];
                globalGame.team2=names[1];
                globalGame.player1=names[2];
                globalGame.player2=names[3];
                globalGame.player3=names[4];
                globalGame.player4=names[5];
                globalGame.shuffler=Integer.valueOf(names[6]);
                globalGame.mala=Boolean.valueOf(settings[0]);
                globalGame.mulj=Boolean.valueOf(settings[1]);
                globalGame.cont=true;
                globalGame.setup=true;
                for(String round:rounds){
                    String splitano[]=round.split(",");
                    int partijaId=Integer.parseInt(splitano[0]);
                    Partija currentPartija=currentGame.getPartijas().get(partijaId);
                    Round newRound=new Round(Integer.parseInt(splitano[5]),Integer.parseInt(splitano[1]),Integer.parseInt(splitano[2]),Integer.parseInt(splitano[3]),Integer.parseInt(splitano[4]));
                    newRound.setUsao(splitano[6]);
                    newRound.setUsaoIndex(Integer.parseInt(splitano[7]));
                    newRound.setPad(Boolean.valueOf(splitano[8]));
                    currentPartija.addRound(newRound);
                    currentPartija.sortRounds();
                }

                //create new Intent
                Intent normalGameIntent=new Intent(this,NormalGame.class);
                normalGameIntent.putExtra(CONTINUE,true);
                //start new activity
                startActivity(normalGameIntent);
            }

        }else{
            Toast.makeText(this,"Nema igre za nastavit!",Toast.LENGTH_LONG).show();
        }

    }

    //onClick function to open a new activity
    public void history(View v){

        //create new Intent
        Intent historyIntent=new Intent(this,History.class);

        //start new activity
        startActivity(historyIntent);
    }
    //onClick function to open a new activity
    public void settings(View v){

        //create new Intent
        Intent settingsIntent=new Intent(this,Settings.class);

        //start new activity
        startActivity(settingsIntent);
    }
    public void novaIgra(View v){
        SharedPreferences sharedPreferences=this.getSharedPreferences(((GlobalGame)this.getApplication()).getSharedPrefs(),MODE_PRIVATE);
        Set<String> rounds=sharedPreferences.getStringSet(ROUNDS,null);
        if(rounds!=null){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Želite li spremiti prethodnu igru u pvoijest?").setTitle("Spremiti?");
            builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder gameBuilder=new AlertDialog.Builder(MainActivity.this);
                    gameBuilder.setTitle("Odaberite igru: ");
                    CharSequence igre[]={"Brza igra","Prilagođena igra"};
                    gameBuilder.setItems(igre, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0){

                                newFastGame();
                            }else{

                                newNormalGame();
                            }
                        }
                    });
                    Dialog newGame=gameBuilder.create();
                    newGame.show();
                }
            });
            builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveGame();
                    AlertDialog.Builder gameBuilder=new AlertDialog.Builder(MainActivity.this);
                    gameBuilder.setTitle("Odaberite igru: ");
                    CharSequence igre[]={"Brza igra","Prilagođena igra"};
                    gameBuilder.setItems(igre, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0){

                                newFastGame();
                            }else{

                                newNormalGame();
                            }
                        }
                    });
                    Dialog newGame=gameBuilder.create();
                    newGame.show();
                }
            });
            Dialog delete=builder.create();
            delete.show();
        }else{
            AlertDialog.Builder gameBuilder=new AlertDialog.Builder(this);
            gameBuilder.setTitle("Odaberite igru: ");
            CharSequence igre[]={"Brza igra","Prilagođena igra"};
            gameBuilder.setItems(igre, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        newFastGame();
                    }else{
                        newNormalGame();
                    }
                }
            });
            Dialog newGame=gameBuilder.create();
            newGame.show();
        }
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
}