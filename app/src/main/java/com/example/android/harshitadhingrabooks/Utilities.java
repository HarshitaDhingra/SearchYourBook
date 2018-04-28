package com.example.android.harshitadhingrabooks;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class Utilities {
    private Utilities() {}
    private static final String LOG_TAG = Utilities.class.getSimpleName();
    List<Book> books = new ArrayList<>();
    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<Book> books=extractFeatureFromJson(jsonResponse);
        return books;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static List<Book> extractFeatureFromJson(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        List<Book> books = new ArrayList<>();
        try {
            JSONObject root=new JSONObject(bookJSON);
            JSONArray items=root.getJSONArray("items");
            for(int i=0;i<items.length();i++)
            {
                JSONObject itemcount = items.getJSONObject(i);
                JSONObject volumeInfo = itemcount.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String author=" ";
                if(!volumeInfo.isNull("authors")){
                    JSONArray array = volumeInfo.getJSONArray("authors");
                    author = array.toString();
                }
                JSONObject image = null;
                String thumbnail=" ";
                if(!volumeInfo.isNull("imageLinks"))
                {
                     image=volumeInfo.getJSONObject("imageLinks");
                    if(!image.isNull("thumbnail"))
                        thumbnail =image.getString("thumbnail");
                }
                String description=" ";
                if(!volumeInfo.isNull("description"))
                    description = volumeInfo.getString("description");
                books.add(new Book(title,author,description,thumbnail));
            }
        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Book JSON results", e);
        }
        return books;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
