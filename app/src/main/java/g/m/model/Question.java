package g.m.model;

/**
 * Created by kushroxx on 26/11/16.
 */

import java.util.List;

public class Question {
    private List<String> answers;
    private String correctAnswer;
    private String question;

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
