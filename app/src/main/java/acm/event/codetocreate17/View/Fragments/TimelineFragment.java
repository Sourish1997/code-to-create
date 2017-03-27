package acm.event.codetocreate17.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;


import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.Model.Data.TimeLineModel;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.MemberRecyclerAdapter;
import acm.event.codetocreate17.Utility.Adapters.TimeLineAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

import static acm.event.codetocreate17.Model.Data.DataGenerator.getTimelineDate;
import static acm.event.codetocreate17.Model.Data.DataGenerator.getTimelineEvent;

public class TimelineFragment extends Fragment implements ScreenShotable {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.timeline_root_layout)
    RelativeLayout timelineContainer;

    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private DataGenerator.Orientation mOrientation;
    private boolean mWithLinePadding;
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
        View rootView = inflater.inflate(R.layout.content_timeline, container, false);
        ButterKnife.bind(this, rootView);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        mOrientation = DataGenerator.Orientation.VERTICAL;
        mWithLinePadding = false;

        //setTitle(getResources().getString(R.string.vertical_timeline));
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        initView();
        return rootView;
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }


    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void setDataListItems(){
        for(int i=0; i<9 ; i++)
        {
            mDataList.add(new TimeLineModel(getTimelineEvent(i),getTimelineDate(i),DataGenerator.OrderStatus.ACTIVE));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(timelineContainer.getWidth(),
                        timelineContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                timelineContainer.draw(canvas);
                TimelineFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}


