package acm.event.codetocreate17.View.Fragments;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import acm.event.codetocreate17.Model.Data.AboutGroupModel;
import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.AboutAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class AboutFragment extends Fragment implements ScreenShotable {
    @BindView(R.id.about_root_view)
    CoordinatorLayout aboutContainer;
    @BindView(R.id.about_recycler_view)
    RecyclerView aboutRecyclerView;

    private Bitmap bitmap;
    private ArrayList<AboutGroupModel> groupList;
    private AboutAdapter aboutAdapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);
        groupList = new ArrayList<>();
        setAboutData();
        aboutRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemAnimator animator = aboutRecyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        return rootView;
    }

    private void setAboutData() {
        groupList.add(DataGenerator.getFacultyOrganaiser(this));
        groupList.add(DataGenerator.getStudentOrganiser(this));
        groupList.add(DataGenerator.getContacts(this));
        aboutAdapter = new AboutAdapter(getContext(), groupList);
        aboutRecyclerView.setAdapter(aboutAdapter);
    }

    @Override
    public void takeScreenShot() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(aboutContainer.getWidth(),
                        aboutContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                aboutContainer.draw(canvas);
                AboutFragment.this.bitmap = bitmap;
            }
        });
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

