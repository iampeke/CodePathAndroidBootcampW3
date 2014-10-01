package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class NewTweetActivity extends Activity {
	private TwitterClient client;
	private Tweet newTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);
		client = TwitterApplication.getRestClient();
		newTweet = new Tweet();
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
        if (id == R.id.miSubmitTweet) {
            return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.submit_tweet, menu);
        return true;
	}
	/*
	public void postNewTweet(String newTweetText) {
		client.postNewTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject resp) {
				newTweet = Tweet.fromJson(resp);
				Toast.makeText(NewTweetActivity.this, "Post Success: " + newTweet.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(NewTweetActivity.this, "New Tweet Post Failure", Toast.LENGTH_SHORT).show();
			}
		}, newTweetText);
	}
	*/
	
	public void onSubmitTweetMenuItem(MenuItem mi) {
		// Get the text from the text field for the new tweet:
		EditText etNewTweet = (EditText) findViewById(R.id.etNewTweet);
		String newTweetText = etNewTweet.getText().toString();
		//postNewTweet(newTweetText);
		Intent i = new Intent();
        i.putExtra("newTweetText", newTweetText);
        //i.putExtra(SearchActivity.COLOR_FILTER, spColorFilterValue.getSelectedItem().toString());
        //i.putExtra(SearchActivity.TYPE_FILTER, spTypeFilterValue.getSelectedItem().toString());
        //i.putExtra(SearchActivity.SITE_FILTER, etSiteFilterValue.getText().toString());
        setResult(RESULT_OK, i);
        finish();
	}
}
