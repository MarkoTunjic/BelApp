package com.example.belapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PointsNormal extends AppCompatActivity {
    private static final String ISEDIT="isEdit";
    private static final String EDITINDEX="editIndex";
    private static final String USAOINDEX="usaoIndex";
    private static final String POINTSTEAM1="pointsTeam1";
    private static final String POINTSTEAM2="pointsTeam2";
    private static final String ZVANJATEAM1="zvanjaTeam1";
    private static final String ZVANJATEAM2="zvanjaTeam2";

    private ToggleButton pointsView1;
    private ToggleButton pointsView2;
    private ToggleButton zvanjaView1;
    private ToggleButton zvanjaView2;
    private ToggleButton player1;
    private ToggleButton player2;
    private ToggleButton player3;
    private ToggleButton player4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_normal);

        GlobalGame globalGame=(GlobalGame)getApplication();
        globalGame.setup=false;

        int pointsTeam1=getIntent().getIntExtra(POINTSTEAM1,0);
        int pointsTeam2=getIntent().getIntExtra(POINTSTEAM2,0);
        int zvanjaTeam1=getIntent().getIntExtra(ZVANJATEAM1,0);
        int zvanjaTeam2=getIntent().getIntExtra(ZVANJATEAM2,0);
        int usaoIndex=getIntent().getIntExtra(USAOINDEX,-1);

        pointsView1=findViewById(R.id.bodoviTeam1);
        pointsView2=findViewById(R.id.bodoviTeam2);
        zvanjaView1=findViewById(R.id.zvanjaTeam1);
        zvanjaView2=findViewById(R.id.zvanjaTeam2);
        player1=findViewById(R.id.p1);
        player2=findViewById(R.id.p2);
        player3=findViewById(R.id.p3);
        player4=findViewById(R.id.p4);

        switch(usaoIndex){
            case 1:
                player1.callOnClick();
                break;
            case 2:
                player2.callOnClick();
                break;
            case 3:
                player3.callOnClick();
                break;
            case 4:
                player4.callOnClick();
                break;
        }

        pointsView1.callOnClick();

        pointsView1.setTextOff(String.valueOf(pointsTeam1));
        pointsView1.setTextOn(String.valueOf(pointsTeam1));
        pointsView1.setText(String.valueOf(pointsTeam1));
        pointsView2.setTextOff(String.valueOf(pointsTeam2));
        pointsView2.setTextOn(String.valueOf(pointsTeam2));
        pointsView2.setText(String.valueOf(pointsTeam2));
        zvanjaView1.setTextOff(String.valueOf(zvanjaTeam1));
        zvanjaView1.setTextOn(String.valueOf(zvanjaTeam1));
        zvanjaView1.setText(String.valueOf(zvanjaTeam1));
        zvanjaView2.setTextOff(String.valueOf(zvanjaTeam2));
        zvanjaView2.setTextOn(String.valueOf(zvanjaTeam2));
        zvanjaView2.setText(String.valueOf(zvanjaTeam2));
        player1.setTextOff(globalGame.player1);
        player1.setTextOn(globalGame.player1);
        player1.setText(globalGame.player1);
        player2.setTextOff(globalGame.player2);
        player2.setTextOn(globalGame.player2);
        player2.setText(globalGame.player2);
        player3.setTextOff(globalGame.player3);
        player3.setTextOn(globalGame.player3);
        player3.setText(globalGame.player3);
        player4.setTextOff(globalGame.player4);
        player4.setTextOn(globalGame.player4);
        player4.setText(globalGame.player4);
    }

    public void selectPoints(View v){
        ToggleButton clickedButton=(ToggleButton) v;
        clickedButton.setBackgroundColor(0x298E0000);
        clickedButton.setChecked(true);

        if(pointsView1.isChecked()&&pointsView1!=v){
            pointsView1.setBackgroundColor(0x287E8181);
            pointsView1.setChecked(false);
        }
        if(pointsView2.isChecked()&&pointsView2!=v){
            pointsView2.setBackgroundColor(0x287E8181);
            pointsView2.setChecked(false);
        }
        if(zvanjaView1.isChecked()&&zvanjaView1!=v){
            zvanjaView1.setBackgroundColor(0x287E8181);
            zvanjaView1.setChecked(false);
        }
        if(zvanjaView2.isChecked()&&zvanjaView2!=v){
            zvanjaView2.setBackgroundColor(0x287E8181);
            zvanjaView2.setChecked(false);
        }
    }

    public void selectPlayer(View v){
        ToggleButton clicked=(ToggleButton)v;
        clicked.setBackgroundColor(0x298E0000);
        clicked.setChecked(true);

        if(player1.isChecked()&&player1!=v){
            player1.setBackgroundColor(0x287E8181);
            player1.setChecked(false);
        }
        if(player2.isChecked()&&player2!=v){
            player2.setBackgroundColor(0x287E8181);
            player2.setChecked(false);
        }
        if(player3.isChecked()&&player3!=v){
            player3.setBackgroundColor(0x287E8181);
            player3.setChecked(false);
        }
        if(player4.isChecked()&&player4!=v){
            player4.setBackgroundColor(0x287E8181);
            player4.setChecked(false);
        }
    }

    public void input(View v){
        Button clickedButton=(Button)v;
        String team1Points=pointsView1.getText().toString();
        String team2Points=pointsView2.getText().toString();
        String team1Zvanja=zvanjaView1.getText().toString();
        String team2Zvanja=zvanjaView2.getText().toString();

        if(pointsView1.isChecked()){
            if(team1Points.equals("0")){
                team1Points=clickedButton.getText().toString();
            }else{
                team1Points=team1Points+clickedButton.getText();
            }

            if(Integer.parseInt(team1Points)<162){
                team2Points=String.valueOf(162-Integer.parseInt(team1Points));
                pointsView1.setTextOff(team1Points);
                pointsView1.setTextOn(team1Points);
                pointsView1.setText(team1Points);
                pointsView2.setTextOff(team2Points);
                pointsView2.setTextOn(team2Points);
                pointsView2.setText(team2Points);
            }

        }else if(pointsView2.isChecked()){
            if(team2Points.equals("0")){
                team2Points=clickedButton.getText().toString();
            }else{
                team2Points=team2Points+clickedButton.getText();
            }
            if(Integer.parseInt(team2Points)<162){
                team1Points=String.valueOf(162-Integer.parseInt(team2Points));
                pointsView1.setTextOff(team1Points);
                pointsView1.setTextOn(team1Points);
                pointsView1.setText(team1Points);
                pointsView2.setTextOff(team2Points);
                pointsView2.setTextOn(team2Points);
                pointsView2.setText(team2Points);
            }

        }else if(zvanjaView1.isChecked()){
            if(team1Zvanja.equals("0")){
                team1Zvanja=clickedButton.getText().toString();
            }else{
                team1Zvanja=team1Zvanja+clickedButton.getText();
            }
            if(Integer.parseInt(team1Zvanja)<1000){
                zvanjaView1.setTextOff(team1Zvanja);
                zvanjaView1.setTextOn(team1Zvanja);
                zvanjaView1.setText(team1Zvanja);
            }
        }else if(zvanjaView2.isChecked()){
            if(team2Zvanja.equals("0")){
                team2Zvanja=clickedButton.getText().toString();
            }else{
                team2Zvanja=team2Zvanja+clickedButton.getText();
            }
            if(Integer.parseInt(team2Zvanja)<1000) {
                zvanjaView2.setTextOff(team2Zvanja);
                zvanjaView2.setTextOn(team2Zvanja);
                zvanjaView2.setText(team2Zvanja);
            }
        }
    }
    public void inputZvanja(View v){
        Button clickedButton=(Button)v;
        int team1Zvanja=Integer.parseInt(zvanjaView1.getText().toString());
        int team2Zvanja=Integer.parseInt(zvanjaView2.getText().toString());
        if(zvanjaView1.isChecked()){
            team1Zvanja+=Integer.parseInt(clickedButton.getText().toString());
            if(team1Zvanja<1000){
                zvanjaView1.setTextOff(String.valueOf(team1Zvanja));
                zvanjaView1.setTextOn(String.valueOf(team1Zvanja));
                zvanjaView1.setText(String.valueOf(team1Zvanja));
            }
        }else if(zvanjaView2.isChecked()){
            team2Zvanja+=Integer.parseInt(clickedButton.getText().toString());
            if(team2Zvanja<1000){
                zvanjaView2.setTextOff(String.valueOf(team2Zvanja));
                zvanjaView2.setTextOn(String.valueOf(team2Zvanja));
                zvanjaView2.setText(String.valueOf(team2Zvanja));
            }
        }
    }
    public void deleteNumbers(View v){
        String team1Points=pointsView1.getText().toString();
        String team2Points=pointsView2.getText().toString();
        String team1Zvanja=zvanjaView1.getText().toString();
        String team2Zvanja=zvanjaView2.getText().toString();

        if(pointsView1.isChecked()){
            team1Points=team1Points.substring(0,team1Points.length()-1);
            if(team1Points.length()==0)
                team1Points="0";
            team2Points=String.valueOf(162-Integer.parseInt(team1Points));
            pointsView1.setTextOff(team1Points);
            pointsView1.setTextOn(team1Points);
            pointsView1.setText(team1Points);
            pointsView2.setTextOff(team2Points);
            pointsView2.setTextOn(team2Points);
            pointsView2.setText(team2Points);
        }else if(pointsView2.isChecked()){
            team2Points=team2Points.substring(0,team2Points.length()-1);
            if(team2Points.length()==0)
                team2Points="0";
            team1Points=String.valueOf(162-Integer.parseInt(team2Points));
            pointsView1.setTextOff(team1Points);
            pointsView1.setTextOn(team1Points);
            pointsView1.setText(team1Points);
            pointsView2.setTextOff(team2Points);
            pointsView2.setTextOn(team2Points);
            pointsView2.setText(team2Points);
        }else if(zvanjaView1.isChecked()){
            team1Zvanja=team1Zvanja.substring(0,team1Zvanja.length()-1);
            if(team1Zvanja.length()==0)
                team1Zvanja="0";
            zvanjaView1.setTextOff(String.valueOf(team1Zvanja));
            zvanjaView1.setTextOn(String.valueOf(team1Zvanja));
            zvanjaView1.setText(String.valueOf(team1Zvanja));
        }else if(zvanjaView2.isChecked()){
            team2Zvanja=team2Zvanja.substring(0,team2Zvanja.length()-1);
            if(team2Zvanja.length()==0)
                team2Zvanja="0";
            zvanjaView2.setTextOff(String.valueOf(team2Zvanja));
            zvanjaView2.setTextOn(String.valueOf(team2Zvanja));
            zvanjaView2.setText(String.valueOf(team2Zvanja));
        }
    }

    public void stigljaNormal(View v){
        int team1Zvanja=Integer.parseInt(zvanjaView1.getText().toString());
        int team2Zvanja=Integer.parseInt(zvanjaView2.getText().toString());
        if(pointsView1.isChecked()){
            pointsView1.setTextOff("252");
            pointsView1.setTextOn("252");
            pointsView1.setText("252");
            pointsView2.setTextOff("0");
            pointsView2.setTextOn("0");
            pointsView2.setText("0");
            team1Zvanja+=team2Zvanja;
            team2Zvanja=0;
            zvanjaView1.setTextOff(String.valueOf(team1Zvanja));
            zvanjaView1.setTextOn(String.valueOf(team1Zvanja));
            zvanjaView1.setText(String.valueOf(team1Zvanja));
            zvanjaView2.setTextOff(String.valueOf(team2Zvanja));
            zvanjaView2.setTextOn(String.valueOf(team2Zvanja));
            zvanjaView2.setText(String.valueOf(team2Zvanja));
        }else if(pointsView2.isChecked()){
            pointsView2.setTextOff("252");
            pointsView2.setTextOn("252");
            pointsView2.setText("252");
            pointsView1.setTextOff("0");
            pointsView1.setTextOn("0");
            pointsView1.setText("0");
            team2Zvanja+=team1Zvanja;
            team1Zvanja=0;
            zvanjaView1.setTextOff(String.valueOf(team1Zvanja));
            zvanjaView1.setTextOn(String.valueOf(team1Zvanja));
            zvanjaView1.setText(String.valueOf(team1Zvanja));
            zvanjaView2.setTextOff(String.valueOf(team2Zvanja));
            zvanjaView2.setTextOn(String.valueOf(team2Zvanja));
            zvanjaView2.setText(String.valueOf(team2Zvanja));
        }
    }

    public void clearAll(View v){
        pointsView1.setTextOff("0");
        pointsView1.setTextOn("0");
        pointsView1.setText("0");
        pointsView2.setTextOff("0");
        pointsView2.setTextOn("0");
        pointsView2.setText("0");
        zvanjaView1.setTextOff(String.valueOf("0"));
        zvanjaView1.setTextOn(String.valueOf("0"));
        zvanjaView1.setText(String.valueOf("0"));
        zvanjaView2.setTextOff(String.valueOf("0"));
        zvanjaView2.setTextOn(String.valueOf("0"));
        zvanjaView2.setText(String.valueOf("0"));
    }

    public void belotNormal(View v){
        if(zvanjaView1.isChecked()){
            zvanjaView1.setTextOff("1001");
            zvanjaView1.setTextOn("1001");
            zvanjaView1.setText("1001");
        }else if(zvanjaView2.isChecked()){
            zvanjaView2.setTextOff("1001");
            zvanjaView2.setTextOn("1001");
            zvanjaView2.setText("1001");
        }
    }

    public void enterNormal(View v){
        boolean isEdit=getIntent().getBooleanExtra(ISEDIT,false);
        ((GlobalGame)this.getApplication()).setup=true;
        ((GlobalGame)this.getApplication()).edit=isEdit;

        Game currentGame=((GlobalGame)this.getApplication()).getGame();
        Partija currentPartija=currentGame.getPartijas().get(currentGame.getPartijas().size()-1);
        int team1Points=Integer.parseInt(pointsView1.getText().toString());
        int team2Points=Integer.parseInt(pointsView2.getText().toString());
        int team1Zvanja=Integer.parseInt(zvanjaView1.getText().toString());
        int team2Zvanja=Integer.parseInt(zvanjaView2.getText().toString());
        String usao=new String();
        int usaoIndex=0;

        boolean odabrano=true;
        if(player1.isChecked()){
            usao=player1.getText().toString();
            usaoIndex=1;
        }else if(player2.isChecked()){
            usao=player2.getText().toString();
            usaoIndex=2;
        }else if(player3.isChecked()){
            usao=player3.getText().toString();
            usaoIndex=3;
        }else if(player4.isChecked()){
            usao=player4.getText().toString();
            usaoIndex=4;
        }else{
            odabrano=false;
        }
        if(odabrano){
            if(isEdit){
                int editIndex=getIntent().getIntExtra(EDITINDEX,-1);
                if((team1Points==0&&team2Points==0)&&(team1Zvanja!=1001 && team2Zvanja!=1001)){
                    currentPartija.getRounds().remove(editIndex);
                }else{
                    Round editRound=currentPartija.getRounds().get(editIndex);
                    if(team1Points==252){
                        editRound.setZvanjaTeam2(0);
                        editRound.setZvanjaTeam1(team1Zvanja+team2Zvanja);
                        editRound.setPointsTeam2(0);
                        editRound.setPointsTeam1(252);
                        editRound.setPad(false);
                    }else if(team2Points==252){
                        editRound.setZvanjaTeam2(team1Zvanja+team2Zvanja);
                        editRound.setZvanjaTeam1(0);
                        editRound.setPointsTeam2(252);
                        editRound.setPointsTeam1(0);
                        editRound.setPad(false);
                    }else{
                        int ukupno=team1Points+team1Zvanja+team2Points+team2Zvanja;
                        if(usaoIndex==1||usaoIndex==2){
                            if((team2Points+team2Zvanja)<ukupno/2){
                                editRound.setZvanjaTeam2(team2Zvanja);
                                editRound.setZvanjaTeam1(team1Zvanja);
                                editRound.setPointsTeam2(team2Points);
                                editRound.setPointsTeam1(team1Points);
                                editRound.setPad(false);
                            }else{
                                editRound.setZvanjaTeam2(team2Zvanja+team1Zvanja);
                                editRound.setZvanjaTeam1(0);
                                editRound.setPointsTeam2(162);
                                editRound.setPointsTeam1(0);
                                editRound.setPad(true);
                            }
                        }else{
                            if((team1Points+team1Zvanja)<ukupno/2){
                                editRound.setZvanjaTeam2(team2Zvanja);
                                editRound.setZvanjaTeam1(team1Zvanja);
                                editRound.setPointsTeam2(team2Points);
                                editRound.setPointsTeam1(team1Points);
                                editRound.setPad(false);
                            }else{
                                editRound.setZvanjaTeam2(0);
                                editRound.setZvanjaTeam1(team1Zvanja+team2Zvanja);
                                editRound.setPointsTeam2(0);
                                editRound.setPointsTeam1(162);
                                editRound.setPad(true);
                            }
                        }
                        editRound.setUsao(usao);
                        editRound.setUsaoIndex(usaoIndex);
                    }
                }
                ((GlobalGame)this.getApplication()).cont=true;
                currentPartija.updateVariables();
                finish();
            }else{
                int ukupno=team1Points+team1Zvanja+team2Points+team2Zvanja;
                boolean pad=false;
                if(team1Points==252){
                    team1Zvanja+=team2Zvanja;
                    team2Zvanja=0;
                    team2Points=0;
                }else if(team2Points==252){
                    team2Zvanja+=team1Zvanja;
                    team1Zvanja=0;
                    team1Points=0;
                }else{
                    if(usaoIndex==1||usaoIndex==2){
                        if((team2Points+team2Zvanja)>=ukupno/2){
                            team2Points=162;
                            team2Zvanja+=team1Zvanja;
                            team1Points=0;
                            team1Zvanja=0;
                            pad=true;
                        }
                    }else{
                        if((team1Points+team1Zvanja)>=ukupno/2){
                            team1Points=162;
                            team1Zvanja+=team2Zvanja;
                            team2Points=0;
                            team2Zvanja=0;
                            pad=true;
                        }
                    }
                }
                Round newRound=new Round(currentPartija.getRounds().size(),team1Points,team2Points,team1Zvanja,team2Zvanja);
                newRound.setUsao(usao);
                newRound.setPad(pad);
                newRound.setUsaoIndex(usaoIndex);
                currentPartija.addRound(newRound);

                currentPartija.updateVariables();
                Log.d("newRound", String.valueOf(newRound.getPointsTeam1()));
                ((GlobalGame)this.getApplication()).cont=true;
                finish();
            }
        }else{
            player1.setError("Prvo odaberite tko je ušao!");
            player2.setError("Prvo odaberite tko je ušao!");
            player3.setError("Prvo odaberite tko je ušao!");
            player4.setError("Prvo odaberite tko je ušao!");
            Toast.makeText(this,"Morate odabrat tko je ušao",Toast.LENGTH_LONG).show();
        }

    }
}