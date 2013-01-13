package com.example.mobilehitchhiker.test;

import com.example.mobilehitchhiker.Config;
import com.example.mobilehitchhiker.TripMap;
import com.example.mobilehitchhiker.TripItemizedOverlay;
import com.google.android.maps.MapView;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class TripMapTest extends ActivityInstrumentationTestCase2<TripMap> {

	private static final String CLASSTAG = TripMapTest.class.getSimpleName();
	private TripMap thisActivity;
	private MapView mapView;
	private TripItemizedOverlay tio;
	private Solo mSolo;

	public TripMapTest() {
		super(com.example.mobilehitchhiker.TripMap.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		Log.v(Config.LOGTAG, " " + TripMapTest.CLASSTAG + " setUp");
		super.setUp();
		setActivityInitialTouchMode(false);
		thisActivity = getActivity();
		mapView = (MapView) thisActivity
				.findViewById(com.example.mobilehitchhiker.R.id.mapview);
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	// Tests that the MapView is not empty
	public void testMapView() {
		Log.v(Config.LOGTAG, " " + TripMapTest.CLASSTAG + " testMapView");
		assertTrue(mapView != null);
	}

}
