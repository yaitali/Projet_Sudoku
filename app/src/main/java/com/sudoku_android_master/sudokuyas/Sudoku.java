package com.sudoku_android_master.sudokuyas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

// declaration de notre activity héritée de Activity
public class Sudoku extends Activity {

    private SudokuView mSudokuView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // initialise notre activity avec le constructeur parent
        super.onCreate(savedInstanceState);
        // charge le fichier main.xml comme vue de l'activité
        setContentView(R.layout.main);
        // recuperation de la vue une voie cree à partir de son id
        mSudokuView = (SudokuView)findViewById(R.id.SudokuView);
        // rend visible la vue
        mSudokuView.setVisibility(View.VISIBLE);
    }
}