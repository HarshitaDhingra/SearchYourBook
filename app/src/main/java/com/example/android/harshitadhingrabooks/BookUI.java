package com.example.android.harshitadhingrabooks;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookUI extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private TextView mEmptyStateTextView;
    private static final int BOOKS_LOADER_ID =1;
    private String Title;
    private int minNumberOfResources = 0;
    private static final int maxNumberOfResources = 10;
    private BAdapter mAdapter;
    private int i=0;
    private static String BOOKS_REQUEST_URL =" ";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_book);
        Intent intent = getIntent();
        Title= intent.getStringExtra("title");
        if (!Title.equals("")) {
            Title = "+intitle:" + Title;
        }
        else {
            Title =" ";
        }
        BOOKS_REQUEST_URL="https://www.googleapis.com/books/v1/volumes?q="+Title+"&startIndex="+ String.valueOf(minNumberOfResources)+"&maxResults="+String.valueOf(maxNumberOfResources);
        ListView bookListview = (ListView) findViewById(R.id.list);
        mEmptyStateTextView =(TextView) findViewById(R.id.empty_view);
        bookListview.setEmptyView(mEmptyStateTextView);
        mAdapter = new BAdapter(this, new ArrayList<Book>());
        bookListview.setAdapter(mAdapter);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null&&activeNetwork.isConnected())
        { LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOKS_LOADER_ID, null, this);}
        else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }
    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        BLoader e=new BLoader(this, BOOKS_REQUEST_URL);
        return e;
    }
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        ProgressBar p=(ProgressBar)findViewById(R.id.loading_spinner);
        p.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_books);
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}