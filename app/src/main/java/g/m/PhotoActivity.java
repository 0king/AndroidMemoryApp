package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import g.m.model.Level;
import g.m.model.Question;
import g.m.utils.Constants;

public class PhotoActivity extends AppCompatActivity {
	private Level level;
    ImageView view;
    ContentHelper server;
	//screen timer
	private static int TIME_OUT = 6000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		/*TextView tgetStringxtview = (TextView)findViewById(R.id.textView2) ;
		txtview.setText((Constants.current_level+1));*/

        //todo pre fetch data
         server = ContentHelper.getInstance();
        view = (ImageView) findViewById(R.id.image);

        loadContent();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				Intent i = new Intent(PhotoActivity.this, QuestionsActivity.class);
				startActivity(i);

				//close this activity
				finish();
			}
		}, TIME_OUT);

	}

	/* calling this function so that if a user presses back button while the timer is running, the user will be taken back and timer stops and QuestionsActivity wont load after timeout*/
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//todo stop handler
	}

    public void loadContent(){

        Glide.with(getApplicationContext())
                .load(Constants.IMAGE_URL[server.getCurrentLevel()])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);


        this.level= server.getLevel(server.getCurrentLevel());
        List<Question> questions = this.level.getQuestions();
        //Log.e("MemoryApp","Total questions in level is "+questions.size()+"and question is "+questions.get(1).getQuestion());
        QtsnData.setQuestions(questions);
        server.loadLevelData();


    }
}
