package com.example.mobilehitchhiker.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.example.mobilehitchhiker.Constants;
import com.example.mobilehitchhiker.MainActivity;
import com.example.mobilehitchhiker.TripMap;
import com.jayway.android.robotium.solo.Solo;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private static final String CLASSTAG = MainActivityTest.class
			.getSimpleName();
	private MainActivity thisActivity;
	private TripMap newActivity;
	private Button buttonCreate;
	private Button buttonFind;
	private EditText startLocation;
	private EditText endLocation;
	private Solo mSolo;

	public MainActivityTest() {
		super(com.example.mobilehitchhiker.MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		Log.v(Constants.LOGTAG, " " + MainActivityTest.CLASSTAG + " setUp");
		super.setUp();
		setActivityInitialTouchMode(false);
		thisActivity = getActivity();
		buttonCreate = (Button) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.button_show);
		buttonFind = (Button) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.button_find);
		startLocation = (EditText) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.start_location);
		endLocation = (EditText) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.end_location);
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	public void testPreConditions() {
		Log.v(Constants.LOGTAG, " " + MainActivityTest.CLASSTAG
				+ " testPreconditions");
		assertTrue(startLocation != null);
		assertTrue(endLocation != null);
		assertTrue(buttonCreate.getText().toString().equals("Create Trip"));
		assertTrue(buttonFind.getText().toString().equals("Find Trip"));
	}

	// Tests the Create Trip button behavior with the empty and filled in
	// correct addresses
	public void testButtonShow() {
		Log.v(Constants.LOGTAG, " " + MainActivityTest.CLASSTAG
				+ " testButtonShow");

		// Create Trip with empty start and end locations
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		mSolo.assertCurrentActivity("Activity is not correct",
				thisActivity.getClass());

		// Create Trip with empty end location
		mSolo.clickOnButton("Continue");
		mSolo.enterText(startLocation, "Bolzano, Italy");
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		mSolo.assertCurrentActivity("Activity is not correct",
				thisActivity.getClass());

		// Create Trip with filled in locations
		mSolo.clickOnButton("Continue");
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		newActivity = new TripMap();
		Log.v("TESTING", "activity is " + newActivity.getClass());
		mSolo.assertCurrentActivity("Activity is not correct",
				newActivity.getClass());
	}

	// Tests the Find Trip button behavior with the empty and filled in correct
	// addresses
	public void testButtonFind() {
		Log.v(Constants.LOGTAG, " " + MainActivityTest.CLASSTAG
				+ " testButtonFind");

		// Create Trip with empty start and end locations
		mSolo.clickOnButton("Find Trip");
		getInstrumentation().waitForIdleSync();
		mSolo.assertCurrentActivity("Activity is not correct",
				thisActivity.getClass());

		// Create Trip with empty end location
		mSolo.clickOnButton("Continue");
		mSolo.enterText(startLocation, "Bolzano, Italy");
		mSolo.clickOnButton("Find Trip");
		getInstrumentation().waitForIdleSync();
		mSolo.assertCurrentActivity("Activity is not correct",
				thisActivity.getClass());

		// Create Trip with filled in locations
		mSolo.clickOnButton("Continue");
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnButton("Find Trip");
		getInstrumentation().waitForIdleSync();
		newActivity = new TripMap();
		Log.v("TESTING", "activity is " + newActivity.getClass());
		mSolo.assertCurrentActivity("Activity is not correct",
				newActivity.getClass());
	}

	@Override
	public void tearDown() throws Exception {
		mSolo.finishOpenedActivities();
	}

}
