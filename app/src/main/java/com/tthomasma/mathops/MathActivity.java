package com.tthomasma.mathops;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MathActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainAddActivity";
	private static final String DATABASE_NAME = "db";

	int whatsRight;
	Button a1button3;
	Button a2button4;
	Button a3button5;
	Button n1button1;
	Button n2button2;
	TextView counterView;
	int[] problem;
	Integer numberOfTries = 0;
	Integer triesCorrectFirstAnswer = 0;
	boolean firstTry = true;
	String counterString = "";
	String operation = "";
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_math);
		operation = (String) getIntent().getCharSequenceExtra("operation");

		a1button3 = (Button) findViewById(R.id.button3);
		a2button4 = (Button) findViewById(R.id.button4);
		a3button5 = (Button) findViewById(R.id.button5);
		n1button1 = (Button) findViewById(R.id.button1);
		n2button2 = (Button) findViewById(R.id.button2);
		counterView = (TextView) findViewById(R.id.textView3);
		a1button3.setOnClickListener(this);
		a2button4.setOnClickListener(this);
		a3button5.setOnClickListener(this);
		
		db = new DatabaseHelper(this, DATABASE_NAME, null, 1);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		boolean gotItRight = false;
		switch (v.getId()) {
		case R.id.button3: {
			if (whatsRight == 0)
				gotItRight = true;
			break;
		}
		case R.id.button4: {
			if (whatsRight == 1)
				gotItRight = true;
			break;
		}
		case R.id.button5: {
			if (whatsRight == 2)
				gotItRight = true;
			break;
		}
		}
		if (gotItRight) {
			Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
			if (firstTry)
				triesCorrectFirstAnswer++;
				numberOfTries++;
				db.writeScore(triesCorrectFirstAnswer, numberOfTries);
			newProblem();
		} else {
			Toast.makeText(this, "Sorry, try again.", Toast.LENGTH_LONG).show();
			firstTry = false;
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int[] value;
		value = savedInstanceState.getIntArray("counters");
		numberOfTries = value[0];
		triesCorrectFirstAnswer = value[1];
		if (value[2] == 1) {
			firstTry = true;
		} else
			firstTry = false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int[] value = { 0, 0, 0 };
		value[0] = numberOfTries;
		value[1] = triesCorrectFirstAnswer;
		if (firstTry)
			value[2] = 1;
		else {
			numberOfTries++;
			value[0] = numberOfTries;
		}
		outState.putIntArray("counters", value);
	}

	@Override
	protected void onResume() {
		super.onResume();
		newProblem();
	}

	private void newProblem() {
		int correct = 0;
		int deltaRange = 12;
		counterString = triesCorrectFirstAnswer.toString() + "/"
				+ numberOfTries.toString();
		counterView.setText(counterString);
		String operationSign = "";
		switch (operation) {
		case "Add": {
			operationSign = "+";
			problem = generateAddQuestion(50);
			correct = problem[0] + problem[1];
			break;
		}
		case "Subtract": {
			operationSign = "-";
			problem = generateSubtractQuestion(100);
			correct = problem[0] - problem[1];
			break;
		}
		case "Multiply": {
			operationSign = "X";
			problem = generateAddQuestion(12);
			correct = problem[0] * problem[1];
			break;
		}
		case "Divide": {
			operationSign = "/";
			problem = generateDivideQuestion(144);
			correct = problem[0] / problem[1];
			deltaRange = 6;
			break;
		}
		}
		TextView textView = (TextView) findViewById(R.id.textView2);
		textView.setText(operationSign);
		firstTry = true;
		setInteger(n1button1, problem[0]);
		setInteger(n2button2, problem[1]);

		int deltaOne = (int) (Math.random() * deltaRange) - (deltaRange/2);
		int deltaTwo = (int) (Math.random() * deltaRange) - (deltaRange/2);
		int wrong1 = correct + deltaOne;
		if (wrong1 < 0)
			wrong1 = 0;
		if (wrong1 == correct) {
			if (wrong1 == 0)
				wrong1 = correct + 1;
			else
				wrong1 = correct - 1;
		}
		int wrong2 = correct + deltaTwo;
		if (wrong2 < 0)
			wrong2 = 0;
		if ((wrong2 == correct) || (wrong2 == wrong1)) {
			wrong2 = Math.min(correct, wrong1) - 1;
			if (wrong2 < 0)
				wrong2 = Math.max(correct, wrong1) + 1;
		}
		whatsRight = (int) (Math.random() * 3);
		if (whatsRight == 0) {
			setInteger(a1button3, correct);
			setInteger(a2button4, wrong1);
			setInteger(a3button5, wrong2);
		} else if (whatsRight == 1) {
			setInteger(a2button4, correct);
			setInteger(a1button3, wrong1);
			setInteger(a3button5, wrong2);
		} else {
			setInteger(a3button5, correct);
			setInteger(a1button3, wrong1);
			setInteger(a2button4, wrong2);
		}
	}

	private void setInteger(Button theButton, int value) {
		Integer number = (Integer) value;
		String stringInt = number.toString();
		theButton.setText(stringInt);
	}

	private int[] generateAddQuestion(int range) {
		int[] question = { 0, 0 };
		question[0] = (int) (Math.random() * range);
		question[1] = (int) (Math.random() * range);
		return question;
	}
	
	private int[] generateSubtractQuestion(int range) {
		int[] question = { 0, 0 };
		int first, second;
		question[0] = (int) (Math.random() * range);
		question[1] = (int) (Math.random() * range);
		first = question[0];
		second = question[1];
		if (first < second) {
			question[0] = second;
			question[1] = first;
		}
		return question;
	}
	
	private int[] generateDivideQuestion(int range) {
		int[] question = { 0, 0 };
		int first, second;
		question[0] = (int) (Math.random() * (range-1));
		question[0]++;
		question[1] = (int) (Math.random() * (range-1));
		first = question[0];
		question[1]++;
		second = question[1];
		if (first < second) {
			question[0] = second;
			question[1] = first;
		}
		while (question[0] % question[1] != 0) {
			question[0]--;
		}
		return question;
	}
}
