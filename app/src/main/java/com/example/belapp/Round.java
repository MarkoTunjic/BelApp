package com.example.belapp;

public class Round {
    private int roundId;
    private int pointsTeam1;
    private int pointsTeam2;
    private int zvanjaTeam1;
    private int zvanjaTeam2;
    private String usao;
    private boolean pad;
    private int usaoIndex;
    public Round(int roundId,int pointsTeam1,int pointsTeam2,int zvanjaTeam1,int zvanjaTeam2){
        this.roundId=roundId;
        this.pointsTeam1=pointsTeam1;
        this.pointsTeam2=pointsTeam2;
        this.zvanjaTeam1=zvanjaTeam1;
        this.zvanjaTeam2=zvanjaTeam2;
    }

    public int getUsaoIndex() {
        return usaoIndex;
    }

    public void setUsaoIndex(int usaoIndex) {
        this.usaoIndex = usaoIndex;
    }

    public boolean isPad() {
        return pad;
    }

    public void setPad(boolean pad) {
        this.pad = pad;
    }

    public String getUsao() {
        return usao;
    }

    public void setUsao(String usao) {
        this.usao = usao;
    }

    public int getRoundId(){
        return roundId;
    }

    public int getPointsTeam1(){
        return pointsTeam1;
    }

    public int getPointsTeam2() {
        return pointsTeam2;
    }

    public int getZvanjaTeam1() {
        return zvanjaTeam1;
    }

    public int getZvanjaTeam2() {
        return zvanjaTeam2;
    }

    public void setPointsTeam1(int pointsTeam1) {
        this.pointsTeam1 = pointsTeam1;
    }

    public void setPointsTeam2(int pointsTeam2) {
        this.pointsTeam2 = pointsTeam2;
    }

    public void setZvanjaTeam1(int zvanjaTeam1) {
        this.zvanjaTeam1 = zvanjaTeam1;
    }

    public void setZvanjaTeam2(int zvanjaTeam2) {
        this.zvanjaTeam2 = zvanjaTeam2;
    }

}
