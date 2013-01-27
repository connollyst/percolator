package com.dosbcn.flashcards.events;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CardSwipeListener implements View.OnTouchListener {

	// TODO
	// ALL OF THIS CODE SUCKS
	// NOBODY KNOWS HOW TO CODE..
	// https://gist.github.com/2980593
	// http://stackoverflow.com/questions/9321016/android-listview-slide-left-right-like-samsung-contact-listview/9321178#9321178

	private enum SwipeDirection {
		LEFT, RIGHT;
	}

	private float historicX = Float.NaN;
	private float historicY = Float.NaN;

	static final int TRIGGER_DELTA = 50;

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		Log.i("CardSwipe", "got touch");
		// TODO I _hate_ depending on fall through switch statements..
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("CardSwipe", "got down touch");
			historicX = event.getX();
			historicY = event.getY();
		case MotionEvent.ACTION_UP:
			Log.i("CardSwipe", "got up touch");
			if (event.getX() - historicX > -TRIGGER_DELTA) {
				onSlideComplete(SwipeDirection.LEFT);
				return true;
			} else if (event.getX() - historicX > TRIGGER_DELTA) {
				onSlideComplete(SwipeDirection.RIGHT);
				return true;
			}
		default:
			return false;
		}
	}

	public void onSlideComplete(SwipeDirection direction) {
		Log.i("CardSwipe", "got '" + direction + "' touch");
	}

}
