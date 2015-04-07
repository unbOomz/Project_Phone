package com.example.retrievedata;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ThirdActivity extends Activity {
	
	Spinner spin;
	Button buttonSelect;
	
	String strSpin;
	//TextView txtView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        
        initWidget();
        chooseSpinner();  
        
        buttonSelect.setOnClickListener(new OnClickListener() {			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strSpin = String.valueOf(spin.getSelectedItem());					
				sentPriority();
			}
		});
	}
	
	private void initWidget() {
		// TODO Auto-generated method stub
		spin = (Spinner)findViewById(R.id.spinner1);
        buttonSelect = (Button)findViewById(R.id.button1);
	}

	protected void sentPriority() {
		// TODO Auto-generated method stub		
		Intent sentData = new Intent(ThirdActivity.this, FouthActivity.class);
		String pro_id = getIntent().getStringExtra("pro_id");
		sentData.putExtra("strSpin", strSpin);
		sentData.putExtra("pro_id", pro_id);
		startActivity(sentData);
	}

	protected void chooseSpinner() {
		// TODO Auto-generated method stub
		ArrayList<String> arrList = new ArrayList<String>();
		
		arrList.add("Time");
        arrList.add("Net");
        arrList.add("SMS");
        arrList.add("MMS");
        arrList.add("Price");
        
        ArrayAdapter<String> arrAd = new ArrayAdapter<String>(ThirdActivity.this, android.R.layout.simple_spinner_item, arrList);        
        spin.setAdapter(arrAd);        
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
