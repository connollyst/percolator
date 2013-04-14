package org.dosbcn.percolator.events;

/**
 * A parameterized event listener. When the event is fired, the {@code onEvent}
 * function will be fired and data of type {@code T} will be provided.
 * 
 * @author Sean Connolly
 * @param <T>
 *            the type of data to be passed when the event is fired
 */
public interface EventListener<T> {

	/**
	 * Handle the event.
	 * 
	 * @param data
	 *            the event data
	 */
	public void onEvent(T data);

}
