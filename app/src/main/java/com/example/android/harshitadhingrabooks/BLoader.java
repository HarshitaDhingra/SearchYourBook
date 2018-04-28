package com.example.android.harshitadhingrabooks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;
public class BLoader extends AsyncTaskLoader<List<Book>> {
    private static final String LOG_TAG = BLoader.class.getName();
    private String mUrl;
    public BLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"onStart");
        forceLoad();
    }
    @Override
    public List<Book> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground");
        if (mUrl == null) {
            return null;
        }
        List<Book> books = Utilities.fetchBookData(mUrl);
        return books;
    }
}
