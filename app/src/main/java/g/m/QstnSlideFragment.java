package g.m;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	private MainActivity activity;
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

		Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_question);
		TextView toolbarText = (TextView) toolbar.findViewById(R.id.current_level_question);
		toolbarText.setText("Case #"+ContentHelper.getInstance().getCurrentLevel());

		/* setting fonts */
		toolbarText.setTypeface(FontManager.get().getFontDigital());


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
        List<Question> questions = QtsnData.getQuestion();
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

		try{
			onButtonClickedListener = (OnButtonClickedListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " must implement OnButtonClickedListener");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			//todo handle variable # of buttons
			case R.id.option1:
				onButtonClickedListener.onButtonClicked(v.getId());
				break;
            case R.id.option2:
                onButtonClickedListener.onButtonClicked(v.getId());
                break;
            case R.id.option3:
                onButtonClickedListener.onButtonClicked(v.getId());
                break;
            case R.id.option4:
                onButtonClickedListener.onButtonClicked(v.getId());
                break;
            case R.id.option5:
                onButtonClickedListener.onButtonClicked(v.getId());
                break;
		}
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
