package acm.event.codetocreate17.Model.RetroAPI;

/**
 * Created by Sourish on 28-03-2017.
 */

import com.google.gson.JsonObject;

import java.util.ArrayList;

import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class RetroAPI{
    public interface ObservableAPIService {
        @FormUrlEncoded
        @POST("session/create")
        Observable<JsonObject> signIn (
                @Field("email") String unameoremail,
                @Field("password") String password
        );

        @FormUrlEncoded
        @POST("team/myteam")
        Observable<JsonObject> syncProfile (
                @Field("token") String accessToken
        );

        @FormUrlEncoded
        @POST("quiz/create")
        Observable<JsonObject> startQuiz (
                @Field("token") String id,
                @Field("startTime") long startTime,
                @Field("qArray[]") ArrayList<Integer> questionArray
        );

        @FormUrlEncoded
        @POST("quiz/getData")
        Observable<JsonObject> getQuizData (
                @Field("token") String id
        );

        @FormUrlEncoded
        @POST("quiz/update")
        Observable<JsonObject> updateQuizData (
                @Field("token") String id,
                @Field("lastQ") int lastQuestion,
                @Field("marks") int marks
        );

        @FormUrlEncoded
        @POST("quiz/finishQuiz")
        Observable<JsonObject> finishQuiz (
                @Field("token") String id,
                @Field("finishTime") long finishTime,
                @Field("marks") int marks
        );

    }

    public ObservableAPIService observableAPIService = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(ObservableAPIService.class);
}
