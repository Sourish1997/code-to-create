package acm.event.codetocreate17.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import acm.event.codetocreate17.Model.RealmModels.User;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.MemberRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class TeamFragment extends Fragment implements ScreenShotable, AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.team_root_layout)
    CoordinatorLayout teamContainer;
    @BindView(R.id.team_details_view)
    RecyclerView membersList;
    @BindView(R.id.team_appbar)
    AppBarLayout appBar;
    @BindView(R.id.team_username)
    TextView usernameTextView;
    @BindView(R.id.team_email_id)
    TextView emailTextView;
    @BindView(R.id.team_name)
    TextView teamNameTextView;
    @BindView(R.id.team_user_crown)
    ImageView crownImage;
    @BindView(R.id.team_profile_image)
    CircleImageView profileImage;

    MemberRecyclerAdapter membersAdapter;
    ArrayList<String> memberNames;
    ArrayList<String> memberEmails;
    ArrayList<Boolean> isLeader;
    boolean userAdded = false;

    Realm realm;
    User user;
    String username;
    String email;
    boolean userIsLeader;

    private Bitmap bitmap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);
        ButterKnife.bind(this, rootView);

        memberNames = new ArrayList<>();
        memberEmails = new ArrayList<>();
        isLeader = new ArrayList<>();

        Realm.init(this.getActivity());
        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
        username = user.name;
        email = user.email;
        usernameTextView.setText(username);
        emailTextView.setText(email);
        userIsLeader = user.isLeader;
        if(!user.isLeader) {
            crownImage.setLayoutParams(new ConstraintLayout.LayoutParams(1, 1));
            crownImage.setImageResource(R.drawable.ic_block);
        }

        if(user.gender.equals("female"))
            profileImage.setImageResource(R.drawable.av_female);
        else
            profileImage.setImageResource(R.drawable.av_male);

        if(user.hasTeam) {
            teamNameTextView.setText(user.teamName.substring(0, 1).toUpperCase() + user.teamName.substring(1));
            for (int i = 0; i < user.noOfMembers; i++) {
                memberNames.add(i, user.teamMembers.get(i).name);
                memberEmails.add(i, user.teamMembers.get(i).email);
                isLeader.add(i, user.teamMembers.get(i).isLeader);
            }
        } else {
            teamNameTextView.setText("No Team!");
        }

        membersAdapter = new MemberRecyclerAdapter(memberNames, memberEmails, isLeader);
        membersList.setAdapter(membersAdapter);
        membersList.setLayoutManager(new LinearLayoutManager(membersList.getContext()));
        membersList.setItemAnimator(new FadeInLeftAnimator());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        appBar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        appBar.removeOnOffsetChangedListener(this);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if(percentage > 0.7 && !userAdded) {
            membersAdapter.addUserToTeamView(username, email, userIsLeader, 0);
            userAdded = true;
        }

        if(percentage < 0.7 && userAdded) {
            membersAdapter.removeUserFromTeamView(0);
            userAdded = false;
        }
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(teamContainer.getWidth(),
                        teamContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                teamContainer.draw(canvas);
                TeamFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}