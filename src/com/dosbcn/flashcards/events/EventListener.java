package com.dosbcn.flashcards.events;

public interface EventListener<T> {

	public void fireEvent(T data);

}
