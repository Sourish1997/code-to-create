package acm.event.codetocreate17.Model.Data;

/**
 * Created by Sourish on 27-03-2017.
 */

public class QuizQuestionModel {
    public String statement;
    public String[] choices;
    public int correctChoice;

    public QuizQuestionModel(String statement, String choices[], int correctChoice) {
        this.statement = statement;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

}
