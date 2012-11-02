package com.example.mobilehitchhiker.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilehitchhiker.Constants;
import com.example.mobilehitchhiker.CreateTripActivity;
import com.jayway.android.robotium.solo.Solo;

public class CreateTripActivityTest extends
		ActivityInstrumentationTestCase2<CreateTripActivity> {

	private CreateTripActivity thisActivity;
	private EditText startLocation;
	private EditText endLocation;
	private Button buttonShow;
	private Solo mSolo;
	private static final String CLASSTAG = "CreateTripActivityTest";

	public CreateTripActivityTest() {
		super(com.example.mobilehitchhiker.CreateTripActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		thisActivity = getActivity();

		buttonShow = (Button) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.button_show);

		startLocation = (EditText) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.start_location);

		endLocation = (EditText) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.end_location);

		mSolo = new Solo(getInstrumentation(), getActivity());

		Log.v(Constants.LOGTAG, " " + CreateTripActivityTest.CLASSTAG
				+ " setUp");
	}

	public void testPreConditions() {
		assertTrue(true);
		assertTrue(startLocation != null);
		assertTrue(endLocation != null);
		assertTrue(buttonShow.getText().toString().equals("Create Trip"));
	} // end of testPreConditions() method definition

	public void testButtonShow() {

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

		// Create Trip with empty end location
		mSolo.clickOnButton("Continue");
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		Activity newActivity = mSolo.getCurrentActivity();
		mSolo.assertCurrentActivity("Activity is not correct",
				newActivity.getClass());

	}

}
