package acm.event.codetocreate17.Model.Holders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import acm.event.codetocreate17.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class QuestionViewHolder extends GroupViewHolder {

    private TextView question;
    private ImageView arrow;
    public CardView card;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        question = (TextView) itemView.findViewById(R.id.faq_question_title);
        arrow = (ImageView) itemView.findViewById(R.id.faq_question_arrow);
        card = (CardView) itemView.findViewById(R.id.faq_question_card);
    }

    public void setQuestionTitle(ExpandableGroup faqQuestionModel) {
        question.setText(faqQuestionModel.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
      }

    @Override
    public void collapse() {
        animateCollapse();
      }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }
}
