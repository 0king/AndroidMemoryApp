package g.m;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 5dr on 07/12/16.
 */

public class AboutDialogFragment extends DialogFragment { //import android.support.v4.app.DialogFragment;

	//public AboutDialogFragment(){}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_about, container, false);
		//getDialog().getWindow().requestFeature(1);
		getDialog().setTitle("About Us");

/*		Button dismiss = (Button) view.findViewById(R.id.dismissBtn);
		dismiss.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();//The fragment dialog dismiss() method dismiss the dialog by removes the fragment from fragment manager, and then commit the fragment transaction.
			}
		});*/

		return view;
	}

}
