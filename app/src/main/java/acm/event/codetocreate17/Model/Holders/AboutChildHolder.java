package acm.event.codetocreate17.Model.Holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import acm.event.codetocreate17.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sourish on 11-04-2017.
 */

public class AboutChildHolder extends ChildViewHolder {

    public TextView mItemTextView, desgination;
    public CircleImageView mImageView;
    public LinearLayout holder;

    public AboutChildHolder(View itemView) {
        super(itemView);
        holder = (LinearLayout) itemView.findViewById(R.id.faq_item_root_layout);
        mImageView = (CircleImageView) itemView.findViewById(R.id.photo);
        mItemTextView = (TextView) itemView.findViewById(R.id.name);
        desgination = (TextView) itemView.findViewById(R.id.designation);
    }
}
