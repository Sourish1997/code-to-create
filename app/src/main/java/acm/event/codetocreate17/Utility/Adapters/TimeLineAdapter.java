package acm.event.codetocreate17.Utility.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;


import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.Model.Data.TimeLineModel;
import acm.event.codetocreate17.Model.Holders.TimeLineViewHolder;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Miscellaneous.DateTimeUtility;
import acm.event.codetocreate17.Utility.Miscellaneous.VectorDrawableUtility;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    private int lastPosition=-1;
    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private DataGenerator.Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<TimeLineModel> feedList, DataGenerator.Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;


            view = mLayoutInflater.inflate(R.layout.fragment_timeline_item, parent, false);


        return new TimeLineViewHolder(view, viewType);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);


        if(timeLineModel.getStatus() == DataGenerator.OrderStatus.INACTIVE) {

            holder.mTimelineView.setMarker(VectorDrawableUtility.getDrawable(mContext, R.drawable.ic_marker_inactive, R.color.colorAccent));
        } else if(timeLineModel.getStatus() == DataGenerator.OrderStatus.ACTIVE) {
            holder.mCard.setElevation(15f);

            holder.mCard.setCardBackgroundColor(Color.parseColor("#1EB76E"));
            holder.mDate.setTextSize(20);
            holder.mDate.setPadding(0,50,0,0);
            holder.mDate.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mMessage.setTextSize(18);
            holder.mMessage.setPadding(0,0,0,50);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) holder.guideline.getLayoutParams();
            lp.guidePercent = 1;
            holder.guideline.setLayoutParams(lp);
            holder.mTimelineView.setMarker(VectorDrawableUtility.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorAccent));
        } else {

            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorAccent));
        }

        if(!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtility.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        }
        else
            holder.mDate.setVisibility(View.GONE);
            setAnimation(holder.mCard, position);

        holder.mMessage.setText(timeLineModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    private void setAnimation(View viewToAnimate, int position)
    {

        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_in_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
