package com.jakuben.twittertest.services;

import android.os.Handler;

public interface TwitterSearch {
	
	public void searchTwitter(String query, Handler searchHandler);

}
