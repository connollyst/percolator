package com.dosbcn.flashcards;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dosbcn.flashcards.data.FlashCard;

public class FlashCardViewAdapter extends BaseAdapter {

	private static final int ROW_COLLAPSED = 0;
	private static final int ROW_EXPANDED = 1;
	private static final int ROW_TYPES = 2;

	private static final int RESOURCE = android.R.layout.simple_list_item_1;
	private static final int TEXT_VIEW_RESOURCE1 = android.R.id.text1;
	private static final int TEXT_VIEW_RESOURCE2 = android.R.id.text1;

	private final LayoutInflater inflator;
	private final List<FlashCard> flashCards;

	public FlashCardViewAdapter(Context context, List<FlashCard> flashCards) {
		inflator = LayoutInflater.from(context.getApplicationContext());
		this.flashCards = flashCards;
	}

	public void clear() {
		flashCards.clear();
	}

	public void addAll(Collection<? extends FlashCard> flashCards) {
		this.flashCards.addAll(flashCards);
	}

	@Override
	public int getViewTypeCount() {
		return ROW_TYPES;
	}

	@Override
	public int getCount() {
		return flashCards.size();
	}

	@Override
	public FlashCard getItem(int position) {
		return flashCards.get(position);
	}

	@Override
	public int getItemViewType(int position) {
		return position == 2 ? ROW_EXPANDED : ROW_COLLAPSED;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		Log.e("ERR", "getView " + position + " " + convertView + " type = "
				+ type);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case ROW_COLLAPSED:
				convertView = inflator.inflate(RESOURCE, null);
				holder.textView = (TextView) convertView
						.findViewById(TEXT_VIEW_RESOURCE1);
				break;
			case ROW_EXPANDED:
				convertView = inflator.inflate(RESOURCE, null);
				holder.textView = (TextView) convertView
						.findViewById(TEXT_VIEW_RESOURCE2);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(getItem(position).getTitle());
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}
}
