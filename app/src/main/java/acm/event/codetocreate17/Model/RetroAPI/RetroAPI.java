package acm.event.codetocreate17.Model.RetroAPI;

/**
 * Created by Sourish on 28-03-2017.
 */

import com.google.gson.JsonObject;

import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
        Observable<JsonObject> syncProfile(
                @Field("id") String accessToken
        );
    }

    public interface ValidityService {
        @FormUrlEncoded
        @POST("session/checktoken")
        Call<ResponseBody> getAccessToken(
                @Field("id") String authToken
        );
    }

    public ObservableAPIService observableAPIService = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(ObservableAPIService.class);

    public ValidityService validityService = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValidityService.class);
}
