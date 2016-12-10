package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g.m.utils.FontManager;

/**
 * Created by kushroxx on 10/12/16.
 */


public class EarnCoins extends DialogFragment implements View.OnClickListener { //import android.support.v4.app.DialogFragment;

    //public AboutDialogFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_earn_coins, container, false);
        TextView txtMessage = (TextView) view.findViewById(R.id.message);
        TextView txtButtonFreeCoins = (TextView) view.findViewById(R.id.earncoins);
        TextView txtButtonClose = (TextView) view.findViewById(R.id.cancel);
        ((TextView) view.findViewById(R.id.title)).setTypeface(FontManager.get().getFontSemiBold());
        txtMessage.setTypeface(FontManager.get().getFontRegular());
        txtButtonFreeCoins.setTypeface(FontManager.get().getFontRegular());
        txtButtonClose.setTypeface(FontManager.get().getFontRegular());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.earncoins:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
                startActivity(new Intent(getActivity(),VideoActivity.class));
                dismiss();
                break;
            case R.id.cancel:
                ContentHelper.getInstance().playButtonClickSound(getActivity().getApplicationContext());
                dismiss();
                break;

        }

    }

}
