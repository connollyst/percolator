package com.dosbcn.percolator.events;

import android.util.Log;
import android.view.View;

import com.dosbcn.percolator.view.CardViewHolder;

public class CardClickListener implements View.OnClickListener {

	private static final String LOG_TAG = CardClickListener.class.getName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(View view) {
		CardViewHolder holder = (CardViewHolder) view.getTag();
		toggleDetailsVisibility(holder);
	}

	private void toggleDetailsVisibility(CardViewHolder holder) {
		toggleVisibility(holder.getDetailsLayout());
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
