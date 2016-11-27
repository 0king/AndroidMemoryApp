package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

	Button startBtn,earnBtn;
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ContentHelper.getInstance().loadLevelData();
		//VideoAd.getInstance().init(this);

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("DEB6865817074BF8BC81596532F6D4CB")
				.build();
		mAdView.loadAd(adRequest);


		startBtn = (Button) findViewById(R.id.startBtn);

		startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PhotoActivity.class));
			}
		});

		earnBtn = (Button) findViewById(R.id.button4);

		earnBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
			}
		});
	}



	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
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
  /*
    mAdView.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdClosed() {
            Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdLeftApplication() {
            Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdOpened() {
            Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
        }
    });*/

}
