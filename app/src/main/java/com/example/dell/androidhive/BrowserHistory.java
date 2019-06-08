package com.example.dell.androidhive;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import static android.content.ContentValues.TAG;

public class BrowserHistory extends AppCompatActivity {

    ListView listView;
    Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_history);
        listView = findViewById(R.id.listViewbro);
        back_button = findViewById(R.id.button_Back);
        getBrowserHist();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowserHistory.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    public void getBrowserHist()  {
        Log.i(TAG, "getBrowserHist: ");
        final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
        Cursor cursor = getContentResolver().query(BOOKMARKS_URI, null, null, null, null);

        // Cursor cursor = getContentResolver().query(Browser.SEARCHES_URI, null, null, null, null);
        MyCursorAdapBrowser myCursorAdapter = new MyCursorAdapBrowser(this,cursor,0);
        listView.setAdapter(myCursorAdapter);
    }
}
