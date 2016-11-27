package g.m;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import g.m.utils.PreferenceManager;
import g.m.utils.Utils;

public class MainActivity extends AppCompatActivity {

	Button startBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ContentHelper.getInstance().loadLevelData();

		startBtn = (Button) findViewById(R.id.startBtn);

		startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PhotoActivity.class));
			}
		});
	}

}
