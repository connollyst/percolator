package com.dosbcn.flashcards;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dosbcn.flashcards.data.Card;

public class CardViewAdapter extends ArrayAdapter<Card> {

	private static final int CARD_VIEW = R.layout.card;

	private final LayoutInflater inflator;
	private final List<Card> cards;

	public CardViewAdapter(Context context, List<Card> cards) {
		super(context, CARD_VIEW, cards);
		this.inflator = LayoutInflater.from(context.getApplicationContext());
		this.cards = cards;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			rowView = inflator.inflate(CARD_VIEW, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) rowView.findViewById(R.id.title);
			viewHolder.description = (TextView) rowView
					.findViewById(R.id.description);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		Card card = cards.get(position);
		holder.title.setText(card.getTitle());
		holder.description.setText(card.getDescription());
		return rowView;
	}

	static class ViewHolder {
		public TextView title;
		public TextView description;
	}

}
