package acm.event.codetocreate17.Utilities;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

/**
 * Created by Sourish on 19-03-2017.
 */

public class Typewriter extends android.support.v7.widget.AppCompatTextView {
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 150;

    public Typewriter(Context context) {
        super(context);
    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();

    private Runnable characterAdder = new Runnable() {

        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));

            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    private Runnable characterRemover = new Runnable() {

        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex--));

            if (mIndex >= 0) {
                mHandler.postDelayed(characterRemover, mDelay);
            }
        }
    };

    public void typeText(CharSequence txt) {
        mText = txt;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void deleteText(CharSequence txt) {
        mText = txt;
        mIndex = txt.length();

        setText(txt);
        mHandler.removeCallbacks(characterAdder);
        mHandler.removeCallbacks(characterRemover);
        mHandler.postDelayed(characterRemover, mDelay);
    }

    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}
