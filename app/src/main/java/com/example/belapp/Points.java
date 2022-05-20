package com.example.belapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.List;

public class Points extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        //get passed values
        Intent current=getIntent();
        int pointsMi=current.getIntExtra("pointsMi",0);
        int pointsVi=current.getIntExtra("pointsVi",0);
        int zvMi=current.getIntExtra("zvanjaMi",0);
        int zvVi=current.getIntExtra("zvanjaVi",0);

        //set initial points
        ToggleButton mi=findViewById(R.id.pointsMi);
        mi.callOnClick();
        ToggleButton vi=findViewById(R.id.pointsVi);
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);
        mi.setText(String.valueOf(pointsMi));
        vi.setText(String.valueOf(pointsVi));
        zvanjaMi.setText(String.valueOf(zvMi));
        zvanjaVi.setText(String.valueOf(zvVi));
        mi.setTextOn(String.valueOf(pointsMi));
        vi.setTextOn(String.valueOf(pointsVi));
        zvanjaMi.setTextOn(String.valueOf(zvMi));
        zvanjaVi.setTextOn(String.valueOf(zvVi));
        mi.setTextOff(String.valueOf(pointsMi));
        vi.setTextOff(String.valueOf(pointsVi));
        zvanjaMi.setTextOff(String.valueOf(zvMi));
        zvanjaVi.setTextOff(String.valueOf(zvVi));
    }


    public void enter(View v){
        //grab the views in which we have to input the value
        ToggleButton mi=findViewById(R.id.pointsMi);
        ToggleButton vi=findViewById(R.id.pointsVi);
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);

        //variables for creating a new round
        int pointsMi = Integer.parseInt(mi.getTextOff().toString());
        int pointsVi = Integer.parseInt(vi.getTextOff().toString());
        int zvMi = Integer.parseInt(zvanjaMi.getTextOff().toString());
        int zvVi = Integer.parseInt(zvanjaVi.getTextOff().toString());
        boolean stigljaMi = mi.getTextOff().toString().equals("252");
        boolean stigljaVi = vi.getTextOff().toString().equals("252");

        if(stigljaMi) {
            zvMi += zvVi;
            zvVi=0;
        }else if(stigljaVi){
            zvVi+=zvMi;
            zvMi=0;
        }

        //find out if it is a edit
        Intent current=getIntent();
        boolean edit=current.getBooleanExtra("edit",false);

        if(!edit) {
            if (!mi.getTextOff().toString().equals("0") || !vi.getTextOff().toString().equals("0")) {

                //add new round to current partija
                Game currentGame = ((GlobalGame) this.getApplication()).getGame();
                Partija currentPartija = currentGame.getPartijas().get(currentGame.getPartijas().size() - 1);
                Round round = new Round( currentPartija.getRounds().size(),pointsMi, pointsVi, zvMi, zvVi);
                currentPartija.addRound(round);
            }
        }else{
            int roundIndex=current.getIntExtra("index",-1);

            Game currentGame = ((GlobalGame) this.getApplication()).getGame();
            Partija currentPartija = currentGame.getPartijas().get(currentGame.getPartijas().size() - 1);
            Round editRound=currentPartija.getRounds().get(roundIndex);
            editRound.setPointsTeam1(pointsMi);
            editRound.setPointsTeam2(pointsVi);

            editRound.setZvanjaTeam1(zvMi);
            editRound.setZvanjaTeam2(zvVi);
            if(editRound.getPointsTeam1()==0&&editRound.getPointsTeam2()==0) currentPartija.getRounds().remove(editRound);
        }
        finish();
    }

    public void stiglja(View v){

        //get the mi and vi points
        ToggleButton mi=findViewById(R.id.pointsMi);
        ToggleButton vi=findViewById(R.id.pointsVi);

        //find out which one is checked
        if(mi.isChecked()){
            //set the points to stiglja points
            mi.setTextOn("252");
            mi.setTextOff("252");
            mi.setText("252");
            vi.setTextOn("0");
            vi.setTextOff("0");
            vi.setText("0");
        }else if(vi.isChecked()){

            //set the points to stiglja points
            vi.setTextOn("252");
            vi.setTextOff("252");
            vi.setText("252");
            mi.setTextOn("0");
            mi.setTextOff("0");
            mi.setText("0");
        }
    }

    public void belot(View v){

        //get the mi and vi points
        ToggleButton mi=findViewById(R.id.zvanjaMi);
        ToggleButton vi=findViewById(R.id.zvanjaVi);

        //check which button is checked
        if(mi.isChecked()){

            //set the value to the 1001
            mi.setText("1001");
            mi.setTextOn("1001");
            mi.setTextOff("1001");

        }else if(vi.isChecked()){

            //set the value to the 1001
            vi.setText("1001");
            vi.setTextOn("1001");
            vi.setTextOff("1001");
        }
    }

    public void clear(View v){
        //grab the views in which we have to input the value
        ToggleButton mi=findViewById(R.id.pointsMi);
        ToggleButton vi=findViewById(R.id.pointsVi);
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);

        //clear MI
        mi.setTextOff("0");
        mi.setText("0");
        mi.setTextOn("0");

        //clear VI
        vi.setTextOff("0");
        vi.setText("0");
        vi.setTextOn("0");

        //clear zvanjaMI
        zvanjaMi.setTextOff("0");
        zvanjaMi.setText("0");
        zvanjaMi.setTextOn("0");

        //clear zvanjaVi
        zvanjaVi.setTextOff("0");
        zvanjaVi.setText("0");
        zvanjaVi.setTextOn("0");
    }

    public void zvanja(View v){
        //cast the clicked view to a button because we are sure it is a button
        Button clickedButton=(Button)v;

        //get the zvanja buttons
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);

        //check which zvanja button is checked
        if(zvanjaMi.isChecked()){
            //add the old value to the clicked value
            String zvanja=String.valueOf(Integer.parseInt(clickedButton.getText().toString())+Integer.parseInt(zvanjaMi.getTextOff().toString()));

            //set the value to the sum
            zvanjaMi.setText(zvanja);
            zvanjaMi.setTextOn(zvanja);
            zvanjaMi.setTextOff(zvanja);

        }else if(zvanjaVi.isChecked()){
            //add the old value to the sum
            String zvanja=String.valueOf(Integer.parseInt(clickedButton.getText().toString())+Integer.parseInt(zvanjaVi.getTextOff().toString()));

            //set the value to the value of the clicked button
            zvanjaVi.setText(zvanja);
            zvanjaVi.setTextOn(zvanja);
            zvanjaVi.setTextOff(zvanja);
        }
    }

    public void delete(View v){
        //grab the views in which we have to input the value
        ToggleButton mi=findViewById(R.id.pointsMi);
        ToggleButton vi=findViewById(R.id.pointsVi);
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);

        //find out which one is active
        if(mi.isChecked()) {
            //get the old text
            StringBuilder text=new StringBuilder(mi.getTextOff());

            //check if it is zero
            if(!text.toString().equals("0")){

                //if not delete the last character
                text.deleteCharAt(text.length()-1);

                //if the length is zero set the value to 0 else to text
                if(text.length()==0){
                    mi.setTextOff("0");
                    mi.setText("0");
                    mi.setTextOn("0");

                    //the rest is 162
                    vi.setTextOff("162");
                    vi.setText("162");
                    vi.setTextOn("162");
                }else {
                    mi.setTextOff(text.toString());
                    mi.setText(text.toString());
                    mi.setTextOn(text.toString());

                    //set the value of vi to the rest of 162
                    int rest=162-Integer.parseInt(text.toString());
                    vi.setTextOff(String.valueOf(rest));
                    vi.setText(String.valueOf(rest));
                    vi.setTextOn(String.valueOf(rest));
                }
            }
        }else if(vi.isChecked()){
            //get the old text
            StringBuilder text=new StringBuilder(vi.getTextOff());

            //check if it is zero
            if(!text.toString().equals("0")){

                //if not delete the last character
                text.deleteCharAt(text.length()-1);

                //if the length is zero set the value to 0 else to text
                if(text.length()==0){
                    vi.setTextOff("0");
                    vi.setText("0");
                    vi.setTextOn("0");

                    //the rest is 162
                    mi.setTextOff("162");
                    mi.setText("162");
                    mi.setTextOn("162");
                }else {
                    vi.setTextOff(text.toString());
                    vi.setText(text.toString());
                    vi.setTextOn(text.toString());

                    //set the value of mi to the rest of 162
                    int rest=162-Integer.parseInt(text.toString());
                    mi.setTextOff(String.valueOf(rest));
                    mi.setText(String.valueOf(rest));
                    mi.setTextOn(String.valueOf(rest));
                }
            }
        }else if(zvanjaMi.isChecked()){
            //get the old text
            StringBuilder text=new StringBuilder(zvanjaMi.getTextOff());

            //check if it is zero
            if(!text.toString().equals("0")){

                //if not delete the last character
                text.deleteCharAt(text.length()-1);

                //if the length is zero set the value to 0 else to text
                if(text.length()==0){
                    zvanjaMi.setTextOff("0");
                    zvanjaMi.setText("0");
                    zvanjaMi.setTextOn("0");
                }else {
                    zvanjaMi.setTextOff(text.toString());
                    zvanjaMi.setText(text.toString());
                    zvanjaMi.setTextOn(text.toString());
                }
            }
        }else if(zvanjaVi.isChecked()){
            //get the old text
            StringBuilder text=new StringBuilder(zvanjaVi.getTextOff());

            //check if it is zero
            if(!text.toString().equals("0")){

                //if not delete the last character
                text.deleteCharAt(text.length()-1);

                //if the length is zero set the value to 0 else to text
                if(text.length()==0){
                    zvanjaVi.setTextOff("0");
                    zvanjaVi.setText("0");
                    zvanjaVi.setTextOn("0");
                }else {
                    zvanjaVi.setTextOff(text.toString());
                    zvanjaVi.setText(text.toString());
                    zvanjaVi.setTextOn(text.toString());
                }
            }
        }
    }

    public void pointsSelect(View v){
        //cast the clicked view to a ToggleButton because we are sure it is a ToggleButton
        ToggleButton clickedButton=(ToggleButton)v;
        clickedButton.setBackgroundColor(0x298E0000);
        clickedButton.setChecked(true);
        //grab all the toggle buttons
        ToggleButton mi=findViewById(R.id.pointsMi);
        ToggleButton vi=findViewById(R.id.pointsVi);
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);

        //check which button is checked and uncheck it and change the color
        if(mi.isChecked() && mi!=clickedButton){
            mi.setChecked(false);
            mi.setBackgroundColor(0x287E8181);
        }
        if(vi.isChecked() && vi!=clickedButton){
            vi.setChecked(false);
            vi.setBackgroundColor(0x287E8181);
        }
        if(zvanjaMi.isChecked() && zvanjaMi!=clickedButton){
            zvanjaMi.setChecked(false);
            zvanjaMi.setBackgroundColor(0x287E8181);
        }
        if(zvanjaVi.isChecked() && zvanjaVi!=clickedButton){
            zvanjaVi.setChecked(false);
            zvanjaVi.setBackgroundColor(0x287E8181);
        }
    }

    public void pointsInput(View v){
        //cast the clicked view to a button because we are sure it is a button
        Button clickedButton=(Button)v;

        //grab the views in which we have to input the value
        ToggleButton mi=findViewById(R.id.pointsMi);
        ToggleButton vi=findViewById(R.id.pointsVi);
        ToggleButton zvanjaMi=findViewById(R.id.zvanjaMi);
        ToggleButton zvanjaVi=findViewById(R.id.zvanjaVi);

        //find out which one is active
        if(mi.isChecked()) {
            if (!vi.getTextOff().toString().equals("252")){
                //grab the old result
                StringBuilder textMi = new StringBuilder(mi.getTextOff());

            //if it is zero delete the zero and put in the new number
            if (textMi.toString().equals("0")) {
                //putting the result on team MI
                mi.setText(clickedButton.getText());
                mi.setTextOff(clickedButton.getText());
                mi.setTextOn(clickedButton.getText());

                //the rest of 162 is the result of team VI
                String rest = String.valueOf(162 - Integer.parseInt(clickedButton.getText().toString()));
                vi.setText(rest);
                vi.setTextOff(rest);
                vi.setTextOn(rest);

                //if it is not zero then we just append the clicked number
            } else {
                //grab the old result
                textMi.append(clickedButton.getText());

                //check if the result is ok
                if (Integer.parseInt(textMi.toString()) <= 162) {
                    //putting the result on team MI
                    mi.setText(textMi.toString());
                    mi.setTextOff(textMi.toString());
                    mi.setTextOn(textMi.toString());

                    //the rest of 162 is the result of team VI
                    String rest = String.valueOf(162 - Integer.parseInt(textMi.toString()));
                    vi.setText(rest);
                    vi.setTextOff(rest);
                    vi.setTextOn(rest);
                }
            }
        }
        }else if(vi.isChecked()){
            if (!mi.getTextOff().toString().equals("252")) {
                //old result
                StringBuilder textVi = new StringBuilder(vi.getTextOff());

                //if it is zero delete the zero and put in the new number
                if (textVi.toString().equals("0")) {
                    //set the result of team MI
                    vi.setText(clickedButton.getText());
                    vi.setTextOff(clickedButton.getText());
                    vi.setTextOn(clickedButton.getText());

                    //the rest of 162 is the result of team MI
                    String rest = String.valueOf(162 - Integer.parseInt(clickedButton.getText().toString()));
                    mi.setText(rest);
                    mi.setTextOff(rest);
                    mi.setTextOn(rest);

                    //if it is not zero then we just append the clicked number
                } else {
                    //append the new number
                    textVi.append(clickedButton.getText());

                    //check if the result is OK
                    if (Integer.parseInt(textVi.toString()) <= 162) {
                        //set the value of vi points
                        vi.setText(textVi);
                        vi.setTextOff(textVi);
                        vi.setTextOn(textVi);

                        //calculate the rest
                        String rest = String.valueOf(162 - Integer.parseInt(textVi.toString()));

                        //set the value of mi points
                        mi.setText(rest);
                        mi.setTextOff(rest);
                        mi.setTextOn(rest);
                    }
                }
            }
        }else if(zvanjaMi.isChecked()){
            //old text
            StringBuilder textMi=new StringBuilder(zvanjaMi.getTextOff());

            //if it is zero delete the zero and put in the new number
            if(textMi.toString().equals("0")){

                //set zvanja for tem MI
                zvanjaMi.setText(clickedButton.getText());
                zvanjaMi.setTextOff(clickedButton.getText());
                zvanjaMi.setTextOn(clickedButton.getText());

            //if it is not zero then we just append the clicked number
            }else{

                //append the new number
                textMi.append(clickedButton.getText());

                //set zvanja for team MI
                if(Integer.parseInt(textMi.toString())<=1001) {
                    zvanjaMi.setText(textMi.toString());
                    zvanjaMi.setTextOff(textMi.toString());
                    zvanjaMi.setTextOn(textMi.toString());
                }

            }
        }else if(zvanjaVi.isChecked()){
            //get the old text
            StringBuilder textVi=new StringBuilder(zvanjaVi.getTextOff());

            //if it is zero delete the zero and put in the new number
            if(textVi.toString().equals("0")){
                zvanjaVi.setText(clickedButton.getText());
                zvanjaVi.setTextOff(clickedButton.getText());
                zvanjaVi.setTextOn(clickedButton.getText());

            //if it is not zero then we just append the clicked number
            }else{
                //append the value
                textVi.append(clickedButton.getText());

                //set zvanja for team VI
                if(Integer.parseInt(textVi.toString())<1001) {
                    zvanjaVi.setText(textVi.toString());
                    zvanjaVi.setTextOff(textVi.toString());
                    zvanjaVi.setTextOn(textVi.toString());
                }
            }
        }
    }
}