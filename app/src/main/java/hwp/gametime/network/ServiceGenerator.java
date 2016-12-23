package hwp.gametime.network;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by dusan_cvetkovic on 11/23/16.
 */

public class ServiceGenerator {

    private static String TAG = ServiceGenerator.class.getSimpleName();
    public static final String API_BASE_URL = "https://api.stattleship.com/basketball/nba/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass() == RealmObject.class;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (authToken != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/vnd.stattleship.com; version=1")
                            .addHeader("Authorization", "Token token=" + authToken)
                            .addHeader("Content-Type", "application/json")
                            .method(original.method(), original.body());

//                    Log.d(TAG, "intercept orig url: " + original.url());
                    Request request = requestBuilder.build();
//                    Log.d(TAG, "intercept built url: " + request.url());
//                    Log.d(TAG, "intercept built url: " + request.headers());
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }
}
