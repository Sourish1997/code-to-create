package acm.event.codetocreate17.View.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;
import java.util.Arrays;

import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.Model.Data.QuizQuestionModel;
import acm.event.codetocreate17.Model.RealmModels.User;
import acm.event.codetocreate17.Model.RetroAPI.RetroAPI;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.SwipeCardAdapter;
import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
import acm.event.codetocreate17.View.Main.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

import static android.content.Context.MODE_PRIVATE;

public class QuizFragment extends Fragment implements ScreenShotable, CardStack.CardEventListener {
    @BindView(R.id.quiz_root_layout)
    ConstraintLayout quizContainer;
    @BindView(R.id.quiz_question_stack)
    CardStack questionStack;
    @BindView(R.id.quiz_intro_card)
    CardView quizIntro;
    @BindView(R.id.quiz_end_card)
    CardView quizCompleteCard;
    @BindView(R.id.quiz_completed_image_overlay)
    ImageView completedImageOverlay;
    @BindView(R.id.quiz_completed_message)
    TextView completedMessage;
    @BindView(R.id.quiz_start_button)
    Button quizStartButton;

    SwipeCardAdapter swipeCardAdapter;
    Realm realm;
    User user;
    RetroAPI retroAPI;
    ProgressDialog progressDialog;

    int cardCount = 15;
    int noOfQuestions = 30;
    int lastQuestion = -1;
    int lastQuestionIndex = -1;
    int marks = 0;
    int[] questionArray;
    private boolean isLeader;
    private boolean finished = false;
    private QuizQuestionModel[] quizDatabase;
    private boolean initialDataReceived = false;
    private boolean updateSent = true;
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
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.bind(this, rootView);

        questionStack.setContentResource(R.layout.fragment_quiz_question);
        questionStack.setStackMargin(18);
        questionStack.setListener(this);

        swipeCardAdapter = new SwipeCardAdapter(getActivity().getApplicationContext(), 0);

        Realm.init(this.getActivity());
        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();

        quizDatabase = new DataGenerator().getQuizDatabase();
        retroAPI = new RetroAPI();
        questionArray = new int[15];

