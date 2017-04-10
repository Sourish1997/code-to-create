package acm.event.codetocreate17.Model.Holders;

import android.view.View;
import android.widget.TextView;


import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import acm.event.codetocreate17.R;

public class AnswerViewHolder extends ChildViewHolder {

  private TextView childTextView;

  public AnswerViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.faq_answer_text);
  }

  public void setAnswer(String name) {
    childTextView.setText(name);
  }
}
