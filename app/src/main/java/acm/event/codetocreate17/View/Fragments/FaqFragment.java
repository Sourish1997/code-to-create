package acm.event.codetocreate17.View.Fragments;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.QuestionAdapter;
import acm.event.codetocreate17.Utility.Utils.Answer;
import acm.event.codetocreate17.Utility.Utils.Question;
import butterknife.BindView;
import butterknife.ButterKnife;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class FaqFragment extends Fragment implements ScreenShotable {

    public QuestionAdapter adapter;

    @BindView(R.id.faq_root_view)
    ConstraintLayout faqLayout;

    @BindView(R.id.faq_recycler_view)
    RecyclerView recyclerView;

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
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        ArrayList<Question> finalQuestions = new ArrayList<>();
        DataGenerator dataGenerator = new DataGenerator();
        String[] questions = dataGenerator.getQuestions();
        String[] answers = dataGenerator.getAnswers();

        for(int i = 0; i < questions.length; i++) {
            ArrayList<Answer> answer = new ArrayList<>();
            answer.add(new Answer(answers[i]));
            Question question = new Question(questions[i], answer);
            finalQuestions.add(question);
        }

        adapter = new QuestionAdapter(finalQuestions);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }






    /*private ArrayList<DataGenerator> getDataSet() {
        ArrayList results = new ArrayList<DataGenerator>();
        for (int index = 0; index < 6; index++) {
            DataGenerator obj = new DataGenerator("Question " + index,
                    "Answer " + index);
            results.add(index, obj);
        }
        return results;
    }*/


    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(faqLayout.getWidth(),
                        faqLayout.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                faqLayout.draw(canvas);
                FaqFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}