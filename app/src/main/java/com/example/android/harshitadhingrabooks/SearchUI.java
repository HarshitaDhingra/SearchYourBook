package com.example.android.harshitadhingrabooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class SearchUI extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_search);
        ((Button) findViewById(R.id.searchbutton)).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        EditText Title=(EditText) findViewById(R.id.title);
        if(v.getId()== R.id.searchbutton){
            String tit= modifyText(Title.getText().toString());
            Intent intent = new Intent(SearchUI.this,BookUI.class);
            intent.putExtra("title",tit);
            startActivity(intent);
        }
        else{
            Title.setText("");
        }
    }
    private String modifyText(String modify){
        String newString = modify.trim();
        do {
            newString = newString.replace(" ", "+");
        } while (newString.contains(" "));
        return newString;
    }
}
