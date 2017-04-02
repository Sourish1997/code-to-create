package acm.event.codetocreate17.View.Landing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
import acm.event.codetocreate17.View.Authentication.LoginActivity;
import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.CustomViews.Typewriter;
import acm.event.codetocreate17.View.Main.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    Animation fadeInAnimation;

    @BindView(R.id.splash_logo)
    ImageView splashLogo;

    @BindView(R.id.splash_text_1)
    Typewriter text1;

    @BindView(R.id.splash_text_2)
    Typewriter text2;

    private boolean shouldFinish = false;
    private Thread animationThread;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(Constants.sharedPreferenceName, MODE_PRIVATE);
        boolean loggedin = sharedPreferences.getBoolean("loggedin", false);
        if(loggedin)
            loadMain();

        fadeInAnimation  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        fadeInAnimation.setAnimationListener(this);
        splashLogo.startAnimation(fadeInAnimation);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (shouldFinish) {
            finish();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        text1.setText("");
        text1.setCharacterDelay(150);
        text2.setText("");
        text2.setCharacterDelay(150);

        final DataGenerator dataGenerator = new DataGenerator();
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text1.typeText(dataGenerator.splashData(0));
                    }
                });
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!text1.getText().equals(dataGenerator.splashData(0))) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text2.typeText(dataGenerator.splashData(1));
                                Thread thread2 = new Thread(new Runnable() {
                                    @Override
                                    public void run () {
                                        while (!text2.getText().equals(dataGenerator.splashData(1))) {
                                            try {
                                                Thread.sleep(50);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(250);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                loadLogin();
                                            }
                                        });
                                    }
                                });
                                thread2.start();
                            }
                        });
                        }
                    });
                    thread1.start();
                }
        });
        animationThread.start();
    }

    public void loadMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadLogin() {
        shouldFinish = true;

        Intent intent = new Intent(this, LoginActivity.class);

        String transitionName = getString(R.string.transition_zoom_out);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, splashLogo, transitionName);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}