package g.m;

import android.content.Intent;
import android.os.Bundle;
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

import g.m.model.Level;
import g.m.model.Question;
import g.m.utils.Constants;

public class PhotoActivity extends AppCompatActivity {
	private Level level;
    ImageView view;
    ContentHelper server;
    public int current_level;
	//screen timer
	private static int TIME_OUT = 1000;
    public Handler photoHandler = new Handler();

    public Runnable photoTimer =   new Runnable() {

        @Override
        public void run() {
            // This method will be executed once the timer is over
            Intent i = new Intent(PhotoActivity.this, QuestionsActivity.class);
            startActivity(i);

            //close this activity
            finish();
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

        server = ContentHelper.getInstance();
        view = (ImageView) findViewById(R.id.image);
        current_level=server.getCurrentLevel()-1;

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_photo);
        TextView txtview = (TextView) toolbar.findViewById(R.id.current_level_photo);
        txtview.setText("Level :"+current_level);

        //todo pre fetch data

        loadContent();

        photoHandler.postDelayed(photoTimer, TIME_OUT);

	}

	/* calling this function so that if a user presses back button while the timer is running, the user will be taken back and timer stops and QuestionsActivity wont load after timeout*/
	@Override
	public void onBackPressed() {
		super.onBackPressed();
        photoHandler.removeCallbacks(photoTimer);
		//todo stop handler
	}

    public void loadContent(){

		Log.e("Memory_app","Current Lvel is "+current_level);

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


        this.level= server.getLevel(current_level);
        List<Question> questions = this.level.getQuestions();
        //Log.e("MemoryApp","Total questions in level is "+questions.size()+"and question is "+questions.get(1).getQuestion());
        QtsnData.setQuestions(questions);

    }
}