        if(!Constants.isGuest) {
            isLeader = user.isLeader;
            if (Constants.accessToken.equals("Unauthorized")) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.sharedPreferenceName, MODE_PRIVATE);
                Constants.accessToken = sharedPreferences.getString("authtoken", "Unauthorized");
            }
        }

        if(!Constants.isGuest)
            if(isLeader)
                getQuizData();

        return rootView;
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        return (distance > 300)? true : false;
    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if(distance > 250) {
            RadioButton choice;
            if(section == 0)
                choice = (RadioButton) questionStack.getTopView().findViewById(R.id.quiz_choice_1);
            else if (section == 1)
                choice = (RadioButton) questionStack.getTopView().findViewById(R.id.quiz_choice_2);
            else if (section == 2)
                choice = (RadioButton) questionStack.getTopView().findViewById(R.id.quiz_choice_3);
            else
                choice = (RadioButton) questionStack.getTopView().findViewById(R.id.quiz_choice_4);
            choice.setChecked(true);
        }
        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {
        cardCount--;
        lastQuestionIndex++;
        int choice = quizDatabase[questionArray[14 - cardCount] - 1].correctChoice;
        if(choice == direction)
            marks++;
        updateSent = false;
        if(cardCount == 0) {
            quizFinished();
            return;
        }
        lastQuestion = questionArray[14 - cardCount];
    }

    @OnClick(R.id.quiz_start_button)
    public void onStartRequest(View v) {
        if(Constants.isGuest) {
            Snackbar snackbar = Snackbar.make(quizContainer, "Sign in with an admin account to take this quiz.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if(!isLeader) {
            Snackbar snackbar = Snackbar.make(quizContainer, "Oops! You are not an admin.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            getQuizData();
        }
    }

    public void getQuizData() {
        String accessToken = Constants.accessToken;
        if(!initialDataReceived) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetching Quiz Data...");
            progressDialog.show();
            progressDialog.setCancelable(false);
        } else {
            quizStartButton.setText("Fetching Quiz Data...");
            quizStartButton.setClickable(false);
        }

        retroAPI.observableAPIService.getQuizData(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(!initialDataReceived) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Could not connect to server!")
                                    .setCancelable(false)
                                    .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            getQuizData();
                                        }
                                    })
                                    .setNegativeButton("HOME", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ((MainActivity) getActivity()).animatedLoadTeamFragment();
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            quizStartButton.setText("Start Quiz");
                            quizStartButton.setClickable(true);
                            Snackbar snackbar = Snackbar
                                    .make(quizContainer, "Could not connect to server!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (jsonObject.get("success").getAsBoolean()) {
                            boolean isLive = jsonObject.get("isLive").getAsBoolean();
                            if(isLive) {
                                boolean started = jsonObject.get("started").getAsBoolean();
                                boolean finished = jsonObject.get("finished").getAsBoolean();
                                if(started && !finished) {
                                    JsonArray qArray = jsonObject.getAsJsonObject("quiz").getAsJsonArray("qArray");
                                    for(int i = 0; i < qArray.size(); i++)
                                        questionArray[i] = qArray.get(i).getAsInt();
                                    lastQuestion = jsonObject.getAsJsonObject("quiz").get("lastQ").getAsInt();
                                    marks = jsonObject.getAsJsonObject("quiz").get("marks").getAsInt();
                                    if(lastQuestion == -1) {
                                        cardCount = 15;
                                        lastQuestionIndex = -1;
                                    } else {
                                        for (int i = 0; i < qArray.size(); i++)
                                            if (questionArray[i] == lastQuestion) {
                                                cardCount = 14 - i;
                                                lastQuestionIndex = i;
                                                break;
                                            }
                                    }
                                    progressDialog.dismiss();
                                    showQuiz();
                                } else if (started && finished) {
                                    progressDialog.dismiss();
                                    showQuizFinishedCard();
                                } else {
                                    if(!initialDataReceived) {
                                        progressDialog.dismiss();
                                    } else {
                                        startQuiz();
                                    }
                                }
                            } else {
                                if(initialDataReceived) {
                                    quizStartButton.setText("Start Quiz");
                                    quizStartButton.setClickable(true);
                                    Snackbar snackbar = Snackbar
                                            .make(quizContainer, "Quiz is not live!!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                } else {
                                    initialDataReceived = true;
                                    progressDialog.dismiss();
                                }
                            }
                            if(!initialDataReceived)
                                initialDataReceived = true;
                        } else {}
                    }
                });
    }

    public void showQuiz() {
        for(int i = lastQuestionIndex + 1; i < 15; i++)
            swipeCardAdapter.add(quizDatabase[questionArray[i] - 1]);
        questionStack.setAdapter(swipeCardAdapter);
        Animation slideOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out);
        quizIntro.startAnimation(slideOutAnimation);
        quizIntro.setVisibility(View.INVISIBLE);
        questionStack.setVisibility(View.VISIBLE);
        Animation growAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.grow);
        questionStack.startAnimation(growAnimation);

        Thread quizDataUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!finished) {
                    if((cardCount % 3) == 0 && updateSent == false && cardCount != 0) {
                        updateQuestionData();
                        updateSent = true;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {}
                }
            }
        });
        quizDataUpdateThread.start();
    }

    public void startQuiz() {
        String accessToken = Constants.accessToken;
        quizStartButton.setText("Initializing Quiz...");
        for(int i = 0; i < 15; i++)
            questionArray[i] = -1;
        for(int i = 0; i < 15; i++) {
            int random = (int) (Math.random() * noOfQuestions) + 1;
            outer:
            while(true) {
                for (int j = 0; j < i; j++) {
                    if (questionArray[j] == random) {
                        random++;
                        if(random == noOfQuestions)
                            random = 0;
                        continue outer;
                    }
                }
                break;
            }
            questionArray[i] = random;
        }
        Arrays.sort(questionArray);
        ArrayList<Integer> qArray = new ArrayList<>();
        for(int i = 0; i < 15; i++)
            qArray.add(questionArray[i]);

        long time = System.currentTimeMillis();
        retroAPI.observableAPIService.startQuiz(accessToken, time, qArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        quizStartButton.setText("Start Quiz");
                        quizStartButton.setClickable(true);
                        Snackbar snackbar = Snackbar
                                .make(quizContainer, "Could not connect to server!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startQuiz();
                                    }
                                });
                        snackbar.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (jsonObject.get("success").getAsBoolean()) {
                            showQuiz();
                        } else {}
                    }
                });
    }

    public void updateQuestionData() {
        String accessToken = Constants.accessToken;
        retroAPI.observableAPIService.updateQuizData(accessToken, lastQuestion, marks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Could not connect to server!")
                                .setCancelable(false)
                                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        showUpdateProgressDialog();
                                    }
                                })
                                .setNegativeButton("HOME", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ((MainActivity) getActivity()).animatedLoadTeamFragment();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (jsonObject.get("success").getAsBoolean()) {
                            boolean isLive = jsonObject.get("isLive").getAsBoolean();
                            if(isLive) {
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            } else {
                                quizFinished();
                            }
                        } else {};
                    }
                });
    }

    public void showUpdateProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Reconnecting...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        updateQuestionData();
    }

    public void quizFinished() {
        String accessToken = Constants.accessToken;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading Final Score...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        long time = System.currentTimeMillis();
        retroAPI.observableAPIService.finishQuiz(accessToken, time, marks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Could not connect to server!")
                                .setCancelable(false)
                                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        quizFinished();
                                    }
                                })
                                .setNegativeButton("HOME", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ((MainActivity) getActivity()).animatedLoadTeamFragment();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (jsonObject.get("success").getAsBoolean()) {
                            progressDialog.dismiss();
                            showQuizFinishedCard();
                        } else {}
                    }
                });
    }

    public void showQuizFinishedCard() {
        finished = true;
        quizIntro.setVisibility(View.GONE);
        questionStack.setVisibility(View.GONE);
        quizCompleteCard.setVisibility(View.VISIBLE);
        final Animation growAnimation  = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.grow);
        growAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                completedImageOverlay.setVisibility(View.VISIBLE);
                completedMessage.setVisibility(View.VISIBLE);
                Animation growAnimation2  = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.grow);
                completedImageOverlay.startAnimation(growAnimation2);
                completedMessage.startAnimation(growAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        quizCompleteCard.startAnimation(growAnimation);
    }

    @Override
    public void topCardTapped() {}

    @Override
    public void takeScreenShot() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(quizContainer.getWidth(),
                        quizContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                quizContainer.draw(canvas);
                QuizFragment.this.bitmap = bitmap;
            }
        });
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

