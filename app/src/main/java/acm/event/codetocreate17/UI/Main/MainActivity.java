package acm.event.codetocreate17.UI.Main;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import acm.event.codetocreate17.R;
import acm.event.codetocreate17.UI.Fragments.TeamFragment;
import acm.event.codetocreate17.UI.Fragments.TimelineFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_drawer)
    LinearLayout drawerLinearLayout;

    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();

        viewAnimator = new ViewAnimator<>(this, list, loadTimelineFragment(), drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem1 = new SlideMenuItem("Close", R.drawable.ic_close);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem("Timeline", R.drawable.ic_delivery_box_and_timer);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem("Team", R.drawable.ic_workers_team);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem("Quiz", R.drawable.ic_notepad);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem("Coupons", R.drawable.ic_coupon);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem("About Us", R.drawable.ic_icon);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem("FAQ", R.drawable.ic_businessman_with_doubts);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem("Sponsors", R.drawable.ic_shaking_hands);
        list.add(menuItem8);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Timeline");
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                drawerLinearLayout.removeAllViews();
                drawerLinearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && drawerLinearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    public ScreenShotable loadTimelineFragment() {
        TimelineFragment timelineFragment = new TimelineFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, timelineFragment).commit();
        return timelineFragment;
    }

    public ScreenShotable loadTeamFragment() {
        TeamFragment teamFragment = new TeamFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, teamFragment).commit();
        return teamFragment;
    }

    /*public ScreenShotable loadFaqFragment() {
        FaqFragment faqFragment = new FaqFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, faqFragment).commit();
        return faqFragment;
    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void replaceFragmentAnimation(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.main_content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.main_content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        if(!slideMenuItem.getName().equals("Close"))
            replaceFragmentAnimation(screenShotable, position);
        switch (slideMenuItem.getName()) {
            case "Close":
                return screenShotable;
            case "Timeline":
                getSupportActionBar().setTitle("Timeline");
                return loadTimelineFragment();
            case "Team":
                getSupportActionBar().setTitle("My Team");
                return loadTeamFragment();
            case "FAQ":
                getSupportActionBar().setTitle("FAQs");
                //return loadFaqFragment();
            default:
                return screenShotable;
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        drawerLinearLayout.addView(view);
    }
}