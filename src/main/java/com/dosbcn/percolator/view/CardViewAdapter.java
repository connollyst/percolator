package com.dosbcn.percolator.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dosbcn.percolator.R;
import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.events.CardClickListener;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * An Android list adapter, responsible for managing the relationship between
 * the list of {@link Card}s and their respective {@link CardViewHolder}s.
 *
 * @author Sean Connolly
 */
public class CardViewAdapter extends ArrayAdapter<Card> {

	private static final String DATE_FORMAT_PATTERN = "MMMM dd, YYYY";
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat
			.forPattern(DATE_FORMAT_PATTERN);
	private static final CardClickListener CLICK_LISTENER = new CardClickListener();
	private static final int CARD_VIEW = R.layout.card;
	private final LayoutInflater inflater;

	/**
	 * Default constructor.
	 *
	 * @param context
	 *            the android context
	 * @param cards
	 *            the initial list of flash cards
	 */
	public CardViewAdapter(Context context, List<Card> cards) {
		super(context, CARD_VIEW, cards);
		this.inflater = LayoutInflater.from(context.getApplicationContext());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Card card = getItem(position);
		if (view == null) {
			view = initializeNewView();
		}
		// Set content
		CardViewHolder cardView = (CardViewHolder) view.getTag();
		cardView.getTitleView().setText(card.getTitle());
		cardView.getDescriptionView().setText(card.getDescription());
		cardView.getStartDateView().setText(
				DATE_FORMAT.print(card.getStartDate()));
		cardView.getNotificationDateView().setText(
				DATE_FORMAT.print(card.getNextNotificationDate()));
		// Set style
		if (position % 2 == 1) {
			view.setBackgroundResource(R.color.percolator_red_six);
		} else {
			view.setBackgroundResource(R.color.percolator_red_seven);
		}
		return view;
	}

	/**
	 * Views are recycled in a list view; here we create a new recyclable view.
	 *
	 * @return a new convertable view
	 */
	private View initializeNewView() {
		View convertView = inflater.inflate(CARD_VIEW, null);
		TextView title = getTitleTextView(convertView);
		LinearLayout detailsLayout = getDetailsLayout(convertView);
		TextView description = getDescriptionTextView(convertView);
		TextView startDate = getStartDateTextView(convertView);
		TextView notificationDate = getNotificationDateTextView(convertView);
		CardViewHolder viewHolder = new CardViewHolder(title, detailsLayout,
				description, startDate, notificationDate);
		convertView.setTag(viewHolder);
		convertView.setOnClickListener(CLICK_LISTENER);
		return convertView;
	}

	/**
	 * Get the 'Title' {@link TextView}.
	 *
	 * @param view
	 *            the context view
	 * @return the text view
	 */
	private TextView getTitleTextView(View view) {
		return (TextView) view.findViewById(R.id.title);
	}

	/**
	 * Get the 'Details' {@link LinearLayout}.
	 *
	 * @param view
	 *            the context view
	 * @return the linear layout
	 */
	private LinearLayout getDetailsLayout(View view) {
		return (LinearLayout) view.findViewById(R.id.details);
	}

	/**
	 * Get the 'Description' {@link TextView}.
	 *
	 * @param view
	 *            the context view
	 * @return the text view
	 */
	private TextView getDescriptionTextView(View view) {
		return (TextView) view.findViewById(R.id.description);
	}

	/**
	 * Get the 'Start Date' {@link TextView}.
	 *
	 * @param view
	 *            the context view
	 * @return the text view
	 */
	private TextView getStartDateTextView(View view) {
		return (TextView) view.findViewById(R.id.start_date_text);
	}

	/**
	 * Get the 'Next Notification' {@link TextView}.
	 *
	 * @param view
	 *            the context view
	 * @return the text view
	 */
	private TextView getNotificationDateTextView(View view) {
		return (TextView) view.findViewById(R.id.notification_date_text);
	}

}
