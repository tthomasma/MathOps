package com.tthomasma.mathops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private String[] items = { "Add", "Subtract", "Multiply", "Divide", "--report--" };

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		ListView mylist = (ListView) findViewById(R.id.listViewMain);
		mylist.setAdapter(adapter);

		mylist.setOnItemClickListener(this);
		
		registerForContextMenu(mylist);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position < 4) {
			Intent intent = new Intent(this, MathActivity.class);
			intent.putExtra("operation", items[position]);
			startActivity(intent);
		}
		else {
			Intent intent = new Intent(this, ReportActivity.class);
			startActivity(intent);
			Toast.makeText(this, "Open the report", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		String whichOne = "";
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.choice1: {
	            whichOne = "Choice 1";
	            break;
	        }
	        case R.id.choice2: {
	            whichOne = "Choice 2";
	            break;
	        }
	        default:
	            return super.onContextItemSelected(item);
	    }
		Toast.makeText(this,
				whichOne,
				Toast.LENGTH_LONG).show();
	    return true;
	}
}
