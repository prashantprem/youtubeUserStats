package com.example.youtubeuserstats;

public class ChannelDetails {
    private String mDescription;
    private String mName;
    private String mUrl;
    private String mChannelId;

    public ChannelDetails(String name, String description, String url, String channelId)
    {
      mName=name;
      mDescription=description;
      mUrl=url;
      mChannelId=channelId;
    }
    public String getName(){ return mName;}
    public String getDescription(){ return mDescription;}
    public String getUrl(){ return mUrl;}
    public String getChannelId(){ return mChannelId;}

}
