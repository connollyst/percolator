package com.dosbcn.flashcards;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dosbcn.flashcards.data.Card;

/**
 * 
 * @author Sean Connolly
 */
public class CardViewAdapter extends ArrayAdapter<Card> {

	private static final int CARD_VIEW = R.layout.card;

	private final LayoutInflater inflator;
	private final List<Card> cards;

	/**
	 * 
	 * @param context
	 * @param cards
	 */
	public CardViewAdapter(Context context, List<Card> cards) {
		super(context, CARD_VIEW, cards);
		this.inflator = LayoutInflater.from(context.getApplicationContext());
		this.cards = cards;
	}

	/**
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			initializeConvertView(convertView);
		}
		CardView cardView = (CardView) convertView.getTag();
		Card card = cards.get(position);
		cardView.getTitleView().setText(card.getTitle());
		cardView.getDescriptionView().setText(card.getDescription());
		return convertView;
	}

	/**
	 * 
	 * @param convertView
	 * @return
	 */
	private View initializeConvertView(View convertView) {
		convertView = inflator.inflate(CARD_VIEW, null);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView description = (TextView) convertView
				.findViewById(R.id.description);
		CardView viewHolder = new CardView();
		viewHolder.setTitleView(title);
		viewHolder.setDescriptionView(description);
		convertView.setTag(viewHolder);
		return convertView;
	}

}
