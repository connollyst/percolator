package org.dosbcn.percolator.events;

import org.dosbcn.percolator.R;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ListView;

public class GlobalLayoutListener implements
		ViewTreeObserver.OnGlobalLayoutListener {

	private final Context context;
	private final ListView view;

	public GlobalLayoutListener(Context context, ListView view) {
		super();
		this.context = context;
		this.view = view;
	}

	@Override
	public void onGlobalLayout() {
		// This will be called once the layout is
		// finished, prior to displaying.
		View headerView = view.findViewById(R.id.header);
		if (headerView != null) {
			// don't need the listener any more
			headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			// Get screen dimensions
			Point size = getDisplaySize();
			int screenHeight = size.y;
			// Add padding to occupy the screen
			int headerHeight = headerView.getMeasuredHeight();
			int padding = screenHeight - headerHeight;
			headerView.setPadding(0, 0, 0, padding);
		}
	}

	private Point getDisplaySize() {
		Point size = new Point();
		Display display = getWindowManager().getDefaultDisplay();
		// if (Build.VERSION.SDK_INT >= 13) {
		// display.getSize(size);
		// } else {
		// For backwards compatibility
		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		@SuppressWarnings("deprecation")
		int height = display.getHeight();
		size.x = width;
		size.y = height;
		// }
		return size;
	}

	private WindowManager getWindowManager() {
		return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}
}
