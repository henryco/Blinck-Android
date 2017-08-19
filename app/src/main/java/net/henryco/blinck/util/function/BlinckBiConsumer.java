package net.henryco.blinck.util.function;

/**
 * Created by HenryCo on 07/05/17.
 */
@FunctionalInterface
public interface BlinckBiConsumer<T, U> {
	void consume(T t, U u);
}
