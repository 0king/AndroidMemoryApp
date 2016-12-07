package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import g.m.model.Question;
import g.m.utils.FontManager;
import me.relex.circleindicator.CircleIndicator;

import static g.m.R.id.viewpager;

public class QuestionsActivity extends FragmentActivity {

	private static final int NUM_PAGES = 3;

	//private ViewPager viewPager;
	private NonSwipeablePager viewPager;
	private PagerAdapter pagerAdapter;
    public Question currentQuestion;
    Button button;
    TextView txtview;

	//todo add viewpager indicator

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);


        txtview=(TextView)findViewById(R.id.coin_text_question);
        txtview.setText(""+ContentHelper.getInstance().getCurrentCoins()+" ");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_question);
        TextView level_current = (TextView)toolbar.findViewById(R.id.current_level_question);

        level_current.setText("Case #"+ContentHelper.getInstance().getCurrentLevel());
        level_current.setTypeface(FontManager.get().getFontDigital());


		viewPager = (NonSwipeablePager)findViewById(viewpager);
		CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
		//todo disable adapter touch event
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		indicator.setViewPager(viewPager);
        viewPager.setCurrentItem(ContentHelper.getInstance().getCurrentQuestion() - 1);




	}

	//todo remove fragments from stack
	@Override
	public void onBackPressed() {
		super.onBackPressed();
        Log.e("MemoryApp","Back button pressed");

        startActivity(new Intent(QuestionsActivity.this, MainActivity.class));
    }
	/*	*//*if (viewPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
		}*//*
	}
*/
	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */

	//todo use FragmentPagerAdapter

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //todo change each fragment content
			List<Question> questions = ContentHelper.getInstance().getQuestion();
            ContentHelper content = ContentHelper.getInstance();
            int questionId = content.getQuestionsOrder()[position];

            if (questionId >= questions.size()) {
                questionId = questions.size() - 1;
            }
            QstnSlideFragment fragment = new QstnSlideFragment();
            fragment.setQuestion((Question) questions.get(questionId));
            currentQuestion = (Question) questions.get(questionId);
            return fragment;
        }

        @Override
        public int getCount() {
            //todo change #questions
            return NUM_PAGES;
        }


    }

 	public void moveToNextPage(){
		if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1)
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
		else{
			startActivity(new Intent(QuestionsActivity.this,ResultActivity.class));
            finish();
		}
	}

    public void changescore(){

        txtview.setText(""+ContentHelper.getInstance().getCurrentCoins());
        txtview.setTypeface(FontManager.get().getFontChargen());
    }

}
