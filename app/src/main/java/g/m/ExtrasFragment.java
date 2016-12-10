package g.m;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



public class ExtrasFragment extends Fragment implements View.OnClickListener{

	View rootView;

	Button rate,share,contact,reset,aboutUs;

	public ExtrasFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_extras, container, false);

		rate = (Button) rootView.findViewById(R.id.rate);
		share = (Button) rootView.findViewById(R.id.share);
		contact = (Button) rootView.findViewById(R.id.contact);
		reset = (Button) rootView.findViewById(R.id.reset);
		aboutUs = (Button) rootView.findViewById(R.id.about);

		rate.setOnClickListener(this);
		share.setOnClickListener(this);
		contact.setOnClickListener(this);
		reset.setOnClickListener(this);
		aboutUs.setOnClickListener(this);

		return rootView;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.reset:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
				resetGame();
				break;
			case R.id.rate:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
				rateApp();
				break;
			case R.id.share:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
				shareApp();
				break;
			case R.id.contact:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
				sendFeedbackViaEmail();
				break;
			case R.id.about:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
				showAboutDialog();
				break;
		}

	}

	void resetGame(){
		//todo kush implement it
		ContentHelper.getInstance().resetGame();
		Toast.makeText(getContext(), "Reset", Toast.LENGTH_SHORT).show();
	}

	public void showAboutDialog() {
		new AboutDialogFragment().show(getChildFragmentManager(),"AboutUsDialog");
	}

	void shareApp(){
		try{
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.setType("text/plain");
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test and improve your memory");
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This app is good to test your memory powers: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");//todo change url

			//startActivity(sendIntent); //using createChooser, you can set the dialog title
			//or
			startActivity(Intent.createChooser(sendIntent, "Share via:"));
		}catch (Exception e){
			//...
		}

	}

	void rateApp(){
		//Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
		Uri uri = Uri.parse("market://details?id=" + "com.upskew.encode");//todo change url
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		// To count with Play market backstack, After pressing back button,
		// to taken back to our application, we need to add following flags to intent.
		goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		try {
			startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
			//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.upskew.encode"))); //todo change url
		}
	}

	void sendFeedbackViaEmail(){
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "kush.agrawal39@gmail.com")); //todo check email
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Detective memory"); //todo change name
		//emailIntent.putExtra(Intent.EXTRA_TEXT, body);
		//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
		startActivity(Intent.createChooser(emailIntent, "Select mail app:-"));

/*		Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
		emailIntent.setData(Uri.parse("mailto: abc@xyz.com"));
		startActivity(Intent.createChooser(emailIntent, "Send feedback"));*/

/*      String[] TO = {"email@server.com"};
		Uri uri = Uri.parse("mailto:email@server.com")
				          .buildUpon()
				          .appendQueryParameter("subject", "subject")
				          .appendQueryParameter("body", "body")
				          .build();
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
		startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/
	}
}
