package com.example.belapp;

import android.app.Application;

public class GlobalGame extends Application {
    private Game game;
    private static final String SHARED_PREFS="sharedPrefs";
    public String team1;
    public String team2;
    public String player1;
    public String player2;
    public String player3;
    public String player4;
    public boolean mala=true;
    public boolean mulj=true;
    public int shuffler=-1;
    public boolean setup=false;
    public boolean cont=false;
    public boolean edit=false;
    public void setupGame(int gameId,int type){
        this.game=new Game(gameId,type);
    }

    public Game getGame() {
        return game;
    }

    public String getSharedPrefs(){
        return SHARED_PREFS;
    }
}
