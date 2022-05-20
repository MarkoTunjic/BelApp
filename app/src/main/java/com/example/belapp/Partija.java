package com.example.belapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Partija {
    private int partijaId;
    private List<Round> rounds;
    private int sumPointsTeam1;
    private int sumPointsTeam2;
    private int finalScoreTeam1;
    private int finalScoreTeam2;
    private int sumZvanjaTeam1;
    private int sumZvanjaTeam2;

    public Partija(int partijaId){
        this.partijaId=partijaId;
        this.rounds=new ArrayList<>();
        this.sumPointsTeam1=0;
        this.sumPointsTeam2=0;
        this.sumZvanjaTeam1=0;
        this.sumZvanjaTeam2=0;
        this.finalScoreTeam1=0;
        this.finalScoreTeam2=0;
    }

    public int getPartijaId() {
        return partijaId;
    }

    public int getSumPointsTeam1() {
        return sumPointsTeam1;
    }

    public int getSumPointsTeam2() {
        return sumPointsTeam2;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public int getFinalScoreTeam1() {
        return finalScoreTeam1;
    }

    public int getFinalScoreTeam2() {
        return finalScoreTeam2;
    }

    public int getSumZvanjaTeam1() {
        return sumZvanjaTeam1;
    }

    public int getSumZvanjaTeam2() {
        return sumZvanjaTeam2;
    }

    public void sortRounds(){
        this.rounds.sort((r1,r2)->r1.getRoundId()-r2.getRoundId());
    }

    public void addRound(Round round){
        this.sumZvanjaTeam2+=round.getZvanjaTeam2();
        this.sumZvanjaTeam1+=round.getZvanjaTeam1();
        this.sumPointsTeam1+=round.getPointsTeam1();
        this.sumPointsTeam2+=round.getPointsTeam2();

        finalScoreTeam1=sumPointsTeam1+sumZvanjaTeam1;
        finalScoreTeam2=sumPointsTeam2+sumZvanjaTeam2;
        this.rounds.add(round);
    }

    public void updateVariables(){

        this.sumZvanjaTeam1=0;
        this.sumZvanjaTeam2=0;
        this.finalScoreTeam1=0;
        this.finalScoreTeam2=0;
        this.sumPointsTeam1=0;
        this.sumPointsTeam2=0;

        for(Round round:rounds){
            this.sumZvanjaTeam1+=round.getZvanjaTeam1();
            this.sumZvanjaTeam2+= round.getZvanjaTeam2();
            this.sumPointsTeam1+=round.getPointsTeam1();
            this.sumPointsTeam2+=round.getPointsTeam2();
            this.finalScoreTeam1+=round.getPointsTeam1()+round.getZvanjaTeam1();
            this.finalScoreTeam2+= round.getPointsTeam2()+ round.getZvanjaTeam2();
        }

    }
}
