package g.m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.widget.Button;

import g.m.model.Question;

import static g.m.R.id.viewpager;

public class QuestionsActivity extends FragmentActivity implements QstnSlideFragment.OnButtonClickedListener {

	private static final int NUM_PAGES = 3;

	//private ViewPager viewPager;
	private NonSwipeablePager viewPager;
	private PagerAdapter pagerAdapter;
    private Question currentQuestion;

	//todo add viewpager indicator

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);

		viewPager = (NonSwipeablePager)findViewById(viewpager);
		//todo disable adapter touch event
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);

	}

	//todo remove fragments from stack
	/*@Override
	public void onBackPressed() {
		super.onBackPressed();

		*//*if (viewPager.getCurrentItem() == 0) {
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
			ContentHelper content = ContentHelper.getInstance();
			int questionId = content.getQuestionsOrder()[position];
			if (questionId >= QtsnData.questions.size()) {
				questionId = QtsnData.questions.size() - 1;
			}
			QstnSlideFragment fragment = new QstnSlideFragment();
			fragment.setQuestion((Question) QtsnData.questions.get(questionId));
            currentQuestion  = (Question) QtsnData.questions.get(questionId);
            return fragment;
		}

		@Override
		public int getCount() {
			//todo change #questions
			return NUM_PAGES;
		}
	}

	@Override
	public void onButtonClicked(int buttonId) {
		//todo button implementation - move to next automatically
		//todo move to resultActivity after last fragment
        Button button = (Button)findViewById(buttonId);
        String answer = button.getText().toString();
        checkAnswer(button,answer );
        moveToNextPage();
	}

	public void checkAnswer(Button button, String answer){
		//todo change color
		//todo updatePoints()
        if (currentQuestion.getCorrectAnswer().equals(answer)) {
            button.setBackgroundResource(R.drawable.button_rectangle_green);
        } else {
            button.setBackgroundResource(R.drawable.button_rectangle_red);
        }
	}

	public void moveToNextPage(){
		if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1)
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
		else{
			startActivity(new Intent(QuestionsActivity.this,ResultActivity.class));
		}
	}

}
