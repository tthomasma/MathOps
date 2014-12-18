package com.tthomasma.mathops;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ReportActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	SimpleCursorAdapter adapter;
	private static final String DATABASE_NAME = "db";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		getSupportLoaderManager().initLoader(1, null, this);
		Toast.makeText(this, "Report open", Toast.LENGTH_LONG).show();

	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new MyDBLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
				new String[] { DatabaseHelper.CORRECT, DatabaseHelper.TRIES },
				new int[] { R.id.correct, R.id.tries }, 0);

		ListView listView = (ListView) findViewById(R.id.listViewReport);
		listView.setAdapter(adapter);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {
	    adapter.swapCursor(null);
	}
	
}
