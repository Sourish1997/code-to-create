package acm.event.codetocreate17.Utility.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import acm.event.codetocreate17.Model.Holders.MemberHolder;
import acm.event.codetocreate17.R;

/**
 * Created by Sourish on 26-03-2017.
 */

public class MemberRecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<String> memberNames;
    ArrayList<String> memberEmails;

    public MemberRecyclerAdapter(ArrayList<String> memberNames, ArrayList<String> memberEmails) {
        this.memberNames = memberNames;
        this.memberEmails = memberEmails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_team_member_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MemberHolder memberHolder = (MemberHolder) holder;
        memberHolder.name.setText(memberNames.get(position));
        memberHolder.email.setText(memberEmails.get(position));
        if(position == 0)
        memberHolder.divider.setBackgroundColor(0x00000000);
    }

    @Override
    public int getItemCount() {
        return memberNames.size();
    }
};
