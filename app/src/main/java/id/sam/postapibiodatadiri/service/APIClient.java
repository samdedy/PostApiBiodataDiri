package id.sam.postapibiodatadiri.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 1/10/2018.
 */

public class APIClient {


   public static String BASE_URL ="http://192.168.43.155/cicool/api/";
   public static String API_KEY = "5D6197C546B7C830D69655A692B1A6AC";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder().addHeader("X-Api-Key", APIClient.API_KEY).build();
                return chain.proceed(newRequest);
            }
        };


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        //OkHttpClient client = new OkHttpClient.Builder().build();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        gb.serializeNulls();
       // gb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gb.create();


        retrofit = new Retrofit.Builder()
                .baseUrl(APIClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

                .client(client)
                .build();



        return retrofit;
    }

}
