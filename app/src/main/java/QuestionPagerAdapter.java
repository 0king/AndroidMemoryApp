import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import g.m.MainActivity;
import g.m.QstnSlideFragment;
import g.m.model.Question;

/**
 * Created by kushroxx on 26/11/16.
 */

public class QuestionPagerAdapter extends FragmentPagerAdapter {
    public QstnSlideFragment parentFragment;
    public List<Question> questions;

    public QuestionPagerAdapter(FragmentManager fragmentManager, QstnSlideFragment fragment, List<Question> questions) {
        super(fragmentManager);
        this.parentFragment = fragment;
        this.questions = questions;
        Log.i("TEST", "questions size = " + questions.size());
    }

    public int getCount() {
        return Math.min(this.questions.size(), 3);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public QstnSlideFragment getItem(int position) {
        int questionId=1;
       /* int questionId = ((MainActivity) this.parentFragment.getActivity()).getQuestionsOrder()[position];
        if (questionId >= this.questions.size()) {
            questionId = this.questions.size() - 1;
        }*/
        QstnSlideFragment fragment = new QstnSlideFragment();
      //  fragment.setParentFragment(this.parentFragment);
        fragment.setQuestion((Question) this.questions.get(questionId));
        return fragment;
    }
}
