package org.dosbcn.flashcards.view;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * An holder for a view as part of the "ViewHolder Pattern".<br/>
 * The {@link ListView} recycles actual {@link View}s and the
 * {@link CardViewHolder} allows us to easily access and modify the state of an
 * actual view.
 * 
 * @author Sean Connolly
 */
public class CardViewHolder {

	private TextView titleView;
	private TextView descriptionView;
	private TextView timeView;

	public TextView getTitleView() {
		return titleView;
	}

	public void setTitleView(TextView titleView) {
		this.titleView = titleView;
	}

	public TextView getDescriptionView() {
		return descriptionView;
	}

	public void setDescriptionView(TextView descriptionView) {
		this.descriptionView = descriptionView;
	}

	public TextView getTimeView() {
		return timeView;
	}

	public void setTimeView(TextView timeView) {
		this.timeView = timeView;
	}

}
