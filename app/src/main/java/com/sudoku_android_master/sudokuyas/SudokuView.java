package com.sudoku_android_master.sudokuyas;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    private Bitmap backgrd;
    // Declaration des objets Ressources et Context permettant d'accéder aux ressources de l'application et de les charger
    private Resources mRes;
    private Context mContext;
    int compteur=0;
    int CaseChoisiePetiteMat;
    int OrigineI, OrigineY, mI,mJ;
    boolean caseSelectionner=false;
    int[] tableauCaseSelectionnerGrandeMatrice= new int[2];
    // tableau modelisant la carte du jeu
    int[][] matrice;
    // ancres pour pouvoir centrer la carte du jeu
    int carteTopAnchor;                   // coordonnées en Y du point d'ancrage de notre carte
    int carteLeftAnchor;                  // coordonnées en X du point d'ancrage de notre carte
    // taille de la carte
    static final int carteWidth = 9;
    static final int carteHeight = 9;
    static final int MatWidth = 9;
    static final int MatHeight = 1;
    // taille de la celelule de vecteur
    static final int carteTileSize = 20;
    // constante modelisant les differentes types de cases
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
    // constante modelisant les differentes types de cases de gestion celelules
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
    // tableau de reference du terrain
    int[][] niveau1 = {
            {CST_un, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_Vide, CST_deux, CST_un, CST_Vide, CST_Vide, CST_Vide, CST_trois, CST_Vide, CST_six},
            {CST_huite, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_Vide, CST_Vide, CST_six, CST_trois, CST_un, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_deux, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_trois, CST_Vide},
            {CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_cinq, CST_un, CST_Vide, CST_cinq},
            {CST_neuf, CST_Vide, CST_trois, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_trois, CST_Vide, CST_Vide, CST_Vide, CST_Vide},
            {CST_sept, CST_Vide, CST_huite, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_un, CST_Vide},
    };
    //tableau de gestion de la grille
    int[][] mat_gestion = {
            {G_un, G_deux, G_trois,G_quatre, G_cinq, G_six,G_sept,G_huite, G_neuf}
    };
    int[][] mat_btn = {
            {G_effacer, G_renitialiser, G_verifier}
    };

    //thread utiliser pour animer les zones de depot des diamants
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
        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);
        // chargement des images
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
        backgrd = BitmapFactory.decodeResource(mRes, R.drawable.gris);

        // initialisation des parmametres du jeu
        initparameters();
        // creation du thread
        cv_thread = new Thread(this);
        // prise de focus pour gestion des touches
        setFocusable(true);
    }
    // chargement du niveau a partir du tableau de reference du niveau
    private void loadlevel() {
        for (int i = 0; i < carteHeight; i++) {
            for (int j = 0; j < carteWidth; j++) {
                matrice[j][i] = niveau1[j][i];
            }
        }
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
            } else if (matrice[i][j] == matrice[OrigineI][OrigineY]) {
                matrice[i][j] = CST_Vide;
                caseSelectionner = false;

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

    public void btnclic(int i, int j) {
        int niveau=1;
        if (i == 0 && j == 1) {


        } else if (i == 0 && j == 1) {
            if (niveau==1){

            }else if (niveau==2) {

            }  else if(niveau==3){

            } else {

            }

        } else if (i == 0 && j == 2) {

        }
        else {

        }

    }

    // initialisation du jeu
    public void initparameters() {
        matrice = new int[carteHeight][carteWidth];
        loadlevel(); // charger le niveau souhaite
        carteTopAnchor = (getHeight() - carteHeight * carteTileSize) / 2;
        carteLeftAnchor = (getWidth() - carteWidth * carteTileSize) / 2;
        if ((cv_thread != null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }
    }
// dessin de la carte du jeu
    private void paintcarte(Canvas canvas) {
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

    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {
        canvas.drawRGB(242, 243, 244);
        paintcarte(canvas);
         //canvas = new Canvas(backgrd.copy(Bitmap.Config.ARGB_8888, true));
        }

    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
        initparameters();
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
                cv_thread.sleep(40);
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                //Log.e("-> RUN <-", "PB DANS RUN");
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

        int jmatbtn= (int) ((x/(getWidth()/3)));
        int imatbtn= (int)  (((y/(getWidth()/3))-(y/(getWidth()/3))/(y/100+95+80 ))-4);
        Log.i("-> FCT <-", "i=: " +i);
        Log.i("-> FCT <-", "j=: " +j);
        Log.i("-> FCT <-", "iPetitMat=: " +(iPetitMat));
        Log.i("-> FCT <-", "jPetitMat=: " +jPetitMat);
        Log.i("-> FCT <-", "jmatbtn=: " +(jmatbtn));
        Log.i("-> FCT <-", "imatbtn=: " +(imatbtn));



        CaseChoisiePetiteMat=LaCaseChoisiePetiteMat(iPetitMat,jPetitMat);
      //  Log.i("-> FCT <-", "CaseChoisiePetiteMat" + "=: " +CaseChoisiePetiteMat);

        if(caseSelectionner==true && CaseChoisiePetiteMat!=99 ){
            mI=tableauCaseSelectionnerGrandeMatrice[0];
            mJ=tableauCaseSelectionnerGrandeMatrice[1];
            Log.i("-> FCT <-", "mi "+mI);
            Log.i("-> FCT <-", "mJ "+mJ);
            Log.i("-> FCT <-", "CaseChoisiePetiteMat "+CaseChoisiePetiteMat);
            if (CaseChoisiePetiteMat==1){
                matrice[mI][mJ]=CST_un;
            }else if (CaseChoisiePetiteMat==2){
                matrice[mI][mJ]=CST_deux;
            }else if (CaseChoisiePetiteMat==3){
                matrice[mI][mJ]=CST_trois;
            }else if (CaseChoisiePetiteMat==4){
                matrice[mI][mJ]=CST_quatre;
            }else if (CaseChoisiePetiteMat==5){
                matrice[mI][mJ]=CST_cinq;
            }else if (CaseChoisiePetiteMat==6){
                matrice[mI][mJ]=CST_six;
            }else if (CaseChoisiePetiteMat==7){
                matrice[mI][mJ]=CST_sept;
            }else if (CaseChoisiePetiteMat==8){
                matrice[mI][mJ]=CST_huite;
            }
            else if (CaseChoisiePetiteMat==9){
                matrice[mI][mJ]=CST_neuf;
            }
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.i("-> FCT <-", "onTouchEvent: Down ");
                tableauCaseSelectionnerGrandeMatrice=SelectedCaseVert(i,j);
                Log.i("-> FCT <-", "tableauCaseSelectionnerGrandeMatrice i=: " +tableauCaseSelectionnerGrandeMatrice[0]);
                Log.i("-> FCT <-", "tableauCaseSelectionnerGrandeMatrice j=: " +tableauCaseSelectionnerGrandeMatrice[1]);
                break;
            case MotionEvent.ACTION_UP:
                Log.i("-> FCT <-", "onTouchEvent: ACTION_UP ");

                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("-> FCT <-", "onTouchEvent: ACTION_MOVE ");

                break;
        }
        return true;
    }



public boolean checkSudoku( int[][] matrice){
		return (checkHorizontal(matrice) || checkVertical(matrice) || checkRegions(matrice));
	}




private boolean checkHorizontal(int[][] matrice) {
		for( int y = 0 ; y < 9 ; y++ ){
			for( int xPos = 0 ; xPos < 9 ; xPos++ ){

				if( matrice[xPos][y] == 0 ){
					return false;
				}
				for( int x = xPos + 1 ; x < 9 ; x++ ){
					if( matrice[xPos][y] == matrice[x][y] || matrice[x][y] == 0 ){
						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean checkVertical(int[][] matrice) {
		for( int x = 0 ; x < 9 ; x++ ){
			for( int yPos = 0 ; yPos < 9 ; yPos++ ){

				if( matrice[x][yPos] == 0 ){
					return false;
				}
				for( int y = yPos + 1 ; y < 9 ; y++ ){
					if( matrice[x][yPos] == matrice[x][y] || matrice[x][y] == 0 ){
						return false;
					}
				}
			}
		}

		return true;
	}


private boolean checkRegions(int[][] matrice) {
		for( int xRegion = 0; xRegion < 3; xRegion++ ){
			for( int yRegion = 0; yRegion < 3 ; yRegion++ ){
				if( !checkRegion(matrice, xRegion, yRegion) ){
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkRegion(int[][] matrice , int xRegion , int yRegion){
		for( int xPos = xRegion * 3; xPos < xRegion * 3 + 3 ; xPos++ ){
			for( int yPos = yRegion * 3 ; yPos < yRegion * 3 + 3 ; yPos++ ){
				for( int x = xPos ; x < xRegion * 3 + 3 ; x++ ){
					for( int y = yPos ; y < yRegion * 3 + 3 ; y++ ){
						if( (( x != xPos || y != yPos) && matrice[xPos][yPos] == matrice[x][y] ) || matrice[x][y] == 0 ){
							return false;
						}
					}
				}
			}
		}
		return true;
	}



	private boolean checkConflict( int[][] matrice , int currentPos , final int number){
		int xPos = currentPos % 9;
		int yPos = currentPos / 9;

		if( checkHorizontalConflict(matrice, xPos, yPos, number) || checkVerticalConflict(matrice, xPos, yPos, number) || checkRegionConflict(matrice, xPos, yPos, number) ){
			return true;
		}

		return false;
	}

	/**
	 * Return true if there is a conflict
	 * @param matrice
	 * @param xPos
	 * @param yPos
	 * @param number
	 * @return
	 */
private boolean checkHorizontalConflict( final int[][] matrice , final int xPos , final int yPos , final int number ){
    for( int x = xPos - 1; x >= 0 ; x-- ){
        if( number == matrice[x][yPos]){
            return true;
        }
    }

    return false;
}

    private boolean checkVerticalConflict( final int[][] matrice , final int xPos , final int yPos , final int number ){
        for( int y = yPos - 1; y >= 0 ; y-- ){
            if( number == matrice [xPos][y] ){
                return true;
            }
        }

        return false;
    }

    private boolean checkRegionConflict( final int[][] matrice , final int xPos , final int yPos , final int number ){
        int xRegion = xPos / 3;
        int yRegion = yPos / 3;

        for( int x = xRegion * 3 ; x < xRegion * 3 + 3 ; x++ ){
            for( int y = yRegion * 3 ; y < yRegion * 3 + 3 ; y++ ){
                if( ( x != xPos || y != yPos ) && number == matrice[x][y] ){
                    return true;
                }
            }
        }

        return false;
    }

}