package com.jakuben.twittertest.services;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.jakuben.twittertest.models.SearchResponse;

public class TwitterSearchImpl implements TwitterSearch {
	
	public static final String SEARCH_RESULTS = "SEARCH_RESULTS";
	
	private static final String TAG = TwitterSearchImpl.class.getSimpleName();
	private static final String BASE_URL = "http://search.twitter.com/search.json";
	
	protected SearchThread mSearchThread;
	
	protected static TwitterSearchImpl mSharedInstance;
	
	public static TwitterSearchImpl sharedInstance() {
		if (mSharedInstance == null) {
			mSharedInstance = new TwitterSearchImpl();
		}
		return mSharedInstance;
	}

	public void searchTwitter(String query, Handler searchHandler) {
		String queryUrl = BASE_URL + query;
		mSearchThread = new SearchThread(queryUrl, searchHandler);
		mSearchThread.start();
	}
    
    private class SearchThread extends Thread {
    	private static final int TIMEOUT = 60000;  // milliseconds
    	
        private String mQueryUrl;
        private Handler mHandler;

        public SearchThread(String queryUrl, Handler handler) {
            mQueryUrl = queryUrl;
            mHandler = handler;
        }

        @Override
        public void run() {    
        	Looper.prepare();
			mHandler.sendEmptyMessageDelayed(-1, TIMEOUT);
        	Message message = new Message(); 
        	
			ClientResource clientResource = new ClientResource(mQueryUrl);
			try {
				String jsonResponse = clientResource.get(MediaType.APPLICATION_JSON).getText();
				SearchResponse response = SearchResponse.create(new JSONObject(jsonResponse));
				message.obj = response;
			}
			catch (JSONException je) {
				message.obj = je;
				Log.e(TAG, "JSONException: Unable to parse JSON response", je);
			}
			catch (IOException ioe) {
				message.obj = ioe;
				Log.e(TAG, "IOException getting search results", ioe);
			}
			catch (Exception e) {
				message.obj = e;
				Log.e(TAG, "Unknown exception caught", e);
			}
			
			mHandler.sendMessage(message);
            
            Looper.loop();
        }
    }
}
