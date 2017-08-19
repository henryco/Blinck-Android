package net.henryco.blinck.util.adapter;

/**
 * Created by HenryCo on 22/05/17.
 */

public interface BlinckDataAdapter<T, U> {

	T adapt1(U data);
	U adapt2(T data);
}
