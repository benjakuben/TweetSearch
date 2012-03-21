package com.jakuben.twittertest;

import com.google.inject.AbstractModule;
import com.jakuben.twittertest.services.TwitterSearch;
import com.jakuben.twittertest.services.TwitterSearchImpl;

public class ServiceInjectionModule extends AbstractModule {
	
	@Override 
	protected void configure() {
		bind(TwitterSearch.class).to(TwitterSearchImpl.class);
	}
}