package acm.event.codetocreate17.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.MemberRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class TeamFragment extends Fragment implements ScreenShotable {
    @BindView(R.id.team_root_layout)
    CoordinatorLayout teamContainer;
    @BindView(R.id.team_details_view)
    RecyclerView membersList;

    RecyclerView.Adapter membersAdapter;
    ArrayList<String> memberNames;
    ArrayList<String> memberEmails;

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

        //TODO: Replace with response from server
        for(int i = 0; i < 3; i++) {
            memberNames.add(i, "Firstname Lastname");
            memberEmails.add(i, "myname@example.com");
        }

        membersAdapter = new MemberRecyclerAdapter(memberNames, memberEmails);
        membersList.setAdapter(membersAdapter);
        membersList.setLayoutManager(new LinearLayoutManager(membersList.getContext()));
        return rootView;
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