package acm.event.codetocreate17.Utility.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.List;

import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Model.Holders.AbouUsHolder;
import acm.event.codetocreate17.View.Fragments.AboutFragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static acm.event.codetocreate17.R.id.view;


/**
 * Created by Shikher on 31-03-2017.
 */

public class AboutAdapter {
    public static class MyAdapter extends ExpandableRecyclerAdapter<MyAdapter.GroupViewHolder, MyAdapter.ItemViewHolder> {

        private LayoutInflater mInflator;

        public MyAdapter(Context context, @NonNull List<AboutFragment.Group> parentItemList) {
            super(parentItemList);
            mInflator = LayoutInflater.from(context);
        }

        @Override
        public MyAdapter.GroupViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View groupView = mInflator.inflate(R.layout.fragment_about_group, parentViewGroup, false);
            return new MyAdapter.GroupViewHolder(groupView);
        }

        @Override
        public MyAdapter.ItemViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View itemView = mInflator.inflate(R.layout.fragment_about_group_item, childViewGroup, false);
            return new MyAdapter.ItemViewHolder(itemView);
        }


        @Override
        public void onBindParentViewHolder(MyAdapter.GroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
            AboutFragment.Group group = (AboutFragment.Group) parentListItem;
            parentViewHolder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(MyAdapter.ItemViewHolder childViewHolder, int position, Object childListItem) {
            childViewHolder.bind(childListItem);
        }

        public class GroupViewHolder extends ParentViewHolder {

            private TextView mGroupTextView;

            public GroupViewHolder(View itemView) {
                super(itemView);
                mGroupTextView = (TextView) itemView.findViewById(R.id.about_list_item_content);
            }

            public void bind(AboutFragment.Group group) {
                mGroupTextView.setText(group.getName());
            }
        }

        public class ItemViewHolder extends ChildViewHolder {

            private TextView mItemTextView, desgination;
            private CircleImageView mImageView;
            private View holder;

            public ItemViewHolder(View itemView) {
                super(itemView);
                holder = itemView;
                mImageView = (CircleImageView) itemView.findViewById(R.id.photo);
                mItemTextView = (TextView) itemView.findViewById(R.id.name);
                desgination = (TextView) itemView.findViewById(R.id.designation);
            }

            public void bind(Object item) {
                final AbouUsHolder child = (AbouUsHolder) item;
                if (child.getContact() == false) {
                mItemTextView.setText(child.getName());
                desgination.setText(child.getDesignation());
                mImageView.setImageResource(child.getImageResource());

            }
            else
                {
                    mItemTextView.setText(child.getName());
                    desgination.setVisibility(View.GONE);
                    mImageView.setImageResource(child.getImageResource());
                    holder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (getAdapterPosition() == 3) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(child.getDesignation()));
                                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                    v.getContext().startActivity(intent);
                                }
                            } else if(getAdapterPosition()==2){
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(child.getDesignation()));
                                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {

                                    v.getContext().startActivity(intent);
                                }
                            } else{
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
    }
}
