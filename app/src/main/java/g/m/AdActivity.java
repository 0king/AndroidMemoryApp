package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;

/**
 * Created by kushroxx on 28/11/16.
 */

public class AdActivity extends AppCompatActivity {

    Button closeBtn;
    private AdView ad_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
       // ContentHelper.getInstance().loadLevelData();

        NativeExpressAdView adview = (NativeExpressAdView)findViewById(R.id.nativeAdView);
        adview.loadAd(new AdRequest.Builder().addTestDevice("DEB6865817074BF8BC81596532F6D4CB").build());

        closeBtn= (Button)findViewById(R.id.button5);
        closeBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdActivity.this, PhotoActivity.class));
            }
        });

    }
}
