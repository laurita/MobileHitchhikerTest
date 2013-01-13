package com.example.mobilehitchhiker.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.example.mobilehitchhiker.Config;
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
		Log.v(Config.LOGTAG, " " + MainActivityTest.CLASSTAG + " setUp");
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
		Log.v(Config.LOGTAG, " " + MainActivityTest.CLASSTAG
				+ " testPreconditions");
		assertTrue(startLocation != null);
		assertTrue(endLocation != null);
		assertTrue(buttonCreate.getText().toString().equals("Create Trip"));
		assertTrue(buttonFind.getText().toString().equals("Find Trip"));
	}

	// Tests Show Trip behavior when trip is not found
	public void testTripNotFound() {
		Log.v(Config.LOGTAG, " " + MainActivityTest.CLASSTAG
				+ " testTripNotFound");

		mSolo.enterText(startLocation, "Bolzano, Italy");
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnText("Pick date");
		getInstrumentation().waitForIdleSync();
		mSolo.setDatePicker(0, 2012, 3, 1); // such trip doesn't exist
		mSolo.clickOnText("Done");
		mSolo.clickOnButton("Find Trip");
		getInstrumentation().waitForIdleSync();

		assertTrue(mSolo.searchText("Trip not found"));

	}

	// Tests the Create Trip behavior
	public void testButtonShow() {
		Log.v(Config.LOGTAG, " " + MainActivityTest.CLASSTAG
				+ " testButtonShow");

		// Create Trip with empty start and end locations
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		mSolo.assertCurrentActivity("Activity is not correct #1",
				thisActivity.getClass());

		// Create Trip with empty end location
		mSolo.clickOnButton("Continue");
		mSolo.enterText(startLocation, "Bolzano, Italy");
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();
		mSolo.assertCurrentActivity("Activity is not correct #2",
				thisActivity.getClass());

		// Create Trip with filled in locations
		mSolo.clickOnButton("Continue");
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnButton("Create Trip");
		getInstrumentation().waitForIdleSync();

		assertTrue(mSolo.searchText("Email"));
		mSolo.enterText(0, "laura.bledaite@gmail.com");
		mSolo.clickOnButton("Ok");
		getInstrumentation().waitForIdleSync();

		newActivity = new TripMap();
		Log.v("TESTING", "activity is " + newActivity.getClass());
		mSolo.assertCurrentActivity("Activity is not correct",
				newActivity.getClass());

	}

	// Tests find trip when trip is found
	public void testFindTrip() {
		mSolo.enterText(startLocation, "Bolzano, Italy");
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnButton("Find Trip");
		getInstrumentation().waitForIdleSync();

		newActivity = new TripMap();
		Log.v("TESTING", "activity is " + newActivity.getClass());
		mSolo.assertCurrentActivity("Activity is not correct",
				newActivity.getClass());

		mSolo.clickOnMenuItem("Contact");
		assertTrue(mSolo.searchText("laura.bledaite@gmail.com"));
	}

	// Tests GPS behavior
	public void testGPS() {
		// disable gps
		try {
			Socket socket = new Socket("10.0.2.2", 5554); // usually 5554
			socket.setKeepAlive(true);
			String str = "geo fix 0.0 0.0";
			Writer w = new OutputStreamWriter(socket.getOutputStream());
			w.write(str + "\r\n");
			w.flush();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		mSolo.clickOnCheckBox(0);
		mSolo.enterText(endLocation, "Trento, Italy");
		mSolo.clickOnButton("Find Trip");
		getInstrumentation().waitForIdleSync();
		assertTrue(mSolo.searchText("Can not resolve current location"));

		// Send coordinates to GPS receiver:
		try {
			Socket socket = new Socket("10.0.2.2", 5554); // usually 5554
			socket.setKeepAlive(true);
			String str = "geo fix 10.0 20.0";
			Writer w = new OutputStreamWriter(socket.getOutputStream());
			w.write(str + "\r\n");
			w.flush();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		mSolo.clickOnButton("Continue");

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
