package g.m;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 5dr on 24/11/16.
 */

public class NonSwipeablePager extends ViewPager {

	public NonSwipeablePager(Context context) {
		super(context);
	}

	public NonSwipeablePager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		return false;
	}

	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
}
