package com.jakuben.twittertest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import roboguice.application.RoboApplication;
import android.util.Log;

import com.google.inject.Module;
import com.jakuben.twittertest.models.SearchResult;

public class TwitterTestApplication extends RoboApplication {
	
	public static final String FILENAME = "tt_results.json";
	
	private static final String TAG = TwitterTestApplication.class.getSimpleName();
	
	private ArrayList<SearchResult> mResults;
	private ResultsJSONSerializer mSerializer;

	public void onCreate() {
		super.onCreate();
		
		mResults = new ArrayList<SearchResult>();
		mSerializer = new ResultsJSONSerializer(this, FILENAME);
		
		try {
			Log.i(TAG, "Loading search results...");
			mResults = mSerializer.loadResults();
		}
		catch (JSONException joe) {
			Log.e(TAG, "Error loading previous results (JSONException)", joe);
		}
		catch (IOException ioe) {
			Log.e(TAG, "Error loading previous results (IOException)", ioe);
		}
	}

    protected void addApplicationModules(List<Module> modules) {
        modules.add(new ServiceInjectionModule());
    }
	
	public ArrayList<SearchResult> getResults() {
		return mResults;
	}
	
	public void setResults(ArrayList<SearchResult> results) {
		mResults = results;
	}
	
	public boolean saveResults() {
		try {
			mSerializer.saveResults(mResults);
			return true;
		}
		catch (JSONException joe) {
			Log.e(TAG, "Error saving results (JSONException)", joe);
		}
		catch (IOException ioe) {
			Log.e(TAG, "Error saving results (IOException)", ioe);
		}
		
		return false;
	}
}
