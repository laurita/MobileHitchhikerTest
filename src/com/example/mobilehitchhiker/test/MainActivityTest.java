package com.example.mobilehitchhiker.test;

import com.example.mobilehitchhiker.Constants;
import com.example.mobilehitchhiker.MainActivity;
import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mainActivity;
	private Button buttonCreate;
	private Button buttonFind;
	private Solo mSolo;
	private static final String CLASSTAG = "MainActivityTest";
	
	public MainActivityTest() {
		super(com.example.mobilehitchhiker.MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mainActivity = getActivity();

		buttonCreate = (Button) mainActivity
				.findViewById(com.example.mobilehitchhiker.R.id.button_create);

		buttonFind = (Button) mainActivity
				.findViewById(com.example.mobilehitchhiker.R.id.button_find);
		
		mSolo = new Solo(getInstrumentation(), getActivity());
	    Log.v(Constants.LOGTAG, " " + MainActivityTest.CLASSTAG + " setUp");

	}

	public void testPreConditions() {
		assertTrue(true);
		assertTrue(buttonCreate.getText().toString().equals("Create Trip"));
		assertTrue(buttonFind.getText().toString().equals("Find Trip"));
	} // end of testPreConditions() method definition

	public void testButtonCreateTrip() {
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		Activity newActivity = mSolo.getCurrentActivity();
		assertTrue(newActivity != mainActivity);
	}

}
