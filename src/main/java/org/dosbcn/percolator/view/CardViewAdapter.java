package org.dosbcn.percolator.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.dosbcn.percolator.R;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.events.CardClickListener;
import org.dosbcn.percolator.events.CardSwipeListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Sean Connolly
 */
public class CardViewAdapter extends ArrayAdapter<Card> {

	private static final DateFormat DATE_FORMAT = SimpleDateFormat
			.getDateTimeInstance();

	private static final CardSwipeListener SWIPE_LISTENER = new CardSwipeListener();
	private static final CardClickListener CLICK_LISTENER = new CardClickListener();

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
			convertView = initializeNewConvertView(convertView);
		}
		CardViewHolder cardView = (CardViewHolder) convertView.getTag();
		Card card = cards.get(position);
		cardView.getTitleView().setText(card.getTitle());
		cardView.getDescriptionView().setText(card.getDescription());
		cardView.getTimeView().setText(
				DATE_FORMAT.format(card.getNextNotificationDate()));
		return convertView;
	}

	/**
	 * 
	 * @param convertView
	 * @return
	 */
	private View initializeNewConvertView(View convertView) {
		convertView = inflator.inflate(CARD_VIEW, null);
		TextView title = getTitleTextView(convertView);
		TextView description = getDescriptionTextView(convertView);
		TextView time = getTimeTextView(convertView);
		CardViewHolder viewHolder = new CardViewHolder();
		viewHolder.setTitleView(title);
		viewHolder.setDescriptionView(description);
		viewHolder.setTimeView(time);
		convertView.setTag(viewHolder);
		convertView.setOnTouchListener(SWIPE_LISTENER);
		convertView.setOnClickListener(CLICK_LISTENER);
		return convertView;
	}

	private TextView getTitleTextView(View convertView) {
		return (TextView) convertView.findViewById(R.id.title);
	}

	private TextView getDescriptionTextView(View convertView) {
		return (TextView) convertView.findViewById(R.id.description);
	}

	private TextView getTimeTextView(View convertView) {
		return (TextView) convertView.findViewById(R.id.time);
	}

}
