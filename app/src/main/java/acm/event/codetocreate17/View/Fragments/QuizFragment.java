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
import android.util.Log;
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

import acm.event.codetocreate17.Model.Data.QuizQuestionModel;
import acm.event.codetocreate17.Model.RealmModels.User;
import acm.event.codetocreate17.Model.RetroAPI.RetroAPI;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.SwipeCardAdapter;
import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
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
    QuizQuestionModel model;

    Realm realm;
    User user;
    RetroAPI retroAPI;
    int cardCount = 15;
    int noOfQuestions = 30;
    int lastQuestion = 0;
    int marks = 0;
    int[] questionArray;
    private boolean isLeader;
    private boolean finished = false;
    ProgressDialog progressDialog;
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

        retroAPI = new RetroAPI();
        questionArray = new int[15];

        isLeader = user.isLeader;
        if(Constants.accessToken.equals("Unauthorized")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.sharedPreferenceName, MODE_PRIVATE);
            Constants.accessToken = sharedPreferences.getString("authtoken", "Unauthorized");
        }

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
        if (direction == 0) {
        } else if (direction == 1) {
        } else if (direction == 2){
        } else {
        }
        cardCount--;
        if(((int) (Math.random() * 2)) == 1)
            marks++;
        updateSent = false;
        lastQuestion = questionArray[15 - cardCount];
        if(cardCount == 0) {
            quizFinished();
        }
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

    public void startQuiz() {
        String accessToken = Constants.accessToken;
        quizStartButton.setText("Initializing Quiz...");
        for(int i = 0; i < 15; i++)
            questionArray[i] = -1;
        for(int i = 0; i < 15; i++) {
            int random = (int) (Math.random() * (noOfQuestions + 1));
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
        lastQuestion = questionArray[0];
        ArrayList<Integer> qArray = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            qArray.add(questionArray[i]);
            Log.e("number", questionArray[i] + "");
        }

        retroAPI.observableAPIService.startQuiz(accessToken, System.currentTimeMillis(), qArray)
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
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (jsonObject.get("success").getAsBoolean()) {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
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
        retroAPI.observableAPIService.finishQuiz(accessToken, System.currentTimeMillis(), marks)
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
                                        showUpdateProgressDialog();
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
                                    JsonArray qArray = jsonObject.getAsJsonArray("qArray");
                                    for(int i = 0; i < qArray.size(); i++)
                                        questionArray[i] = qArray.get(i).getAsInt();
                                    lastQuestion = jsonObject.get("lastQ").getAsInt();
                                    marks = jsonObject.get("marks").getAsInt();
                                    for(int i = 0; i < qArray.size(); i++)
                                        if(questionArray[i] == lastQuestion)
                                            cardCount = 15 - i - 1;
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
                                }
                            }
                            if(!initialDataReceived)
                                initialDataReceived = true;
                        } else {}
                    }
                });
    }

    public void showQuiz() {
        model = new QuizQuestionModel(getResources().getString(R.string.sample_question), getResources().getStringArray(R.array.sample_question_options));
        for(int i =0; i < cardCount; i++)
            swipeCardAdapter.add(model);
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
                    if((cardCount % 3) == 0 && updateSent == false) {
                        updateQuestionData();
                        updateSent = true;
                    }
                }
            }
        });
        quizDataUpdateThread.start();
    }

    public void showQuizFinishedCard() {
        finished = true;
        quizIntro.setVisibility(View.INVISIBLE);
        questionStack.setVisibility(View.INVISIBLE);
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
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(quizContainer.getWidth(),
                        quizContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                quizContainer.draw(canvas);
                QuizFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

