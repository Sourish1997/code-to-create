package acm.event.codetocreate17.Authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import acm.event.codetocreate17.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
