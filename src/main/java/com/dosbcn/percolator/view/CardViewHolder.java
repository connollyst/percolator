package com.dosbcn.percolator.view;

import android.view.View;
import android.widget.LinearLayout;
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
	private LinearLayout detailsLayout;
	private TextView descriptionView;
	private TextView startDateView;
	private TextView notificationDateView;

	public CardViewHolder(TextView titleView, LinearLayout detailsLayout,
			TextView descriptionView, TextView startDateView,
			TextView notificationDateView) {
		this.titleView = titleView;
		this.detailsLayout = detailsLayout;
		this.descriptionView = descriptionView;
		this.startDateView = startDateView;
		this.notificationDateView = notificationDateView;
	}

	public TextView getTitleView() {
		return titleView;
	}

	public void setTitleView(TextView titleView) {
		this.titleView = titleView;
	}

	public LinearLayout getDetailsLayout() {
		return detailsLayout;
	}

	public void setDetailsLayout(LinearLayout detailsLayout) {
		this.detailsLayout = detailsLayout;
	}

	public TextView getDescriptionView() {
		return descriptionView;
	}

	public void setDescriptionView(TextView descriptionView) {
		this.descriptionView = descriptionView;
	}

	public TextView getStartDateView() {
		return startDateView;
	}

	public void setStartDateView(TextView startDateView) {
		this.startDateView = startDateView;
	}

	public TextView getNotificationDateView() {
		return notificationDateView;
	}

	public void setNotificationDateView(TextView notificationDateView) {
		this.notificationDateView = notificationDateView;
	}

}
