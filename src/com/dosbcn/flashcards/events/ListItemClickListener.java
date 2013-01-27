package com.dosbcn.flashcards.events;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.dosbcn.flashcards.CardView;

public class ListItemClickListener {

	private static final String LOG_TAG = ListItemClickListener.class
			.getSimpleName();

	public void onClick(ListView listView, View view, int position, long id) {
		CardView holder = (CardView) view.getTag();
		toggleDescriptionVisibility(holder);
	}

	private void toggleDescriptionVisibility(CardView holder) {
		View description = holder.getDescriptionView();
		int oldVisibility = description.getVisibility();
		int newVisibility;
		switch (oldVisibility) {
		case View.VISIBLE:
			newVisibility = View.GONE;
			break;
		case View.GONE:
			newVisibility = View.VISIBLE;
			break;
		default:
			Log.e(LOG_TAG, "Cannot toggle visibility; unrecognized state: "
					+ oldVisibility);
			newVisibility = View.GONE;
			break;
		}
		description.setVisibility(newVisibility);
	}

}