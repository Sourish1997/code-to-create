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



import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;


import java.util.ArrayList;
import java.util.List;

import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.R;

import acm.event.codetocreate17.Utility.Adapters.AboutUsRecyclerAdapter;
import butterknife.BindView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class AboutFragment extends Fragment implements ScreenShotable {
    @BindView(R.id.about_root_view)
    CoordinatorLayout aboutContainer;

    private Bitmap bitmap;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout root;
    private List<Group> groupList;
    private AboutUsRecyclerAdapter.MyAdapter myAdapter;


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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.about_recycler_view);
        root = (CoordinatorLayout) rootView.findViewById(R.id.about_root_view);
        groupList = new ArrayList<>();
        setAboutData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        return rootView;
    }

    private void setAboutData() {

        groupList.add(DataGenerator.getFacultyOrganaiser(this));
        groupList.add(DataGenerator.getStudentOrganiser(this));
        groupList.add(DataGenerator.getContacts(this));
        myAdapter = new AboutUsRecyclerAdapter.MyAdapter(getContext(), groupList);
        mRecyclerView.setAdapter(myAdapter);
    }





    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(aboutContainer.getWidth(),
                        aboutContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                aboutContainer.draw(canvas);
                AboutFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }


    public class Group implements ParentListItem {


        private List mItems;
        private String name;

        public Group(List items) {
            mItems = items;
        }


        @Override
        public List<?> getChildItemList() {
            return mItems;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }


        }

