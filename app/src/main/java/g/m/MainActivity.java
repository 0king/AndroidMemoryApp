package g.m;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import g.m.utils.PreferenceManager;
import g.m.utils.Utils;

public class MainActivity extends AppCompatActivity implements NetworkStateReceiverListener {

	Button startBtn;
    ContentHelper server;

    private class LoadFromServer extends AsyncTask {
		@Override
		protected Object doInBackground(Object[] objects) {

            server.loadAllImagesFromServer(getApplicationContext());

			return null;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        PreferenceManager.get().init(this);
        int  already_cached=PreferenceManager.get().getInt(PreferenceManager.PREF_ALREADY_CACHED, 0);

        if(already_cached==0 && Utils.isNetworkAvailable(this)) {
            server = ContentHelper.getInstance();
            server.loadJsonFromServer();

            new LoadFromServer().execute();
            PreferenceManager.get().putInt(PreferenceManager.PREF_ALREADY_CACHED, 1);

        }else {
            Toast.makeText(this, "Connect to Internet", Toast.LENGTH_SHORT).show();
        }

		startBtn = (Button) findViewById(R.id.startBtn);

		startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PhotoActivity.class));
			}
		});
	}

}
