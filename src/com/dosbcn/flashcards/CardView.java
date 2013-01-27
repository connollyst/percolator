package com.dosbcn.flashcards;

import android.widget.TextView;

public class CardView {

	private TextView titleView;
	private TextView descriptionView;

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

}
