package com.jakuben.twittertest;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakuben.twittertest.models.SearchResult;


public class SearchResultAdapter extends ArrayAdapter<SearchResult> {
	
	protected Context mContext;
	protected ArrayList<SearchResult> mResults;

	public SearchResultAdapter(Context context, ArrayList<SearchResult> results) {
		super(context, android.R.layout.simple_list_item_1, results);
		mContext = context;
		mResults = results;
	}
	
	public void setResults(ArrayList<SearchResult> results) {
		mResults = results;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.search_result, null);
		}
			
		SearchResult result = mResults.get(position);
			
		ImageView userImageView = (ImageView)convertView.findViewById(R.id.user_image_view);
		userImageView.setBackgroundColor(android.R.color.darker_gray);
			
		TextView userNameTextView = (TextView)convertView.findViewById(R.id.user_name_text_view);
		userNameTextView.setText("@" + result.getFromUser());
			
		TextView timestampTextView = (TextView)convertView.findViewById(R.id.timestamp_text_view);
		timestampTextView.setText(result.getCreatedAt());
			
		TextView tweetTextView = (TextView)convertView.findViewById(R.id.tweet_text_view);
		tweetTextView.setText(result.getTweetText());
		
		return convertView;
	}
}
