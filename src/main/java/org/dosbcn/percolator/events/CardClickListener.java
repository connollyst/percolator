package org.dosbcn.percolator.events;

import android.util.Log;
import android.view.View;
import org.dosbcn.percolator.view.CardViewHolder;

public class CardClickListener implements View.OnClickListener {

	private static final String LOG_TAG = CardClickListener.class.getName();

	@Override
	public void onClick(View view) {
		Log.i(LOG_TAG, "onClick");
		CardViewHolder holder = (CardViewHolder) view.getTag();
		toggleDescriptionVisibility(holder);
		toggleTimeVisibility(holder);
	}

	private void toggleDescriptionVisibility(CardViewHolder holder) {
		toggleVisibility(holder.getDescriptionView());
	}

	private void toggleTimeVisibility(CardViewHolder holder) {
		toggleVisibility(holder.getTimeView());
	}

	private void toggleVisibility(View view) {
		int oldVisibility = view.getVisibility();
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
		view.setVisibility(newVisibility);
	}

}