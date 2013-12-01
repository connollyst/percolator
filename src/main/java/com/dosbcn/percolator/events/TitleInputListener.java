package com.dosbcn.percolator.events;

import android.text.Editable;
import android.text.TextWatcher;
import com.dosbcn.percolator.MainActivity;

/**
 * @author Sean Connolly
 */
public class TitleInputListener implements TextWatcher {

	private final MainActivity activity;

	public TitleInputListener(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i2,
			int i3) {
	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
	}

	@Override
	public void afterTextChanged(Editable editable) {
		boolean hasTitle = editable.length() > 0;
        activity.findSaveButton().setEnabled(hasTitle);
	}
}
