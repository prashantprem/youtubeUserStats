package com.example.youtubeuserstats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class detailedInfoActivity extends AppCompatActivity {

    String detailed_YT_URL =  "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&id="+InfoActivity.CID+"&key=AIzaSyAk3W8od0lp8hbiqbTJgn7HWXDyDQA6cSo";
    String jsonResponse = null;
    private int mSubCount;
    private int mVideoCount;
    private int mViewCount;
    private String mDetailedDescription;
    JSONObject detailedJsonObject = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item2);
        final ImageView detailedImage = findViewById(R.id.detailedImageView);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(InfoActivity.urlimg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                try {
                    assert url != null;
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Bitmap finalBmp = bmp;
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        detailedImage.setImageBitmap(finalBmp);
                        detailedImage.setVisibility(View.VISIBLE);

                    }
                });
            }
        });



//        network request for further more details
        final TextView subCountTextView = findViewById(R.id.subcount);
        final TextView viewCountTextView = findViewById(R.id.viewCount);
        final TextView videoCountTextView = findViewById(R.id.videoCount);
        final TextView descriptionTextView = findViewById(R.id.detailedDescription);
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        final Handler handler2 = new Handler(Looper.getMainLooper());
        executor2.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                URL url = null;
                try {
                    url = QueryUtils.createUrl(detailed_YT_URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    jsonResponse = QueryUtils.makeHttpRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    detailedJsonObject = new JSONObject(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray fullDetail = null;
                try {
                    fullDetail = detailedJsonObject.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject obj = fullDetail.getJSONObject(0).getJSONObject("statistics");
                    JSONObject obj2 = fullDetail.getJSONObject(0).getJSONObject("snippet");
                    mDetailedDescription = obj2.getString("description");
                    mSubCount = obj.getInt("subscriberCount");
                    mVideoCount = obj.getInt("videoCount");
                    mViewCount = obj.getInt("viewCount");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler2.post(new Runnable() {
                    @Override
                    public void run() {

                        descriptionTextView.setText(mDetailedDescription);
                        subCountTextView.setText("Subscribers : "+mSubCount);
                        videoCountTextView.setText("Videos : "+mVideoCount);
                        viewCountTextView.setText("Views : "+mViewCount);

                    }
                });
            }
        });







    }
}
