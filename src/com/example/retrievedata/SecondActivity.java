package com.example.retrievedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {
	
	TextView tproid, tband, tgen, tcapacity, tcolor, tprice, toperator, ttotal;	
	Button promotionButton;
	Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        
        initWidget();
        
        final Intent i = getIntent();
        final ArrayList<HashMap<String, String>> MyArrList2 = (ArrayList<HashMap<String, String>>)i.getSerializableExtra("jsonArray");
        
        String sProductID = MyArrList2.get(i.getIntExtra("position", 0)).get("product_id").toString();
        String sBand = MyArrList2.get(i.getIntExtra("position", 0)).get("band").toString();
		String sGen = MyArrList2.get(i.getIntExtra("position", 0)).get("gen").toString();
		String sCapacity = MyArrList2.get(i.getIntExtra("position", 0)).get("capacity").toString();
		String sColor = MyArrList2.get(i.getIntExtra("position", 0)).get("color").toString();
		//String sOperator = MyArrList2.get(i.getIntExtra("position", 0)).get("operator").toString();
		String sPrice = MyArrList2.get(i.getIntExtra("position", 0)).get("price").toString();
		//String sTotal = MyArrList2.get(i.getIntExtra("position", 0)).get("price_promotion").toString();
		
		String text = "Product Detail";
		
		tproid.setText("ProductID:  " + sProductID);
		tband.setText("Band:  " + sBand);
		tgen.setText("Gen:  " + sGen);
		tcapacity.setText("Capacity:  " + sCapacity);
		tcolor.setText("Color:  " + sColor);
		//toperator.setText("Operator:  " + sOperator);
		tprice.setText("Price:  " + sPrice);
		//ttotal.setText("Total:  " + sTotal);
        
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        
        promotionButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v){
        		intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("pro_id", MyArrList2.get(i.getIntExtra("position", 0)).get("product_id").toString());
        		startActivity(intent);
        	}
        });

	}

	private void initWidget() {
		// TODO Auto-generated method stub
		tproid = (TextView)findViewById(R.id.item_pro_id);
        tband = (TextView)findViewById(R.id.item_band);
        tgen = (TextView)findViewById(R.id.item_gen);
        tcapacity = (TextView)findViewById(R.id.item_capacity);
        tcolor = (TextView)findViewById(R.id.item_color);
        toperator = (TextView)findViewById(R.id.item_operator);
        tprice = (TextView)findViewById(R.id.item_price);
        ttotal = (TextView)findViewById(R.id.item_price_promotion);
        promotionButton = (Button)findViewById(R.id.item_promotion);
	}
}
