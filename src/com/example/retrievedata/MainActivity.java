package com.example.retrievedata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final ListView list = (ListView)findViewById(R.id.list);
    
        String url = "http://10.0.2.2:85/Proj/select1.php";
    
	    try {
	    	JSONArray data = new JSONArray(getJSONUrl(url));
	    	
	    	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	    	HashMap<String, String> map;
	    	
	    	for(int i = 0; i < data.length(); i++){
	    		JSONObject c = data.getJSONObject(i);   
	    		
	    		map = new HashMap<String, String>();
	    		map.put("product_id", c.getString("product_id"));
	    		map.put("band", c.getString("band"));
	    		map.put("gen", c.getString("gen"));
	    		map.put("capacity", c.getString("capacity"));
	    		map.put("color", c.getString("color"));
	    		//map.put("operator", c.getString("operator"));
	    		map.put("price", c.getString("price"));
	    		//map.put("price_promotion", c.getString("price_promotion"));
	    		
	    		MyArrList.add(map);    	    		
	    		
	    	}
	    	
	    	SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, MyArrList, R.layout.activity_column, new String[] {"band","gen","capacity"}, new int[] {R.id.ColBand, R.id.ColGen, R.id.ColCapacity});
	    	list.setAdapter(adapter);
	    	
	    	final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);	
	    	
	    	
	    	list.setOnItemClickListener(new OnItemClickListener() {
	    		public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
	    			
	    			Intent i = new Intent(MainActivity.this, SecondActivity.class);    			
	    			i.putExtra("jsonArray", MyArrList);
	    			i.putExtra("position", position);
	    			startActivity(i);
	    			
	    			/*String sProductID = MyArrList.get(position).get("pro_id").toString();
	    			String sBand = MyArrList.get(position).get("band").toString();
	    			String sGen = MyArrList.get(position).get("gen").toString();
	    			String sCapacity = MyArrList.get(position).get("capacity").toString();
	    			String sColor = MyArrList.get(position).get("color").toString();
	    			String sOperator = MyArrList.get(position).get("operator").toString();
	    			String sPrice = MyArrList.get(position).get("price").toString();
	    			String sTotal = MyArrList.get(position).get("price_promotion").toString();
	    		
	    			viewDetail.setIcon(android.R.drawable.btn_star_big_on);
	    			viewDetail.setTitle("Product Detail");
	    			viewDetail.setMessage("ProductID : " + sProductID + "\n" + "Band : " + sBand + "\n" + "Gen : " + sGen + "\n" + "Capacity : " + sCapacity + "\n"	+ "Color : " + sColor + "\n" + "Operator : " + sOperator + "\n"	+ "Price : " + sPrice + "\n" + "Total : " + sTotal);
	    			viewDetail.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,	int which) {								
									dialog.dismiss();
								}
							});
					viewDetail.show();*/
	    		}
	    		
	    	});
	    }	catch (JSONException e) {		
			e.printStackTrace();
	    }
    }   
    
    public String getJSONUrl(String url) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
