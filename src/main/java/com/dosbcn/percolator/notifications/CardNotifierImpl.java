package com.dosbcn.percolator.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.dosbcn.percolator.ListActivity;
import com.dosbcn.percolator.MainActivity;
import com.dosbcn.percolator.R;
import com.dosbcn.percolator.ViewActivity;
import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardStage;

/**
 * The means by which {@link Card} {@link Notification}s are sent to the device.<br/>
 * The type of notification (reminder, quiz, etc.) is determined, randomly, on
 * the fly based on the {@link Card's} current {@link CardStage}.
 *
 * @author Sean Connolly
 */
public class CardNotifierImpl implements CardNotifier {

	private static final String LOG_TAG = CardNotifierImpl.class.getName();

	private final Context context;

	public CardNotifierImpl(Context context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showNotification(Card card) {
		Log.v(LOG_TAG, "Sending notification for '" + card.getTitle() + "'.");
		getNotificationManager().notify(card.getId(), buildNotification(card));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeNotification(Card card) {
		Log.v(LOG_TAG, "Closing notification for '" + card.getTitle() + "'.");
		getNotificationManager().cancel(card.getId());
	}

	private Notification buildNotification(Card card) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher).setOngoing(false)
				.setContentTitle(card.getTitle())
				.setContentText(card.getDescription())
				.setContentIntent(getPendingListActivityIntent(card));
		Notification notification = builder.getNotification();
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		return notification;
	}

	private PendingIntent getPendingListActivityIntent(Card card) {
		Intent intent = new CardIntent(context, ViewActivity.class, card);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ViewActivity.class);
		stackBuilder.addNextIntent(intent);
		return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
	}

	private NotificationManager getNotificationManager() {
		return (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

}
