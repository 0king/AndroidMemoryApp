package g.m;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import g.m.R;
import g.m.utils.PreferenceManager;
import g.m.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    String now_playing, earned;
    ContentHelper server;

    private static SplashActivity instance;

    public static SplashActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        instance = this;

        //    PreferenceManager.get().init(this);
      //   int  already_cached=PreferenceManager.get().getInt(PreferenceManager.PREF_ALREADY_CACHED, 0);

       //  Log.e("Memory_app","Cached Value "+already_cached+"network info "+ Utils.isNetworkAvailable(this));

         if( Utils.isNetworkAvailable(this)) {
             server = ContentHelper.getInstance();
             server.loadJsonFromServer(getApplicationContext());

             new PrefetchData().execute();
       //  PreferenceManager.get().putInt(PreferenceManager.PREF_ALREADY_CACHED, 1);

         }else {
         Toast.makeText(this, "Connect to Internet", Toast.LENGTH_SHORT).show();
         }


    }

    public void onDataLoaded (Context context){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);

        // close this activity
        finish();

    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls         

        }

        @Override

        protected Void doInBackground(Void... arg0) {


            server.loadAllImagesFromServer(getApplicationContext());
            return null;


        }


        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity

        }



    }
}