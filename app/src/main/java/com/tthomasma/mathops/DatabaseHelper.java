package com.tthomasma.mathops;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
	static final String CORRECT = "correct";
	static final String TRIES = "tries";
	ContentValues cv;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE scores (_id INTEGER PRIMARY KEY AUTOINCREMENT, correct INTEGER, tries INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS scores");
		onCreate(db);
	}

	public void writeScore(int correct, int tries) {

		cv = new ContentValues();
		cv.put(CORRECT, correct);
		cv.put(TRIES, tries);
		this.getWritableDatabase().insert("scores", CORRECT, cv);
	}

}
