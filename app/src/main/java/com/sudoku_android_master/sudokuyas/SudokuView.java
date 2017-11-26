package com.sudoku_android_master.sudokuyas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Color;

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
    // images pour la gestion de la grille
    private Bitmap Gst_vide;
    private Bitmap Gst_un;
    private Bitmap Gst_deux;
    private Bitmap Gst_trois;
    private Bitmap Gst_quatre;
    private Bitmap Gst_cinq;
    private Bitmap Gst_six;
    private Bitmap Gst_sept;
    private Bitmap Gst_huite;
    private Bitmap Gst_neuf;
   /* private Button effacer;
    private Button ecrire;
    private Button renitialiser;
    private Bitmap Gst_effacer;
    private Bitmap Gst_ecrire;
    private Bitmap Gst_renitialiser;*/

    // Declaration des objets Ressources et Context permettant d'accéder aux ressources de l'application et de les charger
    private Resources mRes;
    private Context mContext;

    // tableau modelisant la carte du jeu
    int[][] matrice;
    // ancres pour pouvoir centrer la carte du jeu
    int carteTopAnchor;                   // coordonnées en Y du point d'ancrage de notre carte
    int carteLeftAnchor;                  // coordonnées en X du point d'ancrage de notre carte
    // taille de la carte
    static final int carteWidth = 9;
    static final int carteHeight = 9;
    static final int MatWidth = 3;
    static final int MatHeight = 3;
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
   /* static final int  G_effacer=10;
    static final int  G_ecrire=11;
    static final int  G_renetialiser=12;
    static final int G_effacer = 0;
    static final int G_ecrire = 10;
    static final int G_renitialiser = 20;*/


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
            {CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_Vide, CST_quatre, CST_Vide,CST_un},
    };

    //tableau de gestion de la grille

    int[][] mat_gestion = {

            {G_un, G_deux, G_trois},
            {G_quatre, G_cinq, G_six},
            { G_sept,G_huite, G_neuf}
    };

    //thread utiliser pour animer les zones de depot des diamants
    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;

    Paint paint;


    /**
     * The constructor called from the main JetBoy activity
     *
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
         deux = BitmapFactory.decodeResource(mRes, R.drawable.deux);
         trois		= BitmapFactory.decodeResource(mRes, R.drawable.trois);
         quatre 	= BitmapFactory.decodeResource(mRes, R.drawable.quatre);
         cinq = BitmapFactory.decodeResource(mRes, R.drawable.cinq);
         six = BitmapFactory.decodeResource(mRes, R.drawable.six);
         sept 	= BitmapFactory.decodeResource(mRes, R.drawable.sept);
         huite  =BitmapFactory.decodeResource(mRes, R.drawable.huite);
         neuf   =BitmapFactory.decodeResource(mRes, R.drawable.neuf);
         vide		= BitmapFactory.decodeResource(mRes, R.drawable.vide);
         Gst_un 		= BitmapFactory.decodeResource(mRes, R.drawable.un  );
         Gst_deux = BitmapFactory.decodeResource(mRes, R.drawable.deux);
         Gst_trois		= BitmapFactory.decodeResource(mRes, R.drawable.trois);
         Gst_quatre 	= BitmapFactory.decodeResource(mRes, R.drawable.quatre);
         Gst_cinq = BitmapFactory.decodeResource(mRes, R.drawable.cinq);
         Gst_six = BitmapFactory.decodeResource(mRes, R.drawable.six);
         Gst_sept 	= BitmapFactory.decodeResource(mRes, R.drawable.sept);
         Gst_huite =BitmapFactory.decodeResource(mRes, R.drawable.huite);
         Gst_neuf   =BitmapFactory.decodeResource(mRes, R.drawable.neuf);
         /*Gst_effacer = BitmapFactory.decodeResource(mRes, R.drawable.vide);
         Gst_ecrire  = BitmapFactory.decodeResource(mRes, R.drawable.vide);
         Gst_renitialiser		= BitmapFactory.decodeResource(mRes, R.drawable.vide);*/
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

        Paint paint2 = new Paint();

        paint2.setColor(Color.BLACK);
      

        canvas.save();

        int tailleCarre = un.getHeight();
        for (int i = 0; i < carteHeight; i++) {
            for (int j = 0; j < carteWidth; j++) {

                switch (matrice[i][j]) {
                    case CST_Vide:
                        canvas.drawBitmap(vide, j *80, 145+ i*80 , null);
                        break;
                    case CST_un:
                        canvas.drawBitmap(un, j *80, 145+ i*80 , null);
                        break;
                    case CST_deux:
                        canvas.drawBitmap(deux, j *80, 145+ i*80 , null);
                        canvas.drawLine(0, 0, x, 0,paint2);
                        break;

                    case CST_trois:
                        canvas.drawBitmap(trois, j *80, 145+ i*80 , null);
                        break;
                    case CST_quatre:
                        canvas.drawBitmap(quatre, j *80, 145+ i*80 , null);
                        break;
                    case CST_cinq:
                        canvas.drawBitmap(cinq, j *80, 145+ i*80 , null);
                        canvas.drawLine(0, 0, x, 0,paint2);                 break;
                    case CST_six:
                        canvas.drawBitmap(six, j *80, 145+ i*80 , null);
                        break;
                    case CST_sept:
                        canvas.drawBitmap(sept, j *80, 145+ i*80 , null);
                        break;
                    case CST_huite:
                        canvas.drawBitmap(huite, j *80, 145+ i*80 , null);
                        break;
                    case CST_neuf:
                        canvas.drawBitmap(neuf, j *80, 145+ i*80 , null);
                        break;

                }

            }
        }

        for (int i = 0; i < MatHeight; i++) {
            for (int j = 0; j < MatWidth; j++) {

                switch (mat_gestion [i][j]) {
                    case G_un:
                        canvas.drawBitmap(un, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_deux:
                        canvas.drawBitmap(deux, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_trois:
                        canvas.drawBitmap(trois, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_quatre:
                        canvas.drawBitmap(quatre, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_cinq:
                        canvas.drawBitmap(cinq, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_six:
                        canvas.drawBitmap(six, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_sept:
                        canvas.drawBitmap(sept, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_huite:
                        canvas.drawBitmap(huite, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;
                    case G_neuf:
                        canvas.drawBitmap(neuf, j * getWidth() / 9, tailleCarre *  11+ 40 + un.getHeight() + (i * getWidth() / 9), null);
                        break;

                }

                paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(6);
                paint.setStrokeWidth(1);
                paint.setColor(Color.GRAY);
            }




    }}

    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {

        canvas.drawRGB(242, 243, 244);
        paintcarte(canvas);
        //canvas.drawRect(50, 50, 80, 80, paint);
        //drawRect(left, top, right, bottom, paint)
        }



    /*fonction permettant de recuperer les coordonnés d'une case
    public void  getinfo(float x,float y){

        float leftclick=    x-carteLeftAnchor;
        float topclick=    y-carteTopAnchor;

        if(leftclick >0 && topclick>0) {
            float xx=leftclick/carteTileSize;
            float yy=topclick /carteTileSize;
            if (xx <carteWidth && yy < carteWidth)
               og.i("",xx.intValue() + ":"+yy.intValue());
            else
                Log.i("","vous avez cliqué à l'exterieure du carré");

        }

    }}*/

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
        if (event.getY() < 50) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_UP, null);

        } else if (event.getY() > getHeight() - 50) {
            if (event.getX() > getWidth() - 50) {
                onKeyDown(KeyEvent.KEYCODE_0, null);
            } else {
                onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN, null);
            }
        } else if (event.getX() < 50) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
        } else if (event.getX() > getWidth() - 50) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
        }

        return super.onTouchEvent(event);
    }
}