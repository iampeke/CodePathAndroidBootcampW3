package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "ezCgr57J2PuwvTHaSZemqTa3M";       // Change this
	public static final String REST_CONSUMER_SECRET = "0wTZBDaon20jlz4EulMww9wZOQFy1UYwb9ECEJreODajNrhL3P"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	// Each time we want new data from the Twitter API we create a new method here:
	// handler represents the callback
	
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		// Fully qualified API URL:
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		// Actually send request and fire success / failure callbacks leveraged by handler
		// GET and POST chosen based on API; do not mismatch these...
		// If you don't have any parameters, pass NULL!
		client.get(apiUrl, params, handler);
	}
	
	public void getHomeTimeline(AsyncHttpResponseHandler handler, int page) {
		// Fully qualified API URL:
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		params.put("page", page + "");
		// Actually send request and fire success / failure callbacks leveraged by handler
		// GET and POST chosen based on API; do not mismatch these...
		// If you don't have any parameters, pass NULL!
		client.get(apiUrl, params, handler);
	}
	
	public void postNewTweet(AsyncHttpResponseHandler handler, String tweetText) {
		// Fully qualified API URL:
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweetText);
		// Actually send request and fire success / failure callbacks leveraged by handler
		// GET and POST chosen based on API; do not mismatch these...
		// If you don't have any parameters, pass NULL!
		client.post(apiUrl, params, handler);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}
	*/

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}