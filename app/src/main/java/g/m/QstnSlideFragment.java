package g.m;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import g.m.model.Question;
import g.m.utils.FontManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class QstnSlideFragment extends Fragment implements View.OnClickListener{

	Button btn1,btn2;
	private QuestionsActivity activity;
	private Question question;
	private Button[] buttonAnswers;
	private TextView txtQuestion;

	public QstnSlideFragment() {
		// Required empty public constructor
	}

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion(){
        return this.question;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_qstn_slide, container, false);

		//todo use for for loop
		//Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_question);
		//TextView txtview = (TextView) toolbar.findViewById(R.id.current_level_question);
		//txtview.setText("Level :"+ContentHelper.getInstance().getCurrentLevel());

        this.activity = (QuestionsActivity) getActivity();


        this.buttonAnswers = new Button[5];
        this.buttonAnswers[0] = (Button) rootView.findViewById(R.id.option1);
        this.buttonAnswers[1] = (Button) rootView.findViewById(R.id.option2);
        this.buttonAnswers[2] = (Button) rootView.findViewById(R.id.option3);
        this.buttonAnswers[3] = (Button) rootView.findViewById(R.id.option4);
        this.buttonAnswers[4] = (Button) rootView.findViewById(R.id.option5);
        for (int i = 0; i < this.buttonAnswers.length; i++) {
            this.buttonAnswers[i].setOnClickListener(this);
        }

		this.txtQuestion = (TextView) rootView.findViewById(R.id.question);
        List<Question> questions = ContentHelper.getInstance().getQuestion();
        Log.e ("MemoryApp","Total questions in level is "+questions.size());

		initializeInterface();

		return rootView;
	}

	public void initializeInterface() {
		List<String> answers = this.question.getAnswers();
		Collections.shuffle(answers);
		this.txtQuestion.setText(this.question.getQuestion());
		for (int i = 0; i < this.buttonAnswers.length; i++) {
			if (i < answers.size()) {
				this.buttonAnswers[(4-i)].setText((CharSequence) answers.get(i));
			} else {
				this.buttonAnswers[(4-i)].setVisibility(View.INVISIBLE);
			}
		}
	}

	// Container Activity must implement this interface
	public interface OnButtonClickedListener{
		void onButtonClicked(int buttonId);
	}

	OnButtonClickedListener onButtonClickedListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		//Activity is a context so if you can simply check the context is an Activity and cast it
		Activity activity = context instanceof Activity ? (Activity) context : null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			//todo handle variable # of buttons
			case R.id.option1:
                checkAnswer(this.buttonAnswers[0], this.buttonAnswers[0].getText().toString());
				break;
            case R.id.option2:
                checkAnswer(this.buttonAnswers[1], this.buttonAnswers[1].getText().toString());
                break;
            case R.id.option3:
                checkAnswer(this.buttonAnswers[2], this.buttonAnswers[2].getText().toString());
                break;
            case R.id.option4:
                checkAnswer(this.buttonAnswers[3], this.buttonAnswers[3].getText().toString());
                break;
            case R.id.option5:
                checkAnswer(this.buttonAnswers[4], this.buttonAnswers[4].getText().toString());
                break;
		}
	}

    public void checkAnswer(Button button, String answer){
        //todo change color
        //todo updatePoints()
        if (question.getCorrectAnswer().equals(answer)) {
            Log.e("MemoryApp","Correct Answer");
            button.setBackgroundResource(R.drawable.button_rectangle_green);
            int current_coins = ContentHelper.getInstance().getCurrentCoins();
            ContentHelper.getInstance().setCurrentCoins(current_coins+20);
            int correct_answers = ContentHelper.getInstance().getCurrentCoins();
            ContentHelper.getInstance().setCorrectAnswers(correct_answers+1);
        } else {
            Log.e("MemoryApp","Wrong Answer");
            button.setBackgroundResource(R.drawable.button_rectangle_red);
            int current_coins = ContentHelper.getInstance().getCurrentCoins();
            ContentHelper.getInstance().setCurrentCoins(current_coins-10);
        }
        ContentHelper.getInstance().setCurrentQuestion(ContentHelper.getInstance().getCurrentQuestion() + 1);
        ContentHelper.getInstance().saveLevelData();
        activity.changescore();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                activity.moveToNextPage();
            }
        }, 500);
    }

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			// Code here
		}
	}
}
