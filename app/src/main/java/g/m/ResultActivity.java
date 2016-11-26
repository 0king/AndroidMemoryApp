package g.m;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {

	private ContentHelper server;

	Button next_level,main_menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		server = ContentHelper.getInstance();

		server.resetForNextLevel();

		next_level = (Button) findViewById(R.id.button2);

		next_level.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ResultActivity.this, PhotoActivity.class));
			}
		});

		main_menu = (Button) findViewById(R.id.button3);

		main_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ResultActivity.this, MainActivity.class));
			}
		});
	}
}
