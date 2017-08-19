package net.henryco.blinck.util.function;

/**
 * Created by HenryCo on 20/08/17.
 */

public interface BlinckBiFunction<RETURN, PARAM1, PARAM2> {
	RETURN apply(PARAM1 param1, PARAM2 param2);
}
