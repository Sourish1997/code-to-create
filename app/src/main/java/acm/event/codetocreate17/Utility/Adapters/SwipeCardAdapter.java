package acm.event.codetocreate17.Utility.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import acm.event.codetocreate17.Model.Data.QuizQuestionModel;
import acm.event.codetocreate17.R;

/**
 * Created by Sourish on 26-03-2017.
 */

public class SwipeCardAdapter extends ArrayAdapter<QuizQuestionModel> {
    public SwipeCardAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        TextView question = (TextView)(contentView.findViewById(R.id.quiz_question));
        RadioButton choice1 = (RadioButton) (contentView.findViewById(R.id.quiz_choice_1));
        RadioButton choice2 = (RadioButton) (contentView.findViewById(R.id.quiz_choice_2));
        RadioButton choice3 = (RadioButton) (contentView.findViewById(R.id.quiz_choice_3));
        RadioButton choice4 = (RadioButton) (contentView.findViewById(R.id.quiz_choice_4));
        QuizQuestionModel data = getItem(position);
        question.setText(data.statement);
        choice1.setText(data.choices[0]);
        choice2.setText(data.choices[1]);
        choice3.setText(data.choices[2]);
        choice4.setText(data.choices[3]);
        choice1.setClickable(false);
        choice2.setClickable(false);
        choice3.setClickable(false);
        choice4.setClickable(false);
        return contentView;
    }

}
