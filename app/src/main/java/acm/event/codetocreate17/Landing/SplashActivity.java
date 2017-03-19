package acm.event.codetocreate17.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import acm.event.codetocreate17.Authentication.LoginActivity;
import acm.event.codetocreate17.Data.DataGenerator;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utilities.Typewriter;
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
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

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
        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 3; i++) {
                    final int fi = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text1.typeText(dataGenerator.splashData(0, fi));
                        }
                    });
                    Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(!text1.getText().equals(dataGenerator.splashData(0, fi))) {
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text2.typeText(dataGenerator.splashData(1, fi));
                                    Thread thread2 = new Thread(new Runnable() {
                                        @Override
                                        public void run () {
                                            while (!text2.getText().equals(dataGenerator.splashData(1, fi))) {
                                                try {
                                                    Thread.sleep(200);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(1000);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    text2.deleteText(dataGenerator.splashData(1, fi));
                                                    Thread thread3 = new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            while (!text2.getText().equals("")) {
                                                                try {
                                                                    Thread.sleep(200);
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    text1.deleteText(dataGenerator.splashData(0, fi));
                                                                }
                                                            });
                                                        }
                                                    });
                                                    thread3.start();
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
                    try {
                        if(i == 0)
                            Thread.sleep(6000);
                        else if(i == 1)
                             Thread.sleep(7000);
                        else {
                            Thread.sleep(7000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadLogin();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animationThread.start();

        shouldFinish = true;
    }

    public void loadLogin() {
        Intent intent = new Intent(this, LoginActivity.class);

        String transitionName = getString(R.string.transition_zoom_out);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, splashLogo, transitionName);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}