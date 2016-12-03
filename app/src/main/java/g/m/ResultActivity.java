package g.m;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultActivity extends AppCompatActivity {

	private ContentHelper server;
	private AdView mAdView;

	Button next_level,main_menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		server = ContentHelper.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_result);
        TextView txtview = (TextView) toolbar.findViewById(R.id.current_level_result);
        txtview.setText("Level :"+server.getCurrentLevel());

		mAdView = (AdView) findViewById(R.id.adView1);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("DEB6865817074BF8BC81596532F6D4CB")
				.build();
		mAdView.loadAd(adRequest);

		server.resetForNextLevel();
        server.saveLevelData();

		next_level = (Button) findViewById(R.id.button2);

		next_level.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(ResultActivity.this, PhotoActivity.class));
				finish();
			}
		});

		main_menu = (Button) findViewById(R.id.button3);

		main_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
			}
		});
	}
}
