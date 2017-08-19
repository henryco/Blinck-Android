package net.henryco.blinck.util.function;

/**
 * Created by HenryCo on 07/05/17.
 */
@FunctionalInterface
public interface BlinckConsumer<T> {

	void consume(T consumer);
}
