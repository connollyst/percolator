package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.R;
import org.dosbcn.flashcards.data.Card;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * The means by which {@link Card} {@link Notification}s are sent to the device.<br/>
 * The type of notification (reminder, quiz, etc.) is determined, randomly, on
 * the fly based on the {@link Card's} current {@link CardNotificationStage}.
 * 
 * @author Sean Connolly
 */
public class CardNotifier {

	private final Context context;

	public CardNotifier(Context context) {
		this.context = context;
	}

	/**
	 * Displays an Android {@link Notification} for the given {@link Card}.
	 * 
	 * @param card
	 *            the {@link Card} to display a {@link Notification} for
	 */
	public void showNotification(Card card) {
		Log.v(CardNotifier.class.getSimpleName(), "Sending notification for '"
				+ card.getTitle() + "'.");
		getNotificationManager().notify(card.getId(), buildNotification(card));
	}

	private Notification buildNotification(Card card) {
		return new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(card.getTitle())
				.setContentText(card.getDescription())
				.setContentIntent(
						PendingIntent.getActivity(context, 0, new Intent(), 0))
				.getNotification();
	}

	private NotificationManager getNotificationManager() {
		return (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}
}
