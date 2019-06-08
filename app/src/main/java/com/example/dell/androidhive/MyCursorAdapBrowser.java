package com.example.dell.androidhive;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCursorAdapBrowser extends CursorAdapter {

        private LayoutInflater cursorInflater;

        // Default constructor
        public MyCursorAdapBrowser(Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
            cursorInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }
//
        final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
        final String[] HISTORY_PROJECTION = new String[]{
            "_id", // 0
            "url", // 1
            "visits", // 2
            "date", // 3
            "bookmark", // 4
            "title", // 5
            "favicon", // 6
            "thumbnail", // 7
            "touch_icon", // 8
            "user_entered", // 9
            };
        final int HISTORY_PROJECTION_DATE_INDEX = 3;
        final int HISTORY_PROJECTION_TITLE_INDEX = 5;
        final int HISTORY_PROJECTION_URL_INDEX = 1;
        final int HISTORY_PROJECTION_FAVICON_INDEX= 6;
        final int HISTORY_PROJECTION_BOOKMARK_INDEX = 4;
        final int HISTORY_PROJECTION_VISITS_INDEX = 2;
        final int HISTORY_PROJECTION_ID_INDEX = 0;


    @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView tvDate = view.findViewById(R.id.date);
            TextView tvTitle = view.findViewById(R.id.title);
            TextView tvBoV = view.findViewById(R.id.bov);
            TextView tvURL = view.findViewById(R.id.url);
            ImageView ivFavicon = view.findViewById(R.id.favicon);

//              long date = cursor.getLong(Browser.SEARCHES_PROJECTION_DATE_INDEX);
//              String title = cursor.getString(Browser.SEARCHES_PROJECTION_SEARCH_INDEX);

            long date = cursor.getLong(HISTORY_PROJECTION_DATE_INDEX);
            String title = cursor.getString(HISTORY_PROJECTION_TITLE_INDEX);
            String url = cursor.getString(HISTORY_PROJECTION_URL_INDEX);
            byte[] icon = cursor.getBlob(HISTORY_PROJECTION_FAVICON_INDEX);
            String bokmrk = cursor.getString(HISTORY_PROJECTION_BOOKMARK_INDEX);
            String visits = cursor.getString(HISTORY_PROJECTION_VISITS_INDEX);
            String id = cursor.getString(HISTORY_PROJECTION_ID_INDEX);

            //  String names[] = cursor.getColumnNames();
            tvDate.setText(new Date(date).toString());
            tvBoV.setText(bokmrk.equals("1")?"Bookmarked":"Visited");
            tvTitle.setText(title);
            tvURL.setText(url);
            if(icon != null){
                Bitmap bm = BitmapFactory.decodeByteArray(icon, 0, icon.length);
                ivFavicon.setImageBitmap(bm);
            }

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return cursorInflater.inflate(R.layout.linkbrowser, parent, false);
        }


}
