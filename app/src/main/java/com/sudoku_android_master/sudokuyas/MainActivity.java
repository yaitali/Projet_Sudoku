package com.sudoku_android_master.sudokuyas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

private Button  buttonPlay;;
public Button about;
public Button instructions;
private static final int alertDialogInstructions  = 0;
private static final int alertDialogAbout         = 1;
public  AlertDialog.Builder dialogBuilder;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case alertDialogInstructions:
                // Create out AlterDialog
                dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Le but du jeu est de remplir la grille avec une série de chiffres,\ntous différents, qui ne se trouvent jamais plus d’une fois sur une même ligne, dans une même colonne ou dans une même région .\n");
                dialogBuilder.setCancelable(true);
                dialogBuilder.setNegativeButton("Cancel", new CancelOnClickListener());
                AlertDialog alertDialogInstructions = dialogBuilder.create();
                alertDialogInstructions.show();
                break;
            case alertDialogAbout :
                // Create out AlterDialog
                dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Jeu Sudoku \n Réaliser par  :\nYasmina AIT ALI \n Saliha Oudjidane");
                dialogBuilder.setCancelable(true);
                dialogBuilder.setNegativeButton("Cancel", new CancelOnClickListener());
                AlertDialog alertDialogAbout = dialogBuilder.create();
                alertDialogAbout.show();
                break;
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        buttonPlay         = (Button) findViewById(R.id.play);
        about        = (Button) findViewById(R.id.about);
        instructions = (Button) findViewById(R.id.instructions);
        final Intent puzzle=new Intent(this,Sudoku.class);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(puzzle);
            }
        });



        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(alertDialogInstructions);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(alertDialogAbout);
            }
        });


    }
    public void onResume(Bundle savedInstanceState){
        super.onResume();
    }
    public void onStop(){
        super.onStop();

    }
    public void onRestart(){
        super.onRestart();

    }
}