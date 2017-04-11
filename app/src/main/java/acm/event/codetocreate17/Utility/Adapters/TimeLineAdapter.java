package acm.event.codetocreate17.Utility.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            Log.e("check",Integer.toString(position));
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) holder.guideline.getLayoutParams();
            lp.guidePercent = 1;
            holder.guideline.setLayoutParams(lp);
            holder.mTimelineView.setMarker(VectorDrawableUtility.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorAccent));
        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorAccent));
        }

        if(timeLineModel.getStatus() != DataGenerator.OrderStatus.ACTIVE) {
            holder.mCard.setElevation(5f);
            holder.mCard.setCardBackgroundColor(Color.parseColor("#414141"));
            holder.mDate.setTextSize(15);
            holder.mDate.setPadding(0,0,0,0);
            holder.mDate.setTextColor(Color.parseColor("#1EB76E"));
            holder.mMessage.setTextSize(14);
            holder.mMessage.setPadding(0,0,0,0);
            Log.e("check",Integer.toString(position));
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) holder.guideline.getLayoutParams();
            lp.guidePercent = (float) 0.95;
            holder.guideline.setLayoutParams(lp);
        }

        if(!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtility.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        }
        else
            holder.mDate.setVisibility(View.GONE);

        holder.mMessage.setText(timeLineModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }
}
