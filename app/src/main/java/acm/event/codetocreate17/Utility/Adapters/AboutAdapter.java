package acm.event.codetocreate17.Utility.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;

import acm.event.codetocreate17.Model.Data.AboutGroupModel;
import acm.event.codetocreate17.Model.Data.AboutModel;
import acm.event.codetocreate17.Model.Holders.AboutChildHolder;
import acm.event.codetocreate17.Model.Holders.AboutParentHolder;
import acm.event.codetocreate17.R;


/**
 * Created by Shikher on 31-03-2017.
 */

public class AboutAdapter extends ExpandableRecyclerAdapter<AboutParentHolder, AboutChildHolder> {

    private LayoutInflater layoutInflater;

    public AboutAdapter(Context context, @NonNull ArrayList<AboutGroupModel> parentItemList) {
        super(parentItemList);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AboutParentHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View groupView = layoutInflater.inflate(R.layout.fragment_about_group, parentViewGroup, false);
        return new AboutParentHolder(groupView);
    }

    @Override
    public AboutChildHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View itemView = layoutInflater.inflate(R.layout.fragment_about_group_item, childViewGroup, false);
        return new AboutChildHolder(itemView);
    }


    @Override
    public void onBindParentViewHolder(AboutParentHolder parentViewHolder, int position, ParentListItem parentListItem) {
        AboutGroupModel group = (AboutGroupModel) parentListItem;
        parentViewHolder.groupTextView.setText(group.getName());
    }

    @Override
    public void onBindChildViewHolder(AboutChildHolder childViewHolder, final int position, Object childListItem) {
        final AboutModel child = (AboutModel) childListItem;
        if (child.getContact() == false) {
            childViewHolder.mItemTextView.setText(child.getName());
            childViewHolder.desgination.setText(child.getDesignation());
            childViewHolder.mImageView.setImageResource(child.getImageResource());
        } else {
            childViewHolder.mItemTextView.setText(child.getName());
            childViewHolder.desgination.setVisibility(View.GONE);
            childViewHolder.mImageView.setImageResource(child.getImageResource());
            childViewHolder.holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 3) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(child.getDesignation()));
                        if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                            v.getContext().startActivity(intent);
                        }
                    } else if(position == 2){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(child.getDesignation()));
                        if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                            v.getContext().startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(child.getDesignation()));
                        if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                            v.getContext().startActivity(intent);
                        }

                    }
                }
            });
        }
    }
}
