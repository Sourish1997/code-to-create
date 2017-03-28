package acm.event.codetocreate17.Model.Holders;

import android.support.constraint.Guideline;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.github.vipulasri.timelineview.TimelineView;

import acm.event.codetocreate17.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_timeline_date)
    public TextView mDate;
    @BindView(R.id.card_timeline)
    public CardView mCard;
    @BindView(R.id.text_timeline_title)
    public TextView mMessage;
    @BindView(R.id.time_marker)
    public TimelineView mTimelineView;
    @BindView(R.id.guide_timeline)
    public Guideline guideline;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        mTimelineView.initLine(viewType);
    }
}
