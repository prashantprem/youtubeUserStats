package com.example.youtubeuserstats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class YouTubeAdapter extends ArrayAdapter<ChannelDetails> {
    int tempurl;
    Bitmap bmp = null;

    public YouTubeAdapter(Context context, List<ChannelDetails> channelDetails)
    {
        super(context,0, channelDetails);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }
        final ChannelDetails currentChannel = getItem(position);
        TextView nameView = listItemView.findViewById(R.id.textViewUsername);
        assert currentChannel != null;
        nameView.setText(currentChannel.getName());
        TextView descriptionView = (TextView) listItemView.findViewById(R.id.textViewDescription);
        descriptionView.setText(currentChannel.getDescription());
        Log.d("ADebugTag", "Value: " + currentChannel.getDescription());
         final ImageView imageyt = (ImageView) listItemView.findViewById(R.id.imageView3);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(currentChannel.getUrl());
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

                        imageyt.setImageBitmap(finalBmp);
                        imageyt.setVisibility(View.VISIBLE);

                    }
                });
            }
        });






//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                URL url = null;
//                try {
//                    url = new URL(tempurl);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    assert url != null;
//                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                // Do network action in this function
//            }
//        }).start();





        return listItemView;


    }

}
