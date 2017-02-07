package com.gturedi.flickr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by gturedi on 7.02.2017.
 */
public class FlickrService {

    private static final String API_KEY = "04a42d236e746206fbbf64245342dd2d";
    private static final String URL_BASE = "https://api.flickr.com/services/rest/?format=json&nojsoncallback=1&api_key="+API_KEY;
    private static final String URL_SEARCH = "&method=flickr.photos.search&tags=mode&per_page=15";
    private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    public void searchAsync() {
        Request request = new Request.Builder()
                .url(URL_BASE+URL_SEARCH)
                .build();
        try {
            Response response = getClient().newCall(request).execute();
            String resp = response.body().string();

            Type listType = new TypeToken<ArrayList<PhotoModel>>(){}.getType();
            List<PhotoModel> items = new Gson().fromJson(resp, listType);
            EventBus.getDefault().post(new SearchEvent(items, null));
        } catch (IOException e) {
            Timber.e(e);
            EventBus.getDefault().post(new SearchEvent(null, e));
        }
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(new Cache(new File(""), CACHE_SIZE))
                .build();
    }

}