package com.tthomasma.mathops;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

public class MyDBLoader extends CursorLoader {
	
	private static final String DATABASE_NAME = "db";

	public MyDBLoader(Context context) {
		super(context);
	}

	public MyDBLoader(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		super(context, uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public Cursor loadInBackground() {
		// this is just a simple query, could be anything that gets a cursor

		DatabaseHelper db = new DatabaseHelper(this.getContext(),
				DATABASE_NAME, null, 1);
		
		SQLiteCursor constantsCursor = (SQLiteCursor) db.getReadableDatabase().rawQuery(
						"SELECT _ID, correct, tries FROM scores", null);

		return constantsCursor;
	}
	
	

}
