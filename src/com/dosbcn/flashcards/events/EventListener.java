package com.dosbcn.flashcards.events;

public interface EventListener<T> {

	public void onEvent(T data);

}
