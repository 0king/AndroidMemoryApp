/**
 * Created by kushroxx on 28/11/16.
 */
package g.m;


    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ProgressBar;

    import com.adcolony.sdk.*;

    public class VideoActivity extends Activity
    {
        final private String APP_ID = "app4ecf3028aa1049e5aa";
        final private String ZONE_ID = "vzab74cea7aa4b49fd97";
        final private String TAG = "AdColonyDemo";

        private Button show_button;
        private ProgressBar progress;
        private AdColonyInterstitial ad;
        private AdColonyInterstitialListener listener;
        private AdColonyAdOptions ad_options;

        @Override
        protected void onCreate( Bundle bundle )
        {
            super.onCreate( bundle );
            setContentView( R.layout.activity_interstitial );
            progress = (ProgressBar) findViewById( R.id.progress );

            /** Construct optional app options object to be sent with configure */
            AdColonyAppOptions app_options = new AdColonyAppOptions()
                    .setUserID( "unique_user_id" );

            /**
             * Configure AdColony in your launching Activity's onCreate() method so that cached ads can
             * be available as soon as possible.
             */
            AdColony.configure( this, app_options, APP_ID, ZONE_ID );

            /** Optional user metadata sent with the ad options in each request */
            AdColonyUserMetadata metadata = new AdColonyUserMetadata()
                    .setUserAge( 26 )
                    .setUserEducation( AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE )
                    .setUserGender( AdColonyUserMetadata.USER_MALE );

            /** Ad specific options to be sent with request */
            ad_options = new AdColonyAdOptions()
                    .setUserMetadata( metadata );


            /**
             * Set up listener for interstitial ad callbacks. You only need to implement the callbacks
             * that you care about. The only required callback is onRequestFilled, as this is the only
             * way to get an ad object.
             */
            listener = new AdColonyInterstitialListener()
            {
                /** Ad passed back in request filled callback, ad can now be shown */
                @Override
                public void onRequestFilled( AdColonyInterstitial ad )
                {
                    VideoActivity.this.ad = ad;

                    ad.show();
                    progress.setVisibility( View.INVISIBLE );
                    Log.d( TAG, "onRequestFilled" );
                }

                /** Ad request was not filled */
                @Override
                public void onRequestNotFilled( AdColonyZone zone )
                {
                    progress.setVisibility( View.INVISIBLE );
                    Log.d( TAG, "onRequestNotFilled");
                }

                /** Ad opened, reset UI to reflect state change */
                @Override
                public void onOpened( AdColonyInterstitial ad )
                {
                    //show_button.setEnabled( false );
                    progress.setVisibility( View.VISIBLE );
                    Log.d( TAG, "onOpened" );
                }

                /** Request a new ad if ad is expiring */
                @Override
                public void onExpiring( AdColonyInterstitial ad )
                {
                    //show_button.setEnabled( false );
                    progress.setVisibility( View.VISIBLE );
                   // AdColony.requestInterstitial( ZONE_ID, this, ad_options );
                    Log.d( TAG, "onExpiring" );
                    finish();
                    startActivity(new Intent(VideoActivity.this, MainActivity.class));
                }
                @Override
                public void onClosed(AdColonyInterstitial ad) {
                    Log.d( TAG, "on CLosing" );
                    finish();
                    startActivity(new Intent(VideoActivity.this, MainActivity.class));
                }
            };

            /** Set up button to show an ad when clicked */
        }

        @Override
        protected void onResume()
        {
            super.onResume();

            /**
             * It's somewhat arbitrary when your ad request should be made. Here we are simply making
             * a request if there is no valid ad available onResume, but really this can be done at any
             * reasonable time before you plan on showing an ad.
             */
            if (ad == null || ad.isExpired())
            {
                /**
                 * Optionally update location info in the ad options for each request:
                 * LocationManager location_manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                 * Location location = location_manager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
                 * ad_options.setUserMetadata( ad_options.getUserMetadata().setUserLocation( location ) );
                 */
                progress.setVisibility( View.VISIBLE );
                AdColony.requestInterstitial( ZONE_ID, listener, ad_options );
            }

        }
    }
