package g.m;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import g.m.model.DataWrapper;
import g.m.model.Level;
import g.m.utils.Constants;
import g.m.utils.PreferenceManager;
import g.m.utils.Utils;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import static g.m.AppController.TAG;

/**
 * Created by kushroxx on 25/11/16.
 */

public class ContentHelper {

    private DataWrapper dataWrapper;
    Level level;
    private int coinsCount;
    private int correctAnswers;
    private Fragment currentFragment;
    private int currentFragmentType;
    private int currentLevel;
    private int currentQuestion;
    private int[] questionsOrder;
    private int timeLeft;

    private static ContentHelper instance;

    private ContentHelper() {
        // nothing to do this time
    }

    /**
     * The Static initializer constructs the instance at class
     * loading time; this is to simulate a more involved
     * construction process (it it were really simple, you'd just
     * use an initializer)
     */
    static {
        instance = new ContentHelper();
    }

    /** Static 'instance' method */
    public static ContentHelper getInstance() {
        return instance;
    }


    public void loadAllImagesFromServer(final Context context) {

        for  (int i=0;i<Constants.total_levels;i++) {
           // Log.e("MemoryApp","Url is "+Constants.IMAGE_URL[i]);
            Glide.with(context)
                    .load(Constants.IMAGE_URL[i])
                    .override(600, 200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(-1,-1);
        }


    }

    public void loadJsonFromServer() {

        String tag_json_obj = "json_obj_req";


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.JSON_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("MemoryApp", response.toString());
                        String jsonString = response.toString();
                        if (!BuildConfig.FLAVOR.equals(jsonString)) {
                            ContentHelper.this.dataWrapper = (DataWrapper) new GsonBuilder().create().fromJson(jsonString, DataWrapper.class);

                            Log.e("MemoryApp","Total size is "+ContentHelper.this.dataWrapper.getLevels().size());
                            // initiquestion();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    public Level getLevel(int index) {
        if (index < this.dataWrapper.getLevels().size()) {
            return (Level) this.dataWrapper.getLevels().get(index);
        }
        return null;
    }


    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void loadLevelData() {
        this.currentLevel=1;

        this.questionsOrder = Utils.generateShuffledListOfIds(((Level) this.dataWrapper.getLevels().get(this.currentLevel - 1)).getQuestions().size());
        /*
        String levelData = PreferenceManager.get().getString(PreferenceManager.PREF_LEVEL_DATA, BuildConfig.FLAVOR);
        String qOrder = PreferenceManager.get().getString(PreferenceManager.PREF_QUESTIONS_ORDER, BuildConfig.FLAVOR);
        this.currentLevel = PreferenceManager.get().getInt(PreferenceManager.PREF_CURRENT_LEVEL, 1);
        this.coinsCount = PreferenceManager.get().getInt(PreferenceManager.PREF_COINS_COUNT, 100);
        if (BuildConfig.FLAVOR.equals(levelData)) {
            this.timeLeft = 10;
            this.currentQuestion = 1;
            this.correctAnswers = 0;
        } else {
            String[] levelDetails = levelData.split("\\|");
            this.timeLeft = Integer.parseInt(levelDetails[0]);
            this.currentQuestion = Integer.parseInt(levelDetails[1]);
            this.correctAnswers = Integer.parseInt(levelDetails[2]);
        }
        if (BuildConfig.FLAVOR.equals(qOrder)) {
            this.questionsOrder = Utils.generateShuffledListOfIds(((Level) this.dataWrapper.getLevels().get(this.currentLevel - 1)).getQuestions().size());
            return;
        }
        String[] qStrings = qOrder.split("\\|");
        this.questionsOrder = new int[qStrings.length];
        for (int i = 0; i < qStrings.length; i++) {
            this.questionsOrder[i] = Integer.parseInt(qStrings[i]);
        }*/
    }

    public void saveLevelData() {
        PreferenceManager.get().putString(PreferenceManager.PREF_LEVEL_DATA, this.timeLeft + "|" + this.currentQuestion + "|" + this.correctAnswers);
        PreferenceManager.get().putInt(PreferenceManager.PREF_CURRENT_LEVEL, this.currentLevel);
        PreferenceManager.get().putInt(PreferenceManager.PREF_COINS_COUNT, this.coinsCount);
        String qOrder = BuildConfig.FLAVOR + this.questionsOrder[0];
        for (int i = 1; i < this.questionsOrder.length; i++) {
            qOrder = qOrder + "|" + this.questionsOrder[i];
        }
        PreferenceManager.get().putString(PreferenceManager.PREF_QUESTIONS_ORDER, qOrder);
    }

    public void resetForNextLevel() {
        this.currentLevel++;
        this.timeLeft = 10;
        this.currentQuestion = 1;
        this.correctAnswers = 0;
        if (this.dataWrapper.getLevels().size() < this.currentLevel - 1) {
           this.questionsOrder = Utils.generateShuffledListOfIds(((Level) this.dataWrapper.getLevels().get(this.currentLevel - 1)).getQuestions().size());
        }
    }

    public int[] getQuestionsOrder() {
        return this.questionsOrder;
    }
}