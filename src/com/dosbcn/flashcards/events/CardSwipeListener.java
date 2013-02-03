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

	private float gestureStartX;

	static final int TRIGGER_DELTA = 50;

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return onTouchDown(view, event);
		case MotionEvent.ACTION_UP:
			return onTouchUp(view, event);
		case MotionEvent.ACTION_MOVE:
			return onTouchMove(view, event);
		default:
			// The event was not consumed..
			return false;
		}
	}

	private boolean onTouchDown(View view, MotionEvent event) {
		Log.i("CardSwipe", "touch started");
		gestureStartX = event.getX();
		return true;
	}

	private boolean onTouchUp(View view, MotionEvent event) {
		Log.i("CardSwipe", "touch ended");
		float gestureEndX = event.getX();
		float gestureDistanceX = gestureEndX - gestureStartX;
		if (Math.abs(gestureDistanceX) > TRIGGER_DELTA) {
			if (gestureDistanceX > 0) {
				onSlideComplete(SwipeDirection.RIGHT);
			} else {
				onSlideComplete(SwipeDirection.LEFT);
			}
			return true;
		} else {
			view.performClick();
			return false;
		}
	}

	private boolean onTouchMove(View view, MotionEvent event) {
		Log.i("CardSwipe", "touch moved");
		return true;
	}

	public void onSlideComplete(SwipeDirection direction) {
		Log.i("CardSwipe", "got '" + direction + "' touch");
	}

}
