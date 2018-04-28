package com.example.android.harshitadhingrabooks;

/**
 * Created by PC on 29-07-2017.
 */
public class Book {
    private String mTitle;
    private String mAuthor;
    private String mDescription;
    public String mImageUrl;
    public Book(String a, String  b, String c, String i)
    {
        mTitle=a;
        mAuthor=b;
        mDescription=c;
        mImageUrl=i;
    }
    public String getTitle(){return mTitle;}
    public String getAuthor(){return mAuthor;}
    public String getDescription(){return mDescription;}
    public String getImageUrl(){return mImageUrl;}
}
