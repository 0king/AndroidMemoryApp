package g.m.model;

/**
 * Created by kushroxx on 26/11/16.
 */

import java.util.List;

public class Level {
    private int id;
    private List<Question> questions;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}