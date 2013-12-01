package com.dosbcn.percolator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * A dialog displayed the first time a user opens Percolator.
 *
 * @author Sean Connolly
 */
public class WelcomeDialogFragment extends DialogFragment {

	public interface WelcomeDialogListener {

		public void onWelcomeDismissed(DialogFragment dialog);

	}

	private WelcomeDialogListener listener;

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (WelcomeDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement "
					+ WelcomeDialogListener.class.getSimpleName());
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.ic_launcher)
				.setTitle(R.string.welcome_title)
				.setMessage(R.string.welcome_message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onWelcomeDismissed(WelcomeDialogFragment.this);
					}
				});
		return builder.create();
	}

}
