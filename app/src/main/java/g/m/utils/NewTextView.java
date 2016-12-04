package g.m.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 5dr on 03/12/16.
 */

public class NewTextView extends TextView {

	public NewTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public NewTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NewTextView(Context context) {
		super(context);
		init();
	}

	private void init() {

		/* for EditText, use - if (!isInEditMode()) */

			Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenPro-Regular.ttf");
			setTypeface(tf);

	}
}
