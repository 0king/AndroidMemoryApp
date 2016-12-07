package g.m;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.PorterDuff;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;



import g.m.R;
import android.widget.RelativeLayout;
import android.widget.TextView;


import g.m.utils.FontManager;
import g.m.utils.PreferenceManager;
import g.m.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    String now_playing, earned;
    ContentHelper server;
    ProgressBar progress,progress_horizontal;
    boolean image_loaded,data_loaded=false;


    private static SplashActivity instance;

    public static SplashActivity getInstance() {
        return instance;
    }

    // skipping package and imports
    public class ShowDialog implements AlertDialog.OnClickListener {

        void createAndShowDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);//Context parameter
            builder.setNeutralButton("Try Again",ShowDialog.this );
            builder.setTitle("No Internet Connection");
            builder.setMessage("Connect to Internet and try again");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();



        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if( Utils.isNetworkAvailable(SplashActivity.this)) {

                progress.setVisibility(View.VISIBLE);
                server = ContentHelper.getInstance();


                String jsonString=PreferenceManager.get().getString(PreferenceManager.PREF_JSON_STRING, "0");
                if(BuildConfig.FLAVOR.equals(jsonString)) {
                    server.loadJsonFromServer(getApplicationContext());
                }else {
                    server.loadJsonFromPreferences(getApplicationContext());
                }

              //  new PrefetchData().execute();
                new getAllImages().execute();

            }else {
                createAndShowDialog();
            }
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FontManager.get().initFonts(this);

        /* setting font of "loading" textview */
        //Typeface chargen = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/chargen.ttf");

        TextView loading = (TextView) findViewById(R.id.loadingText);
        loading.setTypeface(FontManager.get().getFontChargen());

        /* setting font of "detective memory" texttviews */
        TextView detective = (TextView) findViewById(R.id.detective);
        TextView memory = (TextView) findViewById(R.id.memory);
        //Typeface spac = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/spac.ttf");
        detective.setTypeface(FontManager.get().getFontEasports());
        memory.setTypeface(FontManager.get().getFontEasports());

        instance = this;

        PreferenceManager.get().init(this);
        progress = (ProgressBar)findViewById(R.id.progress_spinner);

        /* changing color of progress bar */
        progress.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);//android.graphics.PorterDuff.Mode.SRC_IN


        //   int  already_cached=PreferenceManager.get().getInt(PreferenceManager.PREF_ALREADY_CACHED, 0);

        progress_horizontal = (ProgressBar)findViewById(R.id.progress_horizontal);
      //  progress_horizontal.getProgressDrawable().setColorFilter(
        //        Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
       // progress_horizontal.setProgressTintList(ColorStateList.valueOf(Color.RED))
      //   int  already_cached=PreferenceManager.get().getInt(PreferenceManager.PREF_ALREADY_CACHED, 0);


       //  Log.e("Memory_app","Cached Value "+already_cached+"network info "+ Utils.isNetworkAvailable(this));

<<<<<<< HEAD
         if(Utils.isNetworkAvailable(this)) {
             /* if network is available */

             /* set visibility to true after disabled when network unavailable */
             loading.setVisibility(View.VISIBLE);
             progress_horizontal.setVisibility(View.VISIBLE);

             server = ContentHelper.getInstance();

             String jsonString=PreferenceManager.get().getString(PreferenceManager.PREF_JSON_STRING, "");
             Log.e("Memory_App","Json string is "+jsonString);
             if(BuildConfig.FLAVOR.equals(jsonString)) {
                 server.loadJsonFromServer(getApplicationContext());
             }else {
                 server.loadJsonFromPreferences(getApplicationContext());
             }
=======
         if( Utils.isNetworkAvailable(this)) {

             server = ContentHelper.getInstance();
>>>>>>> kushroxx/master

             String jsonString=PreferenceManager.get().getString(PreferenceManager.PREF_JSON_STRING, "");
             Log.e("Memory_App","Json string is "+jsonString);
             if(BuildConfig.FLAVOR.equals(jsonString)) {
                 server.loadJsonFromServer(getApplicationContext());
             }else {
                 server.loadJsonFromPreferences(getApplicationContext());
             }

           //  new PrefetchData().execute();
             new getAllImages().execute();
       //  PreferenceManager.get().putInt(PreferenceManager.PREF_ALREADY_CACHED, 1);

         }else {
<<<<<<< HEAD
             /* if newtwork is not available */
             //ImageView img_view = (ImageView)findViewById(R.id.imgLogo); //i've removed the image
             RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
             relativeLayout.setAlpha(0.5f);

             loading.setVisibility(View.INVISIBLE);
             progress_horizontal.setVisibility(View.INVISIBLE);
=======
             //ImageView img_view = (ImageView)findViewById(R.id.imgLogo); //i've removed the image
             RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
             relativeLayout.setAlpha(0.5f); //todo check it @kush
>>>>>>> kushroxx/master

            new ShowDialog().createAndShowDialog();
         }


    }

    public void onDataLoaded (Context context){
<<<<<<< HEAD
=======

        data_loaded = true;
     //   if(image_loaded && data_loaded) {

            ContentHelper.getInstance().loadLevelData();
            progress.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);

            // close this activity
            finish();
       // }

    }

    public void onImageLoaded (Context context){
>>>>>>> kushroxx/master

        image_loaded = true;
        if(image_loaded && data_loaded) {
            ContentHelper.getInstance().loadLevelData();
            progress.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);

            // close this activity
            finish();
        }

    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
           server.loadFirstImageFromServer(getApplicationContext());
            return null;
        }
    }

    private class getAllImages extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            server.loadAllImagesFromServer(getApplicationContext());
            return null;
        }
    }
}