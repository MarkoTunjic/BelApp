package com.example.belapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DBName="BelApp";



    public DB(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Game(gameId INTEGER PRIMARY KEY,mala SMALLINT NOT NULL,mulj SMALLINT NOT NULL,team1 TEXT,team2 TEXT,player1 TEXT,player2 TEXT,player3 TEXT,player4 TEXT,score1 INTEGER NOT NULL,score2 INTEGER NOT NULL,type SMALLINT NOT NULL,date TEXT NOT NULL,shuffler INTEGER)");
        db.execSQL("CREATE TABLE Round(roundId INTEGER,partijaId INTEGER,gameId INTEGER,points1 INTEGER NOT NULL,points2 INTEGER NOT NULL,zvanja1 INTEGER NOT NULL,zvanja2 INTEGER NOT NULL,usao TEXT,usaoIndex INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Game");
        db.execSQL("DROP TABLE IF EXISTS Round");
    }

    public boolean insertGame(int gameId,boolean mala,boolean mulj,String team1,String team2,String player1,String player2,String player3,String player4,int score1,int score2,int type,String date,int shuffler){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("gameId",gameId);
        contentValues.put("mala",mala?1:0);
        contentValues.put("mulj",mulj?1:0);
        contentValues.put("team1",team1);
        contentValues.put("team2",team2);
        contentValues.put("player1",player1);
        contentValues.put("player2",player2);
        contentValues.put("player3",player3);
        contentValues.put("player4",player4);
        contentValues.put("score1",score1);
        contentValues.put("score2",score2);
        contentValues.put("type",type);
        contentValues.put("date",date);
        contentValues.put("shuffler",shuffler);
        long result=db.insert("Game",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean insertRound(int roundId,int partijaId,int gameId,int points1,int points2,int zvanja1,int zvanja2,String usao,int usaoIndex){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("roundId",roundId);
        contentValues.put("partijaId",partijaId);
        contentValues.put("gameId",gameId);
        contentValues.put("points1",points1);
        contentValues.put("points2",points2);
        contentValues.put("zvanja1",zvanja1);
        contentValues.put("zvanja2",zvanja2);
        contentValues.put("usao",usao);
        contentValues.put("usaoIndex",usaoIndex);
        long result=db.insert("Round",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean deleteGame(int gameId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Game where gameId=?",new String[]{String.valueOf(gameId)});
        if(cursor.getCount()>0) {
            long result=db.delete("Game","gameId=?",new String[]{String.valueOf(gameId)});
            if(result==-1)
                return false;
            else
                return true;
        }
        return false;
    }

    public boolean deleteRounds(int gameId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Round where gameId=?",new String[]{String.valueOf(gameId)});
        if(cursor.getCount()>0) {
            long result=db.delete("Round","gameId=?",new String[]{String.valueOf(gameId)});
            if(result==-1)
                return false;
            else
                return true;
        }
        return false;
    }
    public Cursor getAllGames(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Game",new String[]{});
        return cursor;
    }
    public Cursor getGame(int gameId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Game where gameId=?",new String[]{String.valueOf(gameId)});
        return cursor;
    }

    public Cursor getRounds(int gameId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Round where gameId=? ORDER BY partijaId,roundId",new String[]{String.valueOf(gameId)});
        return cursor;
    }

    public void clearData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM Round");
        db.execSQL("DELETE FROM Game");
    }
}
