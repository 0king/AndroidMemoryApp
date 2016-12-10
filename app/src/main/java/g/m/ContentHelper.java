package g.m;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import g.m.model.DataWrapper;
import g.m.model.Level;
import g.m.model.Question;
import g.m.utils.Constants;
import g.m.utils.PreferenceManager;
import g.m.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.GsonBuilder;



import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static g.m.AppController.TAG;

/**
 * Created by kushroxx on 25/11/16.
 */

public class ContentHelper {

    private DataWrapper dataWrapper;
    Level level;
    private int coinsCount;
    private int coinsEarned;
    private int correctAnswers;
    public int currentLevel;
    private int currentQuestion;
    private int[] questionsOrder;
    private long timeLeft;
    long request_time;
    List<Question> questions;

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


    public void loadFirstImageFromServer(final Context context) {

        Log.e("MemoryApp","Url is "+Constants.IMAGE_URL[0]);
            FutureTarget<File> future=Glide.with(context)
                    .load(Constants.IMAGE_URL[0])
                    .downloadOnly(400, 300);

            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("MemoryApp","Image Loaded is "+Constants.IMAGE_URL[0]);


        SplashActivity.getInstance().onImageLoaded(context);


    }


    public void loadAllImagesFromServer(final Context context) {

        for  (int i=1;i<Constants.total_levels;i++) {
           Log.e("MemoryApp","Url is "+Constants.IMAGE_URL[i]);
            FutureTarget<File> future=Glide.with(context)
                    .load(Constants.IMAGE_URL[i])
                    .downloadOnly(400, 300);

            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("MemoryApp","Image Loaded is "+Constants.IMAGE_URL[i]);
        }

    }

    public void loadJsonFromServer(final Context context) {

        // code request code here
         Request request = new Request.Builder()
                    .url(Constants.JSON_URL)
                    .build();

        request_time= System.currentTimeMillis();

        final ProgressListener progressListener = new ProgressListener() {
            @Override public void update(long bytesRead, long contentLength, boolean done) {
                System.out.println(bytesRead);
                System.out.println(contentLength);
                System.out.println(done);
                long content_done = (100 * bytesRead) / contentLength;
                SplashActivity.getInstance().progress_horizontal.setProgress((int)content_done);
                System.out.format("%d%% done\n",content_done );
            }
        };

        OkHttpClient client;
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("MemoryApp","Time taken is "+(System.currentTimeMillis()-request_time));
                //Log.e("MemoryApp", response.body().string());
                //  Log.e("MemoryApp", "Content "+response.body().string());
                String jsonString = response.body().string();
                if (!BuildConfig.FLAVOR.equals(jsonString)) {
                    PreferenceManager.get().putString(PreferenceManager.PREF_JSON_STRING, jsonString);
                    ContentHelper.this.dataWrapper = (DataWrapper) new GsonBuilder().create().fromJson(jsonString, DataWrapper.class);

                    Log.e("MemoryApp","Total size is "+ContentHelper.this.dataWrapper.getLevels().size());
                    SplashActivity.getInstance().onDataLoaded(context);
                }

            }


        });

    }

    public void loadJsonFromPreferences(final Context context) {
        String jsonString=PreferenceManager.get().getString(PreferenceManager.PREF_JSON_STRING, "0");

        ContentHelper.this.dataWrapper = (DataWrapper) new GsonBuilder().create().fromJson(jsonString, DataWrapper.class);

        Log.e("MemoryApp","Total size is "+ContentHelper.this.dataWrapper.getLevels().size());
        SplashActivity.getInstance().progress_horizontal.setProgress(100);
        SplashActivity.getInstance().onDataLoaded(context);



    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override public long contentLength(){
            return responseBody.contentLength();
        }

        @Override public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }


    public Level getLevel(int index) {
        if (index < this.dataWrapper.getLevels().size()) {
            return (Level) this.dataWrapper.getLevels().get(index);
        }
        return null;
    }

    public void loadData(){

        this.level= getLevel(this.currentLevel);
        Log.e("MemoryApp","Current phpto level"+this.level);
        List<Question> questions = this.level.getQuestions();
        //Log.e("MemoryApp","Total questions in level is "+questions.size()+"and question is "+questions.get(1).getQuestion());
        setQuestions(questions);
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void loadLevelData() {

        this.currentLevel = PreferenceManager.get().getInt(PreferenceManager.PREF_CURRENT_LEVEL, 1);
        this.questionsOrder = Utils.generateShuffledListOfIds(((Level) this.dataWrapper.getLevels().get(this.currentLevel)).getQuestions().size());
        String levelData = PreferenceManager.get().getString(PreferenceManager.PREF_LEVEL_DATA, BuildConfig.FLAVOR);
        String qOrder = PreferenceManager.get().getString(PreferenceManager.PREF_QUESTIONS_ORDER, BuildConfig.FLAVOR);

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
        }
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
        this.correctAnswers = 0;
        this.currentQuestion = 1;;
        if (this.dataWrapper.getLevels().size() < this.currentLevel - 1) {
           this.questionsOrder = Utils.generateShuffledListOfIds(((Level) this.dataWrapper.getLevels().get(this.currentLevel - 1)).getQuestions().size());
        }

    }

    public void resetGame() {
        this.currentLevel =1;
        this.timeLeft = 10;
        this.coinsCount=100;
        this.currentQuestion = 1;
        this.correctAnswers = 0;

        if (this.dataWrapper.getLevels().size() < this.currentLevel - 1) {
            this.questionsOrder = Utils.generateShuffledListOfIds(((Level) this.dataWrapper.getLevels().get(this.currentLevel - 1)).getQuestions().size());
        }

        loadData();

        MainActivity.setCoinsCount();

    }

    public int[] getQuestionsOrder() {
        return this.questionsOrder;
    }

    public void setCurrentQuestion(int position) {
        this.currentQuestion = position;
    }

    public int getCurrentQuestion() {
        return this.currentQuestion;
    }

    public int getCurrentCoins() {
        return this.coinsCount;
    }

    public void setCurrentCoins(int coins) {
         this.coinsCount = coins;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public long getTimeLeft() {
        return this.timeLeft;
    }

    public void setQuestions(List<Question> ques){

        this.questions =ques;
    }
    public List<Question> getQuestion(){

        return this.questions;
    }

    public void setCorrectAnswers(int answers){

        this.correctAnswers =answers;
    }
    public int getCorrectAnswers(){

        return this.correctAnswers;
    }


}