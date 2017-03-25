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
    boolean userAdded = false;

    public MemberRecyclerAdapter(ArrayList<String> memberNames, ArrayList<String> memberEmails) {
        this.memberNames = memberNames;
        this.memberEmails = memberEmails;
    }

    public void removeUserFromTeamView(int position) {
        memberNames.remove(position);
        memberEmails.remove(position);
        notifyItemRemoved(position);
    }

    public void addUserToTeamView(String name, String email, int position) {
        memberNames.add(position, name);
        memberEmails.add(position, email);
        notifyItemInserted(position);
        userAdded = true;
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
            memberHolder.dividerTop.setBackgroundColor(0x00000000);
        if(userAdded && position == 0)
            memberHolder.dividerBottom.setBackgroundColor(0x22000000);
    }

    @Override
    public int getItemCount() {
        return memberNames.size();
    }
};
