package acm.event.codetocreate17.Model.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import acm.event.codetocreate17.R;

/**
 * Created by Sourish on 24-03-2017.
 */

public class MemberHolder extends RecyclerView.ViewHolder {
    public View dividerTop, dividerBottom;
    public ImageView icon;
    public TextView name;
    public TextView email;

    public MemberHolder(View itemView) {
        super(itemView);
        dividerTop = itemView.findViewById(R.id.member_divider_top);
        dividerBottom = itemView.findViewById(R.id.member_divider_bottom);
        icon = (ImageView) itemView.findViewById(R.id.member_leader_icon);
        name = (TextView) itemView.findViewById(R.id.member_name);
        email = (TextView) itemView.findViewById(R.id.member_email);
    }
}