package g.m;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import g.m.utils.FontManager;


public class GameOverDialogFragment extends DialogFragment implements OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_game_over, container, false);
        getDialog().getWindow().requestFeature(1);
        TextView txtMessage = (TextView) view.findViewById(R.id.txt_dialog_message);
        TextView txtButtonOK = (TextView) view.findViewById(R.id.txt_button_ok);
        ((TextView) view.findViewById(R.id.txt_dialog_title)).setTypeface(FontManager.get().getFontSemiBold());
        txtMessage.setTypeface(FontManager.get().getFontRegular());
        txtButtonOK.setTypeface(FontManager.get().getFontRegular());
        txtButtonOK.setOnClickListener(this);
        txtButtonOK.setSoundEffectsEnabled(false);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_button_ok:
                ContentHelper.getInstance().playButtonClickSound(getActivity());
                dismiss();
            default:
        }
    }
}
