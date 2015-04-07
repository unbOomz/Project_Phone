package com.example.retrievedata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FouthActivity extends Activity{	 
	
	private ListView listPromotion;
	private TextView Value;
	private String strReciveSpinner;
	private String strReciveSpinner1;
	private Double strReciveSpinner2;
	private String pro_id;		
	private double val1, val5;
	private float val2;
	private int val3, val4;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fouth_activity);
		
		listPromotion = (ListView)findViewById(R.id.listView1);
		Value = (TextView)findViewById(R.id.ColValue);
		
		strReciveSpinner = getIntent().getStringExtra("strSpin");
		pro_id = getIntent().getStringExtra("pro_id");
		
		String url = "http://10.0.2.2:85/Proj/select2.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pro_id", pro_id));
		
		try {
	    	JSONArray data = new JSONArray(getJSONUrl(url, params));
	    	
	    	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	    	HashMap<String, String> map = null;
	    	
	    	final ArrayList<HashMap<String, String>> MyArrList2 = new ArrayList<HashMap<String, String>>();
	    	
	    	
	    	for(int i = 0; i < data.length(); i++){
	    		JSONObject c = data.getJSONObject(i);   
	    		
	    		map = new HashMap<String, String>();
	    		map.put("promo_id", c.getString("promo_id"));
	    		map.put("name", c.getString("name"));	  	    		
	    		map.put("price", c.getString("price"));
	    		map.put("3G_Net", c.getString("3G_Net"));
	    		map.put("sms", c.getString("sms"));
	    		map.put("mms", c.getString("mms"));
	    		map.put("time", c.getString("time"));
	    		map.put("pro_id", c.getString("pro_id"));	    		
	    		
				val1 = Double.parseDouble(map.get("price"));
				val2 = Float.parseFloat(map.get("3G_Net"));
				val3 = Integer.parseInt(map.get("sms"));
				val4 = Integer.parseInt(map.get("mms"));
				val5 = Double.parseDouble(map.get("time"));
				
				reciveValue();	
				
				map.put("value", strReciveSpinner1);				
					    		
	    		MyArrList.add(map);	  	    		
	    	}	    	
	    	/*Iterator<String> Vmap = map.keySet().iterator();
	    	while(Vmap.hasNext()){
	    		.......
	    	}*/
	    	
	    	SimpleAdapter adapter = new SimpleAdapter(FouthActivity.this, MyArrList, R.layout.activity_column2, new String[] {"name","value"}, new int[] {R.id.ColName, R.id.ColValue});
	    	listPromotion.setAdapter(adapter);
	    	
	    }	catch (JSONException e) {		
			e.printStackTrace();
	    }
	}

	private void reciveValue() {
		// TODO Auto-generated method stub		
		if(strReciveSpinner.equalsIgnoreCase("Price")){
			strReciveSpinner2 = val1/val1;
			strReciveSpinner1 = String.format("%.3f", strReciveSpinner2);
			//strReciveSpinner1 = String.valueOf(strReciveSpinner2);			
		}else if(strReciveSpinner.equalsIgnoreCase("Time")){
			strReciveSpinner2 = val1/val5;
			//strReciveSpinner1 = String.format("%.3f", strReciveSpinner2) + "  Baht/Minute";
			strReciveSpinner1 = String.format("%.3f", strReciveSpinner2);
		}else if(strReciveSpinner.equalsIgnoreCase("Net")){
			strReciveSpinner2 = val1/val2;
			//strReciveSpinner1 = String.format("%.3f", strReciveSpinner2) + "  Baht/GB";
			strReciveSpinner1 = String.format("%.3f", strReciveSpinner2);
		}else if(strReciveSpinner.equalsIgnoreCase("SMS")){
			strReciveSpinner2 = val1/val3;
			//strReciveSpinner1 = String.format("%.3f", strReciveSpinner2) + "  Baht/SMS";
			strReciveSpinner1 = String.format("%.3f", strReciveSpinner2);
		}else if(strReciveSpinner.equalsIgnoreCase("MMS")){
			strReciveSpinner2 = val1/val4;
			//strReciveSpinner1 = String.format("%.3f", strReciveSpinner2) + "  Baht/MMS";
			strReciveSpinner1 = String.format("%.3f", strReciveSpinner2);
		}else{			
			//strReciveSpinner1 = "Out of Value";
		}
	}
	
	public String getJSONUrl(String url, List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Download OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
}
