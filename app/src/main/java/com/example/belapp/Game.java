package com.example.belapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {
    private int gameId;
    private List<Partija> partijas;
    private int scoreTeam1;
    private int scoreTeam2;
    private int type;
    public Game(int gameId,int type){
        this.type=type;
        this.gameId=gameId;
        this.partijas=new ArrayList<>();
        this.scoreTeam1=0;
        this.scoreTeam2=0;
    }

    public int getGameId() {
        return gameId;
    }

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public List<Partija> getPartijas() {
        return partijas;
    }

    public void addPartija(Partija partija){
        this.partijas.add(partija);
    }

    public void addScoreTeam1(int a) {
        this.scoreTeam1 += a;
    }

    public void addScoreTeam2(int a) {
        this.scoreTeam2 += a;
    }

    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public void sortPartijas(){
        this.partijas.sort(new Comparator<Partija>() {
            @Override
            public int compare(Partija o1, Partija o2) {
                return o1.getPartijaId()-o2.getPartijaId();
            }
        });
    }
}
