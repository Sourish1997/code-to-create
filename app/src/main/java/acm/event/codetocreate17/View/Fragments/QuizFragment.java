package acm.event.codetocreate17.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wenchao.cardstack.CardStack;

import acm.event.codetocreate17.Model.Data.QuizQuestionModel;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.SwipeCardAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

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

    SwipeCardAdapter swipeCardAdapter;
    QuizQuestionModel model;

    int cardCount = 15;

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

        model = new QuizQuestionModel(getResources().getString(R.string.sample_question), getResources().getStringArray(R.array.sample_question_options));
        for(int i =0; i < 15; i++)
            swipeCardAdapter.add(model);

        questionStack.setAdapter(swipeCardAdapter);
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
        if(cardCount == 0) {
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
    }

    @OnClick(R.id.quiz_start_button)
    public void onStartRequest(View v) {
        Animation slideOutAnimation  = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out);
        quizIntro.startAnimation(slideOutAnimation);
        quizIntro.setVisibility(View.INVISIBLE);
        questionStack.setVisibility(View.VISIBLE);
        Animation slideInAnimation  = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.grow);
        questionStack.startAnimation(slideInAnimation);
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

