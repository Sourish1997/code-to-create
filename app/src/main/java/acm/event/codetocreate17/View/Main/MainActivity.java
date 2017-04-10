package acm.event.codetocreate17.View.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import acm.event.codetocreate17.Model.RealmModels.TeamMember;
import acm.event.codetocreate17.Model.RealmModels.User;
import acm.event.codetocreate17.Model.RetroAPI.RetroAPI;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
import acm.event.codetocreate17.View.Authentication.LoginActivity;
import acm.event.codetocreate17.View.Fragments.AboutFragment;
import acm.event.codetocreate17.View.Fragments.CouponsFragment;
import acm.event.codetocreate17.View.Fragments.FaqFragment;
import acm.event.codetocreate17.View.Fragments.QuizFragment;
import acm.event.codetocreate17.View.Fragments.SponsorFragment;
import acm.event.codetocreate17.View.Fragments.TeamFragment;
import acm.event.codetocreate17.View.Fragments.TimelineFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.realm.Realm;
import io.realm.RealmList;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_drawer)
    LinearLayout drawerLinearLayout;
    @BindView(R.id.main_content_frame)
    LinearLayout revealContainer;
    @BindView(R.id.main_content_overlay)
    LinearLayout revealOverlay;
    @BindView(R.id.refresh_button)
    ImageButton refreshButton;
    @BindView(R.id.main_scroll_view)
    ScrollView scrollView;

    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private ProgressDialog dialog;
    private String currentFragmentName = "Timeline";
    private boolean refreshed = false;

    Realm realm;
    RetroAPI retroAPI;
    SharedPreferences.Editor sharedPreferencesEditor;

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

        refreshButton.setVisibility(View.INVISIBLE);

        setActionBar();
        createMenuList();

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        retroAPI = new RetroAPI();
        sharedPreferencesEditor = this.getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE).edit();
        viewAnimator = new ViewAnimator<>(this, list, loadTimelineFragment(), drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem1 = new SlideMenuItem("Close", R.drawable.ic_close);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem("Timeline", R.drawable.ic_timeline);
        list.add(menuItem2);
        if(!Constants.isGuest) {
            SlideMenuItem menuItem3 = new SlideMenuItem("Team", R.drawable.ic_group);
            list.add(menuItem3);
        }
        SlideMenuItem menuItem4 = new SlideMenuItem("Quiz", R.drawable.ic_quiz);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem("Coupons", R.drawable.ic_coupons);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem("About Us", R.drawable.ic_info);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem("FAQ", R.drawable.ic_faq);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem("Sponsors", R.drawable.ic_sponsors);
        list.add(menuItem8);
        SlideMenuItem menuItem9 = new SlideMenuItem("Logout", R.drawable.ic_logout);
        list.add(menuItem9);
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
                if (slideOffset > 0.6 && drawerLinearLayout.getChildCount() == 0) {
                    viewAnimator.showMenuContent();
                }
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

    public ScreenShotable loadFaqFragment() {
        FaqFragment faqFragment = new FaqFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, faqFragment).commit();
        return faqFragment;
    }

    public ScreenShotable loadSponsorFragment() {
        SponsorFragment sponsorFragment = new SponsorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, sponsorFragment).commit();
        return sponsorFragment;
    }


    public ScreenShotable loadAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, aboutFragment).commit();
        return aboutFragment;
    }

    public ScreenShotable loadQuizFragment() {
        QuizFragment quizFragment = new QuizFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, quizFragment).commit();
        return quizFragment;
    }

    public ScreenShotable loadCouponsFragment() {
        CouponsFragment couponsFragment = new CouponsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, couponsFragment).commit();
        return couponsFragment;
    }

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
        revealOverlay.setVisibility(View.VISIBLE);
        if(slideMenuItem.getName().equals(currentFragmentName))
            return screenShotable;
        if(!slideMenuItem.getName().equals("Close")) {
            replaceFragmentAnimation(screenShotable, position);
            currentFragmentName = slideMenuItem.getName();
            refreshButton.setVisibility(View.INVISIBLE);
        }
        if(refreshed) {
            BitmapDrawable appImage = new BitmapDrawable(takeScreenShot(revealContainer));
            revealOverlay.setBackground(appImage);
            refreshed = false;
        }
        switch (slideMenuItem.getName()) {
            case "Close":
                return screenShotable;
            case "Timeline":
                getSupportActionBar().setTitle("Timeline");
                return loadTimelineFragment();
            case "Team":
                getSupportActionBar().setTitle("My Team");
                refreshButton.setVisibility(View.VISIBLE);
                return loadTeamFragment();
            case "FAQ":
                getSupportActionBar().setTitle("FAQ");
                return loadFaqFragment();
            case "About Us":
                getSupportActionBar().setTitle("About Us");
                return loadAboutFragment();
            case "Quiz":
                getSupportActionBar().setTitle("Quiz");
                return loadQuizFragment();
            case "Coupons":
                getSupportActionBar().setTitle("Coupons");
                return loadCouponsFragment();
            case "Sponsors":
                getSupportActionBar().setTitle("Sponsors");
                return loadSponsorFragment();
            case "Logout":
                sharedPreferencesEditor.putBoolean("loggedin", false);
                sharedPreferencesEditor.commit();
                loadLogin();
            default:
                return screenShotable;
        }
    }

    public void loadLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.refresh_button)
    public void refresh(View v) {
        syncProfile();
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

    public void refreshFragment() {
        switch (currentFragmentName) {
            case "Team":
                loadTeamFragment();
                break;
        }

        int cx = revealContainer.getLeft();
        int cy = (revealContainer.getBottom() + revealContainer.getTop()) / 2;

        int dx = Math.max(cx, revealContainer.getWidth() - cx);
        int dy = Math.max(cy, revealContainer.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(revealContainer, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        revealOverlay.setVisibility(View.INVISIBLE);
        animator.start();

        refreshed = true;
    }

    public Bitmap takeScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        view.buildDrawingCache();

        if(view.getDrawingCache() == null) return null;

        Bitmap snapshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return snapshot;
    }

    public void syncProfile() {
        String accessToken = Constants.accessToken;
        if(accessToken.equals("Unauthorized")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.sharedPreferenceName, MODE_PRIVATE);
            Constants.accessToken = sharedPreferences.getString("authtoken", "Unauthorized");
            accessToken = Constants.accessToken;
        }
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Refreshing...");
        dialog.show();
        dialog.setCancelable(false);
        retroAPI.observableAPIService.syncProfile(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(drawerLayout, "Could not connect to server", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        syncProfile();
                                    }
                                });
                        snackbar.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if(jsonObject.get("success").getAsBoolean()){
                            User user = realm.where(User.class).findFirst();
                            realm.beginTransaction();
                            user.hasTeam = true;
                            String leader = jsonObject.get("ADMIN").getAsString();
                            user.isLeader = jsonObject.get("admin").getAsBoolean();
                            user.teamName = jsonObject.get("teamName").getAsString();
                            JsonArray teamMembers = jsonObject.getAsJsonArray("teammembers");
                            JsonArray teamMemberEmails = jsonObject.getAsJsonArray("teammembersemail");
                            user.noOfMembers = teamMembers.size() - 1;
                            for(int i = 0; i < teamMembers.size(); i++) {
                                TeamMember teamMember = new TeamMember();
                                teamMember.name = teamMembers.get(i).getAsString();
                                if(teamMember.name.equals(user.name))
                                    continue;
                                teamMember.email = teamMemberEmails.get(i).getAsString();
                                if(teamMember.name.equals(leader))
                                    teamMember.isLeader = true;
                                else
                                    teamMember.isLeader = false;
                                if(user.teamMembers == null)
                                    user.teamMembers = new RealmList<>();
                                user.teamMembers.add(teamMember);
                            }
                            realm.copyToRealmOrUpdate(user);
                            realm.commitTransaction();
                            dialog.dismiss();
                            refreshFragment();
                        } else {
                            String message = jsonObject.get("message").getAsString();
                            if(message.equals("You dont habe any team")) {
                                User user = realm.where(User.class).findFirst();
                                realm.beginTransaction();
                                user.hasTeam = false;
                                user.isLeader = false;
                                realm.copyToRealmOrUpdate(user);
                                realm.commitTransaction();
                                dialog.dismiss();
                                refreshFragment();
                            } else {
                                Snackbar snackbar = Snackbar
                                        .make(drawerLayout, "Could not connect to server", Snackbar.LENGTH_LONG)
                                        .setAction("RETRY", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                syncProfile();
                                            }
                                        });
                                dialog.dismiss();
                                snackbar.show();
                            }
                        }
                    }
                });
    }
}