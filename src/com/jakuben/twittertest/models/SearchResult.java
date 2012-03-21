package com.jakuben.twittertest.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String JSON_IMAGE_URL  = "image_url";
	private static final String JSON_USER_ID    = "user_id";
	private static final String JSON_TIMESTAMP  = "timestamp";
	private static final String JSON_TWEET_TEXT = "tweet_text";
	
	@SerializedName("created_at")        private String mCreatedAt;
	@SerializedName("from_user")         private String mFromUser;
	@SerializedName("from_user_id")      private String mFromUserId;
	@SerializedName("from_user_id_str")  private String mFromUserIdStr;
	@SerializedName("from_user_name")    private String mFromUserName;
	@SerializedName("geo")               private String mGeo;
	@SerializedName("id")                private String mId;
	@SerializedName("id_str")            private String mIdStr;
	@SerializedName("iso_language_code") private String mIsoLanguageCode;
    @SerializedName("profile_image_url") private String mProfileImageUrl;
	@SerializedName("source")            private String mSource;
	@SerializedName("text")              private String mTweetText;
	@SerializedName("to_user")           private String mToUser;
	@SerializedName("to_user_id")        private String mToUserId;
	@SerializedName("to_user_id_str")    private String mToUserIdStr;
	@SerializedName("to_user_name")      private String mToUserName;
	
	public SearchResult(JSONObject json) throws JSONException {
		mProfileImageUrl = json.getString(JSON_IMAGE_URL);
		mFromUser = json.getString(JSON_USER_ID);
		mCreatedAt = json.getString(JSON_TIMESTAMP);
		mTweetText = json.getString(JSON_TWEET_TEXT);
	}
	
	public String getCreatedAt() { return mCreatedAt; }
	
	public String getFromUser() { return mFromUser; }
	
	public String getFromUserId() { return mFromUserId; }
	
	public String getFromUserIdStr() { return mFromUserIdStr; }
	
	public String getFromUserName() { return mFromUserName; }

	public String getGeo() { return mGeo; }
	
	public String getId() { return mId; }
	
	public String getIdStr() { return mIdStr; }
	
	public String getIsoLanguageCode() { return mIsoLanguageCode; }
	
	public String getSource() { return mSource; }
	
	public String getTweetText() { return mTweetText; }
	
	public String getToUser() { return mToUser; }
	
	public String getToUserId() { return mToUserId; }
	
	public String getToUserIdStr() { return mToUserIdStr; }
	
	public String getToUserName() { return mToUserName;	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_IMAGE_URL, mProfileImageUrl);
		json.put(JSON_USER_ID, mFromUser);
		json.put(JSON_TIMESTAMP, mCreatedAt);
		json.put(JSON_TWEET_TEXT, mTweetText);
		
		return json;
	}
}