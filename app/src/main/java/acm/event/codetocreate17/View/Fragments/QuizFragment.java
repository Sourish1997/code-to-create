package acm.event.codetocreate17.View.Fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wenchao.cardstack.CardStack;

import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.SwipeCardAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class QuizFragment extends Fragment implements ScreenShotable, CardStack.CardEventListener {
    @BindView(R.id.quiz_root_layout)
    ConstraintLayout quizContainer;
    @BindView(R.id.quiz_question_stack)
    CardStack questionStack;

    SwipeCardAdapter swipeCardAdapter;
    int cardCount = 5;

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

        swipeCardAdapter = new SwipeCardAdapter(getActivity().getApplicationContext(),0);
        swipeCardAdapter.add("card 1");
        swipeCardAdapter.add("card 2");
        swipeCardAdapter.add("card 3");
        swipeCardAdapter.add("card 4");
        swipeCardAdapter.add("card 5");

        questionStack.setAdapter(swipeCardAdapter);
        return rootView;
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {
        if (direction == 1) {
            Log.e("message", "1");
        } else if (direction == 0) {
            Log.e("message", "0");
        } else if (direction == 2){
            Log.e("message", "2");
        } else {
            Log.e("message", "3");
        }
        cardCount--;
        if(cardCount == 0) {
            ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Fetching more questions. Please wait...", true);
            dialog.show();
            swipeCardAdapter.add("card 1");
            swipeCardAdapter.add("card 2");
            swipeCardAdapter.add("card 3");
            swipeCardAdapter.add("card 4");
            swipeCardAdapter.add("card 5");
            Animation slideInAnimation  = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_in);
            questionStack.startAnimation(slideInAnimation);
            cardCount = 5;
            dialog.dismiss();
        }
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

