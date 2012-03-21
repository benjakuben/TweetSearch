package com.jakuben.twittertest.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {
	
	private ArrayList<SearchResult> results;
	
	@SerializedName("completed_in")     private String mCompletedIn;
	@SerializedName("max_id")           private String mMaxId;
	@SerializedName("max_id_str")       private String mMaxIdStr;
	@SerializedName("next_page")        private String mNextPage;
	@SerializedName("page")             private String mPage;
	@SerializedName("query")            private String mQuery;
	@SerializedName("refresh_url")      private String mRefreshUrl;
	@SerializedName("results_per_page") private String mResultsPerPage;
	@SerializedName("since_id")         private String mSinceId;
	@SerializedName("since_id_str")     private String mSinceIdStr;
	
	public static SearchResponse create(JSONObject response) throws JSONException {
		Gson gson = new Gson();
		return gson.fromJson(response.toString(), SearchResponse.class);
	}
	
	public ArrayList<SearchResult> getResults() { return results; }
	
	public String getQuery() { return mQuery; }
	
	public String getCompletedIn() { return mCompletedIn; }
	
	public String getMaxId() { return mMaxId; }
	
	public String getMaxIdStr() { return mMaxIdStr; }
	
	public String getNextPage() { return mNextPage; }
	
	public String getPage() { return mPage; }
	
	public String getRefreshUrl() { return mRefreshUrl; }
	
	public String getResultsPerPage() { return mResultsPerPage; }
	
	public String getSinceId() { return mSinceId; }
	
	public String getSinceIdStr() { return mSinceIdStr; }
}
