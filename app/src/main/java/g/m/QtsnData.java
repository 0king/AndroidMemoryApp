package g.m;

import java.util.List;

import g.m.model.Question;

/**
 * Created by 5dr on 22/11/16.
 */

public class QtsnData {
	String question;
	String answer;
	String option1;
	String option2;
	String option3;
	String option4;

	public static List<Question> questions;

	public static void setQuestions(List<Question> ques){

		QtsnData.questions =ques;
	}

	public static List<Question> getQuestion(){

		return QtsnData.questions;
	}




}
