package g.m;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import g.m.utils.Constants;
import g.m.utils.FontManager;
import g.m.utils.PreferenceManager;
import g.m.utils.Utils;

public class MainActivity extends AppCompatActivity {

	public static Button startBtn, earnBtn;
	ImageButton settingsBtn,coinsButton;
	private AdView mAdView;
    public static TextView coin_text;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ContentHelper.getInstance().loadData();

		//Shows the banner ad
		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("DEB6865817074BF8BC81596532F6D4CB")
				.build();
		mAdView.loadAd(adRequest);


		startBtn = (Button) findViewById(R.id.startBtn);


		/* setting custom font to play button text */
		//Typeface chargen = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/chargen.ttf");
		startBtn.setTypeface(FontManager.get().getFontChargen());

		/* setting custom font for guidance text on main screen */
		//Typeface minecraft = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/minecraft.ttf");
		TextView textView3 = (TextView) findViewById(R.id.textView3);
		TextView textView4 = (TextView) findViewById(R.id.textView4);
        coin_text = (TextView) findViewById(R.id.coin_text);
        Log.e("MemoryApp","Coins :"+ContentHelper.getInstance().getCurrentCoins());
        coin_text.setText(""+ContentHelper.getInstance().getCurrentCoins()+" ");
        coin_text.setTypeface(FontManager.get().getFontChargen());

		textView3.setTypeface(FontManager.get().getFontAndroid7());
		textView4.setTypeface(FontManager.get().getFontAndroid7());

        if((ContentHelper.getInstance().getCurrentLevel()) == Constants.total_levels ) {
            startBtn.setVisibility(View.INVISIBLE);
            new GameOverDialogFragment().show(getSupportFragmentManager(), "GameOver");
        }

		startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                ContentHelper.getInstance().playButtonClickSound(getApplicationContext());
				if(ContentHelper.getInstance().getCurrentCoins()<= 10){
                    new EarnCoins().show(getSupportFragmentManager(), "EarnCoins");

				}else {
					if (ContentHelper.getInstance().getTimeLeft() > 0) {
						startActivity(new Intent(MainActivity.this, PhotoActivity.class));
						finish();
					} else {
						startActivity(new Intent(MainActivity.this, QuestionsActivity.class));
						finish();
					}
				}
			}
		});
        coinsButton = (ImageButton) findViewById(R.id.coin_main);
        coinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EarnCoins().show(getSupportFragmentManager(), "EarnCoins");
                //finish(); //this method ends the activity
            }
        });
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentHelper.getInstance().playButtonClickSound(getApplicationContext());
				//fragmentTransaction.add(R.id.activity_main, new ExtrasFragment());
				//fragmentTransaction.commit();
				//Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
				getSupportFragmentManager()
						.beginTransaction()
						.add(android.R.id.content,new ExtrasFragment())
						.addToBackStack(null)
						.commit();
				//finish(); //this method ends the activity
			}
		});


	}

	public void onBackPressed() {
		super.onBackPressed();
		ContentHelper.getInstance().saveLevelData();
	}



	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
        ContentHelper.getInstance().saveLevelData();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
	}

	@Override
	public void onDestroy() {
		if (mAdView != null) {
			mAdView.destroy();
		}
		super.onDestroy();
	}

    public static void resetActivity(){


        startBtn.setVisibility(View.VISIBLE);
        Log.e("MemoryApp","Coins Updated :"+ContentHelper.getInstance().getCurrentCoins());
        coin_text.setText(""+ContentHelper.getInstance().getCurrentCoins()+" ");
        coin_text.setTypeface(FontManager.get().getFontChargen());


    }
}
