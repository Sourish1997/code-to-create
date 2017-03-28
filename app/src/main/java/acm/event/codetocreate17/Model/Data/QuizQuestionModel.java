package acm.event.codetocreate17.Model.Data;

/**
 * Created by Sourish on 27-03-2017.
 */

public class QuizQuestionModel {
    public String statement;
    public String[] choices;

    public QuizQuestionModel(String statement, String choices[]) {
        this.statement = statement;
        this.choices = choices;
    }

}
