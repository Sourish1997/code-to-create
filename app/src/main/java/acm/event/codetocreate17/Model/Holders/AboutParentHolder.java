package acm.event.codetocreate17.Model.Holders;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import acm.event.codetocreate17.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Sourish on 11-04-2017.
 */

public class AboutParentHolder extends ParentViewHolder {

    public TextView groupTextView;
    public ImageView arrow;

    public AboutParentHolder(View itemView) {
        super(itemView);
        groupTextView = (TextView) itemView.findViewById(R.id.about_list_item_content);
        arrow = (ImageView) itemView.findViewById(R.id.about_question_arrow);
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);;
        if (expanded) {
            animateCollapse();
        } else {
            animateExpand();
        }
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
