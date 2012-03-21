package com.jakuben.twittertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

import com.jakuben.twittertest.models.SearchResult;

public class ResultsJSONSerializer {

	private Context mContext;
	private String mFilename;
	
	public ResultsJSONSerializer(Context context, String filename) {
		mContext = context;
		mFilename = filename;
	}
	
	public void saveResults(ArrayList<SearchResult> results) throws JSONException, IOException {
		JSONArray array = new JSONArray();
		for (SearchResult result : results) {
			array.put(result.toJSON());
		}
		
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	public ArrayList<SearchResult> loadResults() throws JSONException, IOException {
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		BufferedReader reader = null;
		
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				// omit line breaks
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				results.add(new SearchResult(array.getJSONObject(i)));
			}
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		return results;
	}
}
