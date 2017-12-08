package com.sudoku_android_master.sudokuyas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class SudokuView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    // Declaration des images
    private Bitmap vide;
    private Bitmap un;
    private Bitmap deux;
    private Bitmap trois;
    private Bitmap quatre;
    private Bitmap cinq;
    private Bitmap six;
    private Bitmap sept;
    private Bitmap huite;
    private Bitmap neuf;
    private Bitmap effacer;
    private Bitmap renitialiser;
    private Bitmap verifier;
    private Bitmap win;
    private Bitmap withe;

    private Bitmap chrono;
    private long beginChrono, endChrono;
    boolean displayDialog = true;


    private Resources mRes;
    private Context mContext;
    Paint text = new Paint();
    int compteur=0;
    int niveau=1;
    int CaseChoisiePetiteMat;
    int btnSup,ibtn,jbtn;
    String winer;
    int OrigineI, OrigineY, mI,mJ,J,I;
    boolean caseSelectionner=false;
    int[] tableauCaseSelectionnerGrandeMatrice= new int[2];
    int [] tableauCaseSelectionnerGrandeMatrice1=new int [2];
    boolean Clibre;
    int[][] matrice;
    int carteTopAnchor;                   // coordonnées en Y du point d'ancrage de notre carte
    int carteLeftAnchor;                  // coordonnées en X du point d'ancrage de notre carte
    static final int carteWidth = 9;
    static final int carteHeight = 9;
    static final int MatWidth = 9;
    static final int MatHeight = 1;
    static final int carteTileSize = 20;
    static final int CST_un = 1;
    static final int CST_deux = 2;
    static final int CST_trois = 3;
    static final int CST_quatre = 4;
    static final int CST_cinq = 5;
    static final int CST_six = 6;
    static final int CST_sept = 7;
    static final int CST_huite = 8;
    static final int CST_neuf = 9;
    static final int CST_Vide = 0;
    static final int CST_vert = 77;
    static final int G_un = 1;
    static final int G_deux = 2;
    static final int G_trois = 3;
    static final int G_quatre = 4;
    static final int G_cinq = 5;
    static final int G_six = 6;
    static final int G_sept = 7;
    static final int G_huite = 8;
    static final int G_neuf = 9;
    static final int G_effacer = 10;
    static final int G_renitialiser = 11;
    static final int G_verifier = 12;
    // tableau de reference de la grille

    int[][] EASY = {
            {CST_deux, CST_un, CST_neuf, CST_sept, CST_huite, CST_cinq, CST_trois, CST_six, CST_quatre},
            {CST_cinq, CST_trois, CST_sept, CST_quatre, CST_six, CST_deux, CST_huite, CST_un, CST_neuf},
            {CST_huite, CST_quatre, CST_six, CST_neuf, CST_trois, CST_un, CST_deux, CST_cinq, CST_sept},
            {CST_six, CST_neuf, CST_quatre, CST_cinq, CST_sept, CST_trois, CST_un, CST_huite, CST_deux},
            {CST_sept, CST_cinq, CST_deux, CST_un, CST_quatre, CST_huite, CST_neuf, CST_trois, CST_six},
            {CST_trois, CST_huite, CST_Vide, CST_deux, CST_neuf, CST_six, CST_Vide, CST_quatre, CST_cinq},
            {CST_quatre, CST_deux, CST_huite, CST_trois, CST_Vide, CST_sept, CST_six, CST_neuf, CST_un},
            {CST_un, CST_six, CST_cinq, CST_huite, CST_deux, CST_neuf, CST_quatre, CST_sept, CST_trois},
            {CST_neuf, CST_sept, CST_trois, CST_six, CST_un, CST_quatre, CST_cinq, CST_deux, CST_huite},
    };
    int[][] EASY_Sol = {
            {CST_deux, CST_un, CST_neuf, CST_sept, CST_huite, CST_cinq, CST_trois, CST_six, CST_quatre},
            {CST_cinq, CST_trois, CST_sept, CST_quatre, CST_six, CST_deux, CST_huite, CST_un, CST_neuf},
            {CST_huite, CST_quatre, CST_six, CST_neuf, CST_trois, CST_un, CST_deux, CST_cinq, CST_sept},
            {CST_six, CST_neuf, CST_quatre, CST_cinq, CST_sept, CST_trois, CST_un, CST_huite, CST_deux},
            {CST_sept, CST_cinq, CST_deux, CST_un, CST_quatre, CST_huite, CST_neuf, CST_trois, CST_six},
            {CST_trois, CST_huite, CST_un, CST_deux, CST_neuf, CST_six, CST_sept, CST_quatre, CST_cinq},
            {CST_quatre, CST_deux, CST_huite, CST_trois, CST_cinq, CST_sept, CST_six, CST_neuf, CST_un},
            {CST_un, CST_six, CST_cinq, CST_huite, CST_deux, CST_neuf, CST_quatre, CST_sept, CST_trois},
            {CST_neuf, CST_sept, CST_trois, CST_six, CST_un, CST_quatre, CST_cinq, CST_deux, CST_huite},
    };
    int[][] MEDIEUM = {
            {CST_cinq, CST_Vide, CST_Vide, CST_trois, CST_deux, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_Vide, CST_Vide, CST_deux, CST_Vide, CST_un, CST_neuf, CST_Vide, CST_sept, CST_Vide},
            {CST_Vide, CST_Vide, CST_six, CST_huite, CST_Vide, CST_Vide, CST_deux, CST_Vide, CST_Vide},
            {CST_six, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_deux},
            {CST_huite, CST_quatre, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_trois, CST_sept},
            {CST_neuf, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_six},
            {CST_Vide, CST_Vide,CST_cinq, CST_Vide, CST_Vide, CST_trois, CST_neuf, CST_Vide, CST_Vide},
            {CST_Vide, CST_neuf, CST_Vide, CST_quatre, CST_cinq, CST_Vide, CST_six, CST_Vide, CST_Vide},
            {CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_six, CST_un, CST_Vide, CST_Vide, CST_huite},
    };
    int[][] MEDIEUM_SOLUTION = {
            {CST_cinq, CST_sept, CST_neuf, CST_trois, CST_deux, CST_quatre, CST_huite, CST_six, CST_un},
            {CST_quatre, CST_huite, CST_deux, CST_six, CST_un, CST_neuf, CST_trois, CST_sept, CST_cinq},
            {CST_trois, CST_un, CST_six, CST_huite, CST_sept, CST_cinq, CST_deux, CST_quatre, CST_neuf},
            {CST_six, CST_cinq, CST_sept, CST_un, CST_trois, CST_huite, CST_quatre, CST_neuf, CST_deux},
            {CST_huite, CST_quatre, CST_un, CST_deux, CST_neuf, CST_six, CST_cinq, CST_trois, CST_sept},
            {CST_neuf, CST_deux, CST_trois, CST_cinq, CST_quatre, CST_sept, CST_un, CST_huite, CST_six},
            {CST_un, CST_six,CST_cinq, CST_sept, CST_huite, CST_trois, CST_neuf, CST_deux, CST_quatre},
            {CST_sept, CST_neuf, CST_huite, CST_quatre, CST_cinq, CST_deux, CST_six, CST_un, CST_trois},
            {CST_deux, CST_trois, CST_quatre, CST_neuf, CST_six, CST_un, CST_sept, CST_cinq, CST_huite},
    };
    int[][] HARD = {
            {CST_cinq, CST_Vide, CST_Vide, CST_trois, CST_deux, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_Vide, CST_Vide, CST_deux, CST_Vide, CST_un, CST_neuf, CST_Vide, CST_sept, CST_Vide},
            {CST_Vide, CST_Vide, CST_six, CST_huite, CST_Vide, CST_Vide, CST_deux, CST_Vide, CST_Vide},
            {CST_six, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_deux},
            {CST_huite, CST_quatre, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_trois, CST_sept},
            {CST_neuf, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_six},
            {CST_Vide, CST_Vide, CST_cinq, CST_Vide, CST_Vide, CST_trois, CST_neuf, CST_Vide, CST_Vide},
            {CST_Vide, CST_neuf, CST_Vide, CST_quatre, CST_cinq, CST_Vide, CST_six, CST_Vide, CST_Vide},
            {CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_six, CST_un, CST_Vide, CST_Vide, CST_huite},
    };
    int[][] HARD_solution = {
            {CST_cinq, CST_sept, CST_neuf, CST_trois, CST_deux, CST_quatre, CST_huite, CST_six, CST_un},
            {CST_quatre, CST_huite, CST_deux, CST_six, CST_un, CST_neuf, CST_trois, CST_sept, CST_cinq},
            {CST_trois, CST_un, CST_six, CST_huite, CST_sept, CST_cinq, CST_deux, CST_quatre, CST_neuf},
            {CST_six, CST_cinq, CST_sept, CST_un, CST_trois, CST_huite, CST_quatre, CST_neuf, CST_deux},
            {CST_huite, CST_quatre, CST_un, CST_deux, CST_neuf, CST_six, CST_cinq, CST_trois, CST_sept},
            {CST_neuf, CST_deux, CST_trois, CST_cinq, CST_quatre, CST_sept, CST_un, CST_huite, CST_six},
            {CST_un, CST_six, CST_cinq, CST_sept, CST_huite, CST_trois, CST_neuf, CST_deux, CST_quatre},
            {CST_sept, CST_neuf, CST_huite, CST_quatre, CST_cinq, CST_deux, CST_six, CST_un, CST_trois},
            {CST_deux, CST_trois, CST_quatre, CST_neuf, CST_six, CST_un, CST_sept, CST_cinq, CST_huite},
    };

    int[][] mat_gestion = {
            {G_un, G_deux, G_trois,G_quatre, G_cinq, G_six,G_sept,G_huite, G_neuf}
    };
    int[][] mat_btn = {
            {G_effacer, G_renitialiser, G_verifier}
    };


    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;

    /**
     * The constructor called from the main JetBoy activity
     * @param context
     * @param attrs
     */


    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        mContext = context;
        mRes = mContext.getResources();
         un 		= BitmapFactory.decodeResource(mRes, R.drawable.un);
         //vert = BitmapFactory.decodeResource(mRes, R.drawable.vert);
         deux = BitmapFactory.decodeResource(mRes, R.drawable.deux);
         trois		= BitmapFactory.decodeResource(mRes, R.drawable.trois);
         quatre 	= BitmapFactory.decodeResource(mRes, R.drawable.quatre);
         cinq = BitmapFactory.decodeResource(mRes, R.drawable.cinq);
         six = BitmapFactory.decodeResource(mRes, R.drawable.six);
         sept 	= BitmapFactory.decodeResource(mRes, R.drawable.sept);
         huite  =BitmapFactory.decodeResource(mRes, R.drawable.huite);
         neuf   =BitmapFactory.decodeResource(mRes, R.drawable.neuf);
         vide		= BitmapFactory.decodeResource(mRes, R.drawable.vide);
        effacer  =BitmapFactory.decodeResource(mRes, R.drawable.effacer);
        renitialiser   =BitmapFactory.decodeResource(mRes, R.drawable.renitialiser);
        verifier		= BitmapFactory.decodeResource(mRes, R.drawable.ecrire);
        win    = BitmapFactory.decodeResource(mRes, R.drawable.win);
        chrono = BitmapFactory.decodeResource(mRes, R.drawable.chrono);
        withe  = BitmapFactory.decodeResource(mRes, R.drawable.withe);

        initparameters(1);
        cv_thread = new Thread(this);
        setFocusable(true);
    }

    public void startChrono(){
        beginChrono = System.currentTimeMillis();
    }
    public void stopChrono(){
        endChrono = System.currentTimeMillis();
    }
    public double getChrono() {
        return ((endChrono - beginChrono) / 1000);
    }

    private void loadlevel(int niveau) {

        if(niveau==1){
            for (int i=0; i< carteHeight; i++) {
                for (int j=0; j< carteWidth; j++) {
                    matrice[j][i]= EASY[j][i];

                }
            }

        } else if(niveau==2){
            for (int i=0; i< carteHeight; i++) {
                for (int j=0; j< carteWidth; j++) {
                    matrice[j][i]= MEDIEUM[j][i];

                }
            }}
        else if(niveau==3){
            for (int i=0; i< carteHeight; i++) {
                for (int j=0; j< carteWidth; j++) {
                    matrice[j][i]= HARD[j][i];

                }
            }}
        startChrono();
    }

    public int initparameters(int niveau) {
        matrice = new int[carteHeight][carteWidth];
        int load;
     loadlevel(niveau);

        carteTopAnchor = (getHeight() - carteHeight * carteTileSize) / 2;
        carteLeftAnchor = (getWidth() - carteWidth * carteTileSize) / 2;
        if ((cv_thread != null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        } return niveau;
    }

/*
séléctionner une case(la derniere ) dans la grille et récuperer ses coordonnées (i,j)
pour pouvoir la remplacer avec la case séléctionnée dans le vecteur des chiffres.
 */

    public int[]SelectedCaseVert(int i, int j){
            int[] res = new int[2];
        if(i>-1 && i<9 && j>-1 && j<9) {
            if (matrice[i][j] == CST_Vide && compteur != 1) {
                matrice[i][j] = CST_vert;
                caseSelectionner = true;
                OrigineI = i;
                OrigineY = j;
                compteur = 1;
            } else if (matrice[i][j] == CST_Vide && compteur == 1) {
                matrice[OrigineI][OrigineY] = CST_Vide;
                matrice[i][j] = CST_vert;
                caseSelectionner = true;
                OrigineI = i;
                OrigineY = j;
            }
            res[0]=i;
            res[1]=j;
         }else{

            res[0]=99;
            res[1]=99;
            caseSelectionner = false;
        }

        return res;
    }


   public int[]GestionGrandeMatrice(int i, int j){
        int[] resultat = new int[2];
        if(i>-1 && i<9 && j>-1 && j<9) {
            if (matrice[i][j] != CST_Vide) {
                caseSelectionner = true;
                OrigineI = i;
                OrigineY = j;
                compteur = 1;
            }
            resultat[0]=i;
            resultat[1]=j;
        }else{

            resultat[0]=99;
            resultat[1]=99;
            caseSelectionner = false;
        }

        return resultat;
    }

    /**
     *
     * @param i
     * @param j
     * @return chiffre ;le chiffre representant le bouton sur le quel on clique
     */
    public int LaCaseChoisiePetiteMat(int i, int j) {

        if (i == 0 && j == 0) { compteur=2;
            return 1;
        } else if (i == 0 && j == 1) { compteur=2;
            return 2;
        } else if (i == 0 && j == 2) { compteur=2;
            return 3;
        } else if (i == 0 && j == 3) { compteur=2;
            return 4;
        } else if (i == 0 && j == 4) { compteur=2;
            return 5;
        } else if (i == 0 && j == 5) { compteur=2;
            return 6;
        } else if (i == 0 && j == 6) { compteur=2;
            return 7;
        } else if (i == 0 && j == 7) { compteur=2;
            return 8;
        } else if (i == 0 && j == 8) {compteur=2;
            return 9;
        }
        else {
            return 99;
        }

    }

    public int btnSup(int i, int j) {

        if (i == 1 && j == 1) {compteur=2;
            return 11;
        } else if (i == 1 && j == 2) {compteur=2;

            return 12;
        } else if (i == 1  && j == 3) {compteur=3;

            return 13;
        } else {
            return 99;
        }
    }

    public boolean VerificationGrilleSudoku(int i,int j){
        boolean GrilleResolue =false;
        for ( i=0; i< carteHeight; i++) {
            for (j=0; j< carteWidth; j++) {
                if  (matrice[i][j]==EASY_Sol[i][j] && matrice[i][j]!=CST_Vide){
                    GrilleResolue =true;
                    Log.i("-> FCT <-", "La grille est bien remplie : " + GrilleResolue);

                }else if (matrice[i][j]!=EASY_Sol[i][j] ){
                    matrice[i][j]=CST_Vide;
                    GrilleResolue=false;
                    Log.i("-> FCT <-", "cette case est mal remplie: " + (matrice[i][j]));
                }
            }
        }
        return GrilleResolue ;

    };



    private void paintWin(Canvas canvas) {
        canvas.drawBitmap(win, carteLeftAnchor + 30, carteTopAnchor + 20, null);
    }
    private void paintChrono(Canvas canvas) {
        canvas.drawBitmap(chrono, getWidth()-80, 10 , null);
    }

    private void paintWithe(Canvas canvas) {
        canvas.drawBitmap(withe, -10, -10, null);
    }

// dessin de la carte du jeu
    private void paintcarte(Canvas canvas) {
        paintParametres(canvas);
        int x=getWidth();
        int tailleCarre = un.getHeight();
        for (int i = 0; i < carteHeight; i++) {
            for (int j = 0; j < carteWidth; j++) {
                switch (matrice[i][j]) {
                   /* case CST_vert:
                        canvas.drawBitmap(vert, j *80, 145+ i*80 , null);
                        break;*/
                    case CST_Vide:
                        canvas.drawBitmap(vide, j *80, 100+ i*80 , null);
                        break;
                    case CST_un:
                        canvas.drawBitmap(un, j *80, 100+ i*80 , null);
                        break;
                    case CST_deux:
                        canvas.drawBitmap(deux, j *80, 100+ i*80 , null);
                        break;
                    case CST_trois:
                        canvas.drawBitmap(trois, j *80, 100+ i*80 , null);
                        break;
                    case CST_quatre:
                        canvas.drawBitmap(quatre, j *80, 100+ i*80 , null);
                        break;
                    case CST_cinq:
                        canvas.drawBitmap(cinq, j *80, 100+ i*80 , null);
                                       break;
                    case CST_six:
                        canvas.drawBitmap(six, j *80, 100+ i*80 , null);
                        break;
                    case CST_sept:
                        canvas.drawBitmap(sept, j *80, 100+ i*80 , null);
                        break;
                    case CST_huite:
                        canvas.drawBitmap(huite, j *80, 100+ i*80 , null);
                        break;
                    case CST_neuf:
                        canvas.drawBitmap(neuf, j *80, 100+ i*80 , null);
                        break;
                }

            }
        }

        for (int i = 0; i < MatHeight; i++) {
            for (int j = 0; j < MatWidth; j++) {

                switch (mat_gestion[i][j]) {
                    case G_un:
                        canvas.drawBitmap(un, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_deux:
                        canvas.drawBitmap(deux, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_trois:
                        canvas.drawBitmap(trois, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_quatre:
                        canvas.drawBitmap(quatre, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_cinq:
                        canvas.drawBitmap(cinq, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_six:
                        canvas.drawBitmap(six, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_sept:
                        canvas.drawBitmap(sept, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_huite:

                        canvas.drawBitmap(huite, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                    case G_neuf:
                        canvas.drawBitmap(neuf, j * getWidth() / 9, getWidth() + 100 + 95 + (i * getWidth() / 9), null);
                        break;
                }
            }
        }

        for (int i = 0; i < MatHeight; i++) {
            for (int j = 0; j < MatWidth; j++) {

                switch (mat_btn[i][j]) {
                    case G_effacer:
                        canvas.drawBitmap(effacer, j * getWidth() / 3, getWidth() + 100 + 95+80 + (i * getWidth() / 3), null);
                        break;
                    case G_renitialiser:
                        canvas.drawBitmap(renitialiser, j * getWidth() / 3, getWidth() + 100+95+80 + (i * getWidth() / 3), null);
                        break;
                    case G_verifier:
                        canvas.drawBitmap(verifier, j * getWidth() / 3, getWidth() +100+95+80 + (i * getWidth() / 3), null);
                        break;
                }
            }
        }

    }
    //dessin de fond

    public boolean isWon(String winer) {
        boolean iswon = false;
        if (winer == "g") {
            iswon = true;

        } else if (winer=="e") {
            iswon = false;
        }
        return iswon;
    }

    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)

        private void paintParametres(Canvas canvas)
        {
            int time = (int) getChrono();
            text.setTextSize(20);
            text.setColor(Color.BLACK);
            //text.setStyle(Paint.Style.FILL_AND_STROKE);
            text.setFakeBoldText(true);
            if (niveau == 1) {
                if (isWon(winer)) {
                    paintWithe(canvas);
                    paintWin(canvas);
                }if(!isWon(winer))
                {
                    displayDialog = true;
                    text.setColor(Color.BLACK);
                    text.setFakeBoldText(true);
                    text.setTextSize(20);
                    canvas.drawText("EASY", 12, carteTopAnchor / 6, text);
                    paintChrono(canvas);
                    canvas.drawText(""+ time + "", getWidth()-120, 40, text);
                }
            }else if (niveau == 2) {
                if (isWon(winer)) {
                    paintWithe(canvas);
                    paintWin(canvas);
                }if(!isWon(winer))
                {
                    displayDialog = true;
                    text.setColor(Color.BLACK);
                    text.setFakeBoldText(true);
                    text.setTextSize(20);
                    canvas.drawText("MEDIEUM", 12, carteTopAnchor / 6, text);
                    paintChrono(canvas);
                    canvas.drawText(""+ time + "", getWidth()-120, 40, text);
                }
            }else if (niveau == 3) {
                if (isWon(winer)) {
                    paintWithe(canvas);
                    paintWin(canvas);
                } if(!isWon(winer))
                {
                    displayDialog = true;
                    text.setColor(Color.BLACK);
                    text.setFakeBoldText(true);
                    text.setTextSize(20);
                    canvas.drawText("HARD", 12, carteTopAnchor / 6, text);
                    paintChrono(canvas);
                    canvas.drawText(""+ time + "", getWidth()-120, 40, text);
                }
            }

            }


    private void nDraw(Canvas canvas) {
        canvas.drawRGB(242, 243, 244);
        paintcarte(canvas);


    }
    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
        initparameters(1);
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceDestroyed");
    }

    /**
     * run (run du thread cr��)
     * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib�re le canvas
     */


    public void run() {
        Canvas c = null;
        while (in) {
            try {
                cv_thread.sleep(300);
                stopChrono();

                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
            }
        }
    }



    // fonction permettant de recuperer les evenements tactiles

    public boolean onTouchEvent(MotionEvent event) {
        Log.i("-> FCT <-", "event.getX: " + event.getX());
        Log.i("-> FCT <-", "event.getY: " + event.getY());
        Log.i("-> FCT <-", "getWidth: " + deux.getWidth());
        float x=event.getX();
        float y=event.getY();
        Log.i("-> FCT <-", "event.getX: " +x);
        Log.i("-> FCT <-", "event.getY: " +y);
        int j= (int) (x/(getWidth()/9));
        int i= (int) ((y/(getWidth()/9))-((y/(getWidth()/9))/(y/100)));
        int jPetitMat= (int) ((x/(getWidth()/9)));
        int iPetitMat= (int)  ((y/(getWidth()/9))-((y/(getWidth()/9))/(y/100+95))-11);
        int jmatbtn= (int) ((x/(getWidth()/3))+1);
        int imatbtn= (int)  (((y/(getWidth()/3))-(y/(getWidth()/3))/(y/100+95+80 ))-3);
        Log.i("-> FCT <-", "i=: " +i);
        Log.i("-> FCT <-", "j=: " +j);
        Log.i("-> FCT <-", "iPetitMat=: " +(iPetitMat));
        Log.i("-> FCT <-", "jPetitMat=: " +jPetitMat);
        Log.i("-> FCT <-", "jmatbtn=: " +(jmatbtn));
        Log.i("-> FCT <-", "imatbtn=: " +(imatbtn));

         CaseChoisiePetiteMat=LaCaseChoisiePetiteMat(iPetitMat,jPetitMat);
         btnSup = btnSup( imatbtn,  jmatbtn);
         Log.i("-> FCT <-", "btnSup" + "=: " +btnSup);

        if(caseSelectionner==true && CaseChoisiePetiteMat!=99){
            mI=tableauCaseSelectionnerGrandeMatrice[0];
            mJ=tableauCaseSelectionnerGrandeMatrice[1];
            Log.i("-> FCT <-", "mi "+mI);
            Log.i("-> FCT <-", "mJ "+mJ);
            Log.i("-> FCT <-", "CaseChoisiePetiteMat "+CaseChoisiePetiteMat);
           // FonctionInsertLibre(CaseChoisiePetiteMat,mI,mJ);
            if (CaseChoisiePetiteMat == 1) {
                matrice[mI][mJ] = CST_un;
            } else if (CaseChoisiePetiteMat == 2) {
                matrice[mI][mJ] = CST_deux;
            } else if (CaseChoisiePetiteMat == 3) {
                matrice[mI][mJ] = CST_trois;
            } else if (CaseChoisiePetiteMat == 4) {
                matrice[mI][mJ] = CST_quatre;
            } else if (CaseChoisiePetiteMat == 5) {
                matrice[mI][mJ] = CST_cinq;
            } else if (CaseChoisiePetiteMat == 6) {
                matrice[mI][mJ] = CST_six;
            } else if (CaseChoisiePetiteMat == 7) {
                matrice[mI][mJ] = CST_sept;
            } else if (CaseChoisiePetiteMat == 8) {
                matrice[mI][mJ] = CST_huite;
            } else if (CaseChoisiePetiteMat == 9) {
                matrice[mI][mJ] = CST_neuf;
            }
        }
        if(caseSelectionner==true && btnSup!=99 ){
            I=tableauCaseSelectionnerGrandeMatrice1[0];
            J=tableauCaseSelectionnerGrandeMatrice1[1];
            Log.i("-> FCT <-", "I "+I);
            Log.i("-> FCT <-", "J "+J);
            Log.i("-> FCT <-", "btnSup "+btnSup);
            if (btnSup==11){
                matrice[I][J]=CST_Vide;
            }
            if (btnSup==12){
                if ( initparameters(niveau)==1){
                    initparameters(1) ;
                }else if(initparameters(niveau)==2){
                    initparameters(2) ;
                }else if (initparameters(niveau)==3){
                    initparameters(3) ;
                }
            }
            }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.i("-> FCT <-", "onTouchEvent: Down ");
                tableauCaseSelectionnerGrandeMatrice=SelectedCaseVert(i,j);
                Log.i("-> FCT <-", "tableauCaseSelectionnerGrandeMatrice i=: " +tableauCaseSelectionnerGrandeMatrice[0]);
                Log.i("-> FCT <-", "tableauCaseSelectionnerGrandeMatrice j=: " +tableauCaseSelectionnerGrandeMatrice[1]);

                tableauCaseSelectionnerGrandeMatrice1=GestionGrandeMatrice( i,  j);
                Log.i("-> FCT <-", "tableauCaseSelectionnerGrandeMatrice1 i=: " +tableauCaseSelectionnerGrandeMatrice1[0]);
                Log.i("-> FCT <-", "tableauCaseSelectionnerGrandeMatrice1 j=: " +tableauCaseSelectionnerGrandeMatrice1[1]);
                break;
            case MotionEvent.ACTION_UP:
                Log.i("-> FCT <-", "onTouchEvent: ACTION_UP ");
                if (btnSup==12){
                if ( initparameters(niveau)==1){
                    initparameters(1) ;
                }else if(initparameters(niveau)==2){
                    initparameters(2) ;
                }else if (initparameters(niveau)==3){
                    initparameters(3) ;

                };
            }
                if (btnSup==13){
                    String winer;
                    mI=tableauCaseSelectionnerGrandeMatrice[0];
                    mJ=tableauCaseSelectionnerGrandeMatrice[1];

                    if (VerificationGrilleSudoku(mI,mJ)){
                        Toast.makeText(mContext, "Bravo,vous avez résolu cette grille sudoku!!.", Toast.LENGTH_LONG).show();


                    }else if (!VerificationGrilleSudoku(mI,mJ)){
                        winer="e";
                        isWon(winer);
                        Toast.makeText(mContext, "Oh non,vous avez mal rempli la grille!!.recommencez avec sur les cases vides", Toast.LENGTH_LONG).show();
                    };
                }

            break;
            case MotionEvent.ACTION_MOVE:
                Log.i("-> FCT <-", "onTouchEvent: ACTION_MOVE ");

                break;
        }
        return true;
    }




}


