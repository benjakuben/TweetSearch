package com.jakuben.twittertest;

import roboguice.activity.RoboListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.jakuben.twittertest.models.SearchResponse;
import com.jakuben.twittertest.services.TwitterSearch;

public class MainActivity extends RoboListActivity {

	@Inject protected TwitterSearch mSearchService;
	
	protected EditText mSearchEditText;
	protected Button mSearchButton;
	protected Button mRefreshButton;
	protected SearchResultAdapter mAdapter;
	protected SearchResponse mSearchResponse;
	protected TwitterTestApplication mApplication;
	protected ProgressDialog mProgressDialog;
	protected boolean mIsSearching;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mApplication = (TwitterTestApplication)getApplication();
		mProgressDialog = new ProgressDialog(this);
        
        mSearchButton = (Button)findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(mSearchButtonOnClick);
        
        mRefreshButton = (Button)findViewById(R.id.refresh_button);
        mRefreshButton.setOnClickListener(mRefreshButtonOnClick);
        mRefreshButton.setVisibility(View.GONE);
        
        mSearchEditText = (EditText)findViewById(R.id.search_edit_text);
    	initializeAdapter();
    	
    	TextView emptyTextView = (TextView)findViewById(android.R.id.empty);
    	if (mApplication.getResults().size() == 0) {
    		emptyTextView.setVisibility(View.GONE);
    	}
    }
    
    @Override
    public void onStop() {
    	((TwitterTestApplication)getApplication()).saveResults();
    	super.onStop();
    }
    
    protected void initializeAdapter() {
    	mAdapter = new SearchResultAdapter(MainActivity.this, mApplication.getResults());
		setListAdapter(mAdapter);
    }
    
    protected void hideKeyboard() {
    	InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    	inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    
    protected void initializeSearch(String progressLabel) {
    	mIsSearching = true;
    	mProgressDialog = ProgressDialog.show(MainActivity.this, "", progressLabel, true);
    	mRefreshButton.setVisibility(View.GONE);
    }

    protected void showSearchError() {
    	Toast.makeText(this, "There was an error performing your search.", Toast.LENGTH_LONG).show();
    }
    
    protected OnClickListener mSearchButtonOnClick = new OnClickListener() {
		public void onClick(View view) {
			hideKeyboard();
			initializeSearch(getString(R.string.search_hud));
			mSearchService.searchTwitter("?q=" + mSearchEditText.getText().toString(), mSearchHandler);
		}
    };
    
    protected OnClickListener mRefreshButtonOnClick = new OnClickListener() {
    	public void onClick(View view) {
			initializeSearch(getString(R.string.refresh_hud));
			mSearchService.searchTwitter(mSearchResponse.getRefreshUrl(), mSearchHandler);
    	}
    };
    
    protected Handler mSearchHandler = new Handler() {
		@Override
        public void handleMessage(Message message) {
        	mProgressDialog.dismiss();
        	
        	switch(message.what){
        		case -1:  // Service call timed out
        			if (mIsSearching) {
        				mIsSearching = false;
        	        	showSearchError();
        			}
        			break;
        		default:  // Service call returned results or error
        			if (mIsSearching) {
	        			mIsSearching = false;
	                	if (message.obj == null || message.obj.getClass().equals(Exception.class)) {
	                		showSearchError();
	        	        }
	                	else {
	                		mSearchResponse = (SearchResponse)message.obj;
	                		mRefreshButton.setVisibility(View.VISIBLE);
	                		mApplication.setResults(mSearchResponse.getResults());
	                		initializeAdapter();
	                	}
        			}
        			break;
        	}
        }
    };
}