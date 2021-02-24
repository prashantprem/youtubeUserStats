package com.example.youtubeuserstats;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InfoActivity extends AppCompatActivity {

    String YT_URL = "https://youtube.googleapis.com/youtube/v3/search?type=channel&part=snippet&channelType=any&maxResults=5&q="+MainActivity.str+"&key=[API_Key]";
    private YouTubeAdapter mAdapter;
    List<ChannelDetails> result;
    public static String CID;
    public static  String urlimg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ListView channelListView = (ListView) findViewById(R.id.list);
        mAdapter = new YouTubeAdapter(this, new ArrayList<ChannelDetails>());
        channelListView.setAdapter(mAdapter);
         ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                     result = QueryUtils.fetchChannelData(YT_URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        if(result != null && !result.isEmpty())
                        {
                            mAdapter.addAll(result);
                        }
                    }
                });
            }
        });

        channelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChannelDetails currentChannel = mAdapter.getItem(i);
                assert currentChannel != null;
                CID = currentChannel.getChannelId();
                urlimg=currentChannel.getUrl();
                Intent detailedInfoIntent = new Intent(InfoActivity.this, detailedInfoActivity.class);
                startActivity(detailedInfoIntent);

            }
        });




    }
}






