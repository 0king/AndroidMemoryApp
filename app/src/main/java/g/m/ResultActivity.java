package g.m;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import g.m.utils.Constants;
import g.m.utils.FontManager;

public class ResultActivity extends AppCompatActivity {

	private ContentHelper server;
	private AdView mAdView;
    ImageButton coinsButton;

	Button next_level,main_menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		server = ContentHelper.getInstance();

		main_menu = (Button) findViewById(R.id.home);
		next_level = (Button) findViewById(R.id.nextLevel);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_result);
		TextView toolbarText = (TextView) toolbar.findViewById(R.id.current_level_result);

		/* setting fonts for textViews */
		TextView coinsText = (TextView) findViewById(R.id.textCoins);
		TextView coinsEarned = (TextView) findViewById(R.id.coinsearned);
		TextView coinsLost = (TextView) findViewById(R.id.coinslost);
		//extView totalCoins = (TextView) findViewById(R.id.totalCoins);

		TextView current_level = (TextView) findViewById(R.id.current_level_result);
		TextView coins_count = (TextView) findViewById(R.id.coin_text_result);


        current_level.setText("Case #"+ContentHelper.getInstance().getCurrentLevel());
        current_level.setTypeface(FontManager.get().getFontDigital());

        coins_count.setText(""+ContentHelper.getInstance().getCurrentCoins()+" ");
        coins_count.setTypeface(FontManager.get().getFontChargen());


        int correct_anwsers =ContentHelper.getInstance().getCorrectAnswers();
        coinsEarned.setText(""+(correct_anwsers*20));
        coinsLost.setText(""+((3-correct_anwsers)*10));
		coinsText.setTypeface(FontManager.get().getFontDigital());
		//totalCoins.setTypeface(FontManager.get().getFontDigital());
		next_level.setTypeface(FontManager.get().getFontChargen());
		main_menu.setTypeface(FontManager.get().getFontChargen());
		coinsEarned.setTypeface(FontManager.get().getFontDigital());
		coinsLost.setTypeface(FontManager.get().getFontDigital());
		toolbarText.setTypeface(FontManager.get().getFontDigital());


		toolbarText.setText("Case #"+server.getCurrentLevel());

		mAdView = (AdView) findViewById(R.id.adView1);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("DEB6865817074BF8BC81596532F6D4CB")
				.build();
		mAdView.loadAd(adRequest);

        //next_level = (Button) findViewById(R.id.button2);

        if(server.getCurrentCoins() < 10) {
            next_level.setVisibility(View.INVISIBLE);
            new EarnCoins().show(getSupportFragmentManager(), "EarnCoins");
        }

		if((server.getCurrentLevel()) == Constants.total_levels ){
            next_level.setVisibility(View.INVISIBLE);
            new GameOverDialogFragment().show(getSupportFragmentManager(), "GameOver");
		}else{
			server.resetForNextLevel();
		}
		ContentHelper.getInstance().loadData();

        
        server.saveLevelData();

        if(server.getCurrentLevel() % 3 == 0){

            startActivity(new Intent(ResultActivity.this, AdActivity.class));
            finish();
        }


        next_level.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                ContentHelper.getInstance().playButtonClickSound(getApplicationContext());
				startActivity(new Intent(ResultActivity.this, PhotoActivity.class));
                finish();

			}
		});

		//main_menu = (Button) findViewById(R.id.button3);

		main_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentHelper.getInstance().playButtonClickSound(getApplicationContext());
				startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
			}
		});

	}

	@Override
	public void onBackPressed() {

		Log.e("MemoryApp","Back button pressed");

		startActivity(new Intent(ResultActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
	}

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
