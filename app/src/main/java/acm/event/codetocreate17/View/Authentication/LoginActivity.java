package acm.event.codetocreate17.View.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import acm.event.codetocreate17.Model.RealmModels.TeamMember;
import acm.event.codetocreate17.Model.RealmModels.User;
import acm.event.codetocreate17.Model.RetroAPI.RetroAPI;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Utils.Constants;
import acm.event.codetocreate17.View.Main.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_username)
    EditText usernameEditText;
    @BindView(R.id.login_password)
    EditText passwordEditText;
    @BindView(R.id.login_username_layout)
    TextInputLayout usernameLayout;
    @BindView(R.id.login_password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.login_root_layout)
    ConstraintLayout loginContainer;

    RetroAPI retroAPI;
    SharedPreferences.Editor sharedPreferencesEditor;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        retroAPI = new RetroAPI();
        sharedPreferencesEditor = this.getSharedPreferences(Constants.sharedPreferenceName, Context.MODE_PRIVATE).edit();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.link_guest_login)
    public void onGuestLogin(View v) {
        loadMain();
    }

    @OnClick(R.id.login_button)
    public void onUserLogin(View v) {
        signin();
    }

    public void signin(){
        Log.e("message", "Inside sign in");
        loginButton.setText("SIGNING IN...");
        loginButton.setClickable(false);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Log.e("message", username);
        Log.e("message", password);
        retroAPI.observableAPIService.signIn(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loginButton.setText("SIGN IN");
                        loginButton.setClickable(true);
                        Snackbar snackbar = Snackbar
                                .make(loginContainer, "Could not connect to server", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        signin();
                                    }
                                });

                        snackbar.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Log.e("message", "Inside on next");
                        if(jsonObject.get("success").getAsBoolean()){
                            Log.e("message", jsonObject.get("success").getAsString());
                            sharedPreferencesEditor.putBoolean("loggedin",true);
                            sharedPreferencesEditor.putString("authtoken",jsonObject.get("token").getAsString());
                            sharedPreferencesEditor.commit();
                            Constants.accessToken = jsonObject.get("token").getAsString();
                            User user = new User();
                            realm.beginTransaction();
                            user.name = jsonObject.getAsJsonObject("user").get("name").getAsString();
                            user.email = jsonObject.getAsJsonObject("user").get("email").getAsString();
                            user.gender = jsonObject.getAsJsonObject("user").get("gender").getAsString();
                            realm.copyToRealmOrUpdate(user);
                            realm.commitTransaction();
                            syncProfile();
                        } else {
                            usernameLayout.setError("Enter valid username");
                            passwordLayout.setError("Enter valid password");
                            loginButton.setText("SIGN IN");
                            loginButton.setClickable(true);
                        }
                    }
                });
    }

    public void syncProfile() {
        String accessToken = Constants.accessToken;
        Log.e("message", accessToken);
        retroAPI.observableAPIService.syncProfile(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loginButton.setText("SIGN IN");
                        loginButton.setClickable(true);
                        Snackbar snackbar = Snackbar
                                .make(loginContainer, "Could not connect to server", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        signin();
                                    }
                                });
                        snackbar.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if(jsonObject.get("success").getAsBoolean()){
                            Log.e("message", "Inside syncProf");
                            User user = realm.where(User.class).findFirst();
                            realm.beginTransaction();
                            user.hasTeam = true;
                            String leader = jsonObject.get("ADMIN").getAsString();
                            user.isLeader = jsonObject.get("admin").getAsBoolean();
                            user.teamName = jsonObject.get("teamName").getAsString();
                            JsonArray teamMembers = jsonObject.getAsJsonArray("teammembers");
                            JsonArray teamMemberEmails = jsonObject.getAsJsonArray("teammembersemail");
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
                        } else {
                            User user = realm.where(User.class).findFirst();
                            realm.beginTransaction();
                            user.hasTeam = false;
                            realm.copyToRealmOrUpdate(user);
                            realm.commitTransaction();
                        }
                        loadMain();
                    }
                });
    }

    public void loadMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
