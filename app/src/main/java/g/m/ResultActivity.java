package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import g.m.utils.Constants;
import g.m.utils.FontManager;

public class ResultActivity extends AppCompatActivity {

	private ContentHelper server;
	private AdView mAdView;

	Button next_level,main_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		server = ContentHelper.getInstance();

<<<<<<< HEAD
		main_menu = (Button) findViewById(R.id.home);
		next_level = (Button) findViewById(R.id.nextLevel);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_result);
		TextView toolbarText = (TextView) toolbar.findViewById(R.id.current_level_result);

		/* setting fonts for textViews */
		TextView coinsText = (TextView) findViewById(R.id.textCoins);
		TextView coinsEarned = (TextView) findViewById(R.id.coinsearned);
		TextView coinsLost = (TextView) findViewById(R.id.coinslost);
		TextView totalCoins = (TextView) findViewById(R.id.totalCoins);
		coinsText.setTypeface(FontManager.get().getFontDigital());
		totalCoins.setTypeface(FontManager.get().getFontDigital());
		next_level.setTypeface(FontManager.get().getFontChargen());
		main_menu.setTypeface(FontManager.get().getFontAflsolid());
		coinsEarned.setTypeface(FontManager.get().getFontDigital());
		coinsLost.setTypeface(FontManager.get().getFontDigital());
		toolbarText.setTypeface(FontManager.get().getFontDigital());


		toolbarText.setText("Level: "+server.getCurrentLevel());
=======
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_result);
       // TextView txtview = (TextView) toolbar.findViewById(R.id.current_level_result);
      //  txtview.setText("Level :"+server.getCurrentLevel());
>>>>>>> kushroxx/master

		mAdView = (AdView) findViewById(R.id.adView1);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("DEB6865817074BF8BC81596532F6D4CB")
				.build();
		mAdView.loadAd(adRequest);
        next_level = (Button) findViewById(R.id.button2);
		next_level.setTypeface(FontManager.get().getFontChargen());

<<<<<<< HEAD

		if((server.getCurrentLevel()-1) == Constants.total_levels){
			server.resetGame();
            next_level.setVisibility(View.INVISIBLE);
		}else{
			server.resetForNextLevel();
		}

=======
		if((server.getCurrentLevel()) == Constants.total_levels){
			server.resetGame();
            next_level.setVisibility(View.INVISIBLE);
		}else{
			server.resetForNextLevel();
		}

>>>>>>> kushroxx/master
        
        server.saveLevelData();

		next_level.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(ResultActivity.this, PhotoActivity.class));
				finish();
			}
		});

<<<<<<< HEAD
=======
		main_menu = (Button) findViewById(R.id.button3);
		main_menu.setTypeface(FontManager.get().getFontChargen());
>>>>>>> kushroxx/master

		main_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
			}
		});
	}
}
