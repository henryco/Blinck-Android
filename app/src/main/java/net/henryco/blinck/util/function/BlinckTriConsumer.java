package net.henryco.blinck.util.function;

/**
 * Created by HenryCo on 07/05/17.
 */
@FunctionalInterface
public interface BlinckTriConsumer<T, U, Y> {
	void consumer(T t, U u, Y y);
}
