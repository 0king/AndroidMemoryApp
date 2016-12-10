package g.m;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.concurrent.RunnableFuture;

import g.m.model.Level;
import g.m.model.Question;
import g.m.utils.Constants;
import g.m.utils.FontManager;
import g.m.utils.PreferenceManager;

public class PhotoActivity extends AppCompatActivity {
	private Level level;
    ImageView view;
    ContentHelper server;
    public int current_level;
    TextView mTextField;
    private CountDownTimer timer_clock;
    boolean isCancelled = false;
    int sound=1 ;

 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

        server = ContentHelper.getInstance();
        view = (ImageView) findViewById(R.id.image);
        current_level=server.getCurrentLevel()-1;

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_photo);
       TextView txtview = (TextView) toolbar.findViewById(R.id.current_level_photo);
        txtview.setText("Case #"+(current_level+1));
        txtview.setTypeface(FontManager.get().getFontDigital());


		/* setting fonts for textViews */
        mTextField = (TextView)findViewById(R.id.timer);

		TextView textInstruction = (TextView) findViewById(R.id.textInstruction);
        mTextField.setTypeface(FontManager.get().getFontDigital());
		textInstruction.setTypeface(FontManager.get().getFontChargen());


        //todo pre fetch data

        loadContent();

	}

	/* calling this function so that if a user presses back button while the timer is running, the user will be taken back and timer stops and QuestionsActivity wont load after timeout*/
	@Override
	public void onBackPressed() {

        //cancelTimer();
        isCancelled = true;
        startActivity(new Intent(PhotoActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
	}


    @Override
    public void onStop() {
        Log.e("MemoryApp","Stopped photo");
        isCancelled = true;
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        isCancelled = false;
        startCountDown();
        //resumeTimer();

    }



    public void loadContent(){

		Log.e("Memory_app","Current Lvel is "+current_level);

        if(current_level==0){
            Glide.with(getApplicationContext())
                    .load(Uri.parse("file:///android_asset/level1.jpg"))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                     .into(new SimpleTarget<GlideDrawable>(400, 300) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            Log.i("Memory_app", "GlideDrawalble = '" + resource + "'");
                            view.setImageDrawable(resource.getCurrent());
                        }
                    });

        }else {

            Glide.with(getApplicationContext())
                    .load(Constants.IMAGE_URL[current_level])
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.i("Memory_app", "Listener onException: " + e.toString());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Log.i("Memory_app", "onResourceReady with resource = " + resource);
                            Log.i("Memory_app", "onResourceReady from memory cache = " + isFromMemoryCache);
                            return false;
                        }
                    })
                    .into(new SimpleTarget<GlideDrawable>(400, 300) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            Log.i("Memory_app", "GlideDrawalble = '" + resource + "'");
                            view.setImageDrawable(resource.getCurrent());
                        }
                    });
        }



       PhotoActivity.this.startCountDown();
       /* this.timer = new TimerStart((Math.min(ContentHelper.getInstance().getTimeLeft(), 10) * 1000), 500);
        this.timer.start();*/
}

    public void startCountDown() {

        this.timer_clock = new CountDownTimer((Math.min(ContentHelper.getInstance().getTimeLeft(), 10) * 1000), 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isCancelled == false) {
                    long timeleft = (millisUntilFinished / 1000) + 1;
                    mTextField.setText("" + timeleft);
                    ContentHelper.getInstance().setTimeLeft(timeleft);
                    ContentHelper.getInstance().saveLevelData();
                }else{
                    cancel();
                }

            }

            @Override
            public void onFinish() {

                ContentHelper.getInstance().setTimeLeft(0);
                ContentHelper.getInstance().saveLevelData();
                finish();
                Intent i = new Intent(PhotoActivity.this, QuestionsActivity.class);
                startActivity(i);

            }
        }.start();
    }

    public void cancelTimer() {
        if (this.timer_clock != null) {
            this.timer_clock.cancel();
        }
    }
/*
    public void resumeTimer() {
        startCountDown(ContentHelper.getInstance().getTimeLeft());
    }
*/
}
