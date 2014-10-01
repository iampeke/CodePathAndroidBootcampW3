package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		populateTimeline();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
	}
	
	public void customLoadMoreDataFromApi(int offset) {
        populateTimeline(offset);
    }

	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void populateTimeline(int page) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, page);
	}
	
	public void postNewTweet(String newTweetText) {
		client.postNewTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject resp) {
				tweets.add(0, Tweet.fromJson(resp));
				aTweets.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(TimelineActivity.this, "Tweet not posted", Toast.LENGTH_SHORT).show();
			}
		}, newTweetText);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.miCreateTweet) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_tweet, menu);
		return true;
	}
	
	public void onCreateTweetMenuItem(MenuItem mi) {
		//Toast.makeText(this, "Create Tweet Clicked", Toast.LENGTH_SHORT).show();
		
		// Create the intent:
        Intent i = new Intent(this, NewTweetActivity.class); // explicit intent

        //Define the parameters ("extras" encoded into a bundle):
        //i.putExtra(SIZE_FILTER, sizeFilterValue);
        //i.putExtra(COLOR_FILTER, colorFilterValue);
        //i.putExtra(TYPE_FILTER, typeFilterValue);
        //i.putExtra(SITE_FILTER, siteFilterValue);

        // Execute the intent:
        startActivityForResult(i, 50); //Last number could be a constant that describes that actual payload type
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 50) { // Check for the correct payload
            if (resultCode == RESULT_OK) { // Checks if the request was correct
                //Toast.makeText(this, "Filter Changed", Toast.LENGTH_SHORT).show();
                String newTweetText = data.getStringExtra("newTweetText");
                postNewTweet(newTweetText);
                //Toast.makeText(this, tweets.get(tweets.size()).toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
